package com.up.app.service;

import com.jfinal.kit.HashKit;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.up.app.model.cm.Session;
import com.up.app.model.cm.User;
import com.up.app.service.cm.FileService;
import com.up.app.service.cm.RoleService;
import com.up.habit.Habit;
import com.up.habit.config.ThreadVariable;
import com.up.habit.controller.HabitController;
import com.up.habit.controller.render.To;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.StrKit;
import com.up.habit.service.CommonService;
import com.up.habit.service.HabitService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO:
 *
 * @author 王剑洪 on 2019/12/14 1:56
 */
public class IndexService extends HabitService<User> {

    public static IndexService me = new IndexService();

    /**
     * 过期时间,默认180分钟
     */
    public static final int EXPIRE_SECOND = 60 * 180;
    /**
     * 过期剩余时间
     */
    public static final int MAX_REMAIN = 10 * 1000;
    public static final String LOGIN_USER_CACHE = "loginUser";

    public static final String DEF_PASSWORD = "123456";

    public static final String KEY_ROLES = "roles";
    public static final String KEY_PERM = "permissions";
    public static final String PERM_ALL = "*/*/*";

    private long expireAt() {
        return System.currentTimeMillis() + EXPIRE_SECOND * 1000;
    }

    /**
     * TODO:登录
     *
     * @param account
     * @param password
     * @param code
     * @param uuId
     * @param c
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2019/12/21 22:21
     **/
    public To login(String account, String password, String code, String uuId, HabitController c) {
        /*if (!CommonService.me.validateCaptcha(uuId, code)) {
            return To.fail("验证码错误!");
        }*/
        if (StrKit.hasBlank(account, password)) {
            return To.fail("账号或密码不能为空");
        }
        User user = dao.template("queryByAccount", account).findFirst();

        if (user == null || !HashKit.sha256(user.getSalt() + password).equals(user.getPassword())) {
            return To.fail("账号或密码错误!");
        }
        if (user.getState().intValue() != 0) {
            return To.fail("账号已停用!");
        }
        uuId = StrKit.getRandomUUID();
        user.setLoginIp(IpKit.getRealIpV2(c.getRequest()));
        user.setLoginTime(new Date());
        user.update();
        //用户session
        Session session = new Session().setId(uuId).setUserId(user.getId()).setExpireAt(expireAt());
        session.save();
        user.remove("salt", "password");
        user.put("token", session.getId());
        user.put("expireAt", session.getId());
        Habit.getCache().put(LOGIN_USER_CACHE, uuId, user, EXPIRE_SECOND);
        return To.ok("登录成功!", uuId);
    }

    /**
     * TODO:退出登录
     *
     * @param token
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:10
     **/
    public To logout(String token) {
        if (token != null) {
            Habit.getCache().remove(LOGIN_USER_CACHE, token);
            new Session().dao().deleteById(token);
        }
        return To.ok("退出登录!");
    }

    /**
     * TODO:获取用户信息
     *
     * @param token
     * @return com.up.app.model.cm.User
     * @Author 王剑洪 on 2020/1/5 16:10
     **/
    public User getUserInfo(String token) {
        User user = Habit.getCache().get(LOGIN_USER_CACHE, token);
        if (user == null) {
            Session session = new Session().dao().findById(token);
            if (session != null) {
                /**登录过期时间*/
                long remain = session.getExpireAt().longValue() - System.currentTimeMillis();
                //登录已过期
                if (remain < MAX_REMAIN) {
                    return null;
                } else if (remain > MAX_REMAIN) {
                    /**快过期了,还在操作,更新过期时间*/
                    session.setExpireAt(expireAt());
                    session.update();
                }
                Kv kv = Kv.by("id", session.getUserId()).set("host", ThreadVariable.me().getHost());
                user = dao.template("queryById", kv).findFirstByCache();
                if (user != null) {
                    user.put("token", session.getId());
                    user.put("expireAt", session.getId());
                    user.remove("password", "salt");
                    Habit.getCache().put(LOGIN_USER_CACHE, token, user, EXPIRE_SECOND);
                }
            }
        }
        if (user != null) {
            List<Integer> roleList = new ArrayList<>();
            if (user.getId().intValue() == 1) {
                roleList.add(1);
            } else {
                roleList = dao.template("queryRoles", user.getId()).queryIntListByCache();
            }

            user.put(KEY_ROLES, roleList);
            if (roleList.contains(1)) {
                List<String> permissions = new ArrayList<>();
                permissions.add(PERM_ALL);
                user.put(KEY_PERM, permissions);
            } else {
                List<String> permissions = RoleService.me.buttonPermission(roleList);
                user.put(KEY_PERM, permissions);
            }
        }
        return user;
    }

    /**
     * TODO:获取用户角色名称列表和岗位列表
     *
     * @return
     */
    public To getUserRoleAndPost() {
        Integer userId = (Integer) ThreadVariable.me().getAdmin();
        List<String> roles = dao.template("queryUserRoleName", userId).queryList();
        List<String> posts = dao.template("queryUserPostName", userId).queryList();
        return To.ok(Kv.by("roles", roles).set("posts", posts));
    }

    /**
     * TODO:编辑用户自身信息
     *
     * @param avatar
     * @param nickname
     * @param email
     * @param phone
     * @param sex
     * @return
     */
    public To edit(Integer avatar, String nickname, String email, String phone, Integer sex, String token) {
        User user = dao.findById(ThreadVariable.me().getAdmin());

        Integer oldAvatar = user.getAvatar();
        Integer newAvatar = avatar;
        user.setAvatar(avatar == null ? user.getAvatar() : avatar);
        user.setNickname(StrKit.isBlank(nickname) ? user.getNickname() : nickname);
        user.setEmail(StrKit.isBlank(email) ? user.getEmail() : email);
        user.setPhone(StrKit.isBlank(phone) ? user.getPhone() : phone);
        user.setSex(NumberKit.has(sex, 0, 1, 2) ? sex : user.getSex());
        boolean res = db().tx(() -> {
            if (user.update()) {
                FileService.me.usedNonTransaction(newAvatar, oldAvatar);
            }
            return true;
        });
        if (res) {
            Habit.getCache().remove(LOGIN_USER_CACHE, token);
        }
        return To.to(user.update());
    }

    /**
     * TODO:修改自身密码
     *
     * @param password
     * @param newPassword
     * @return
     */
    public To setPassword(String password, String newPassword) {
        if (StrKit.isBlank(password)) {
            return To.fail("原密码不正确!");
        }
        if (StrKit.isBlank(newPassword)) {
            return To.fail("新密码不能为空!");
        }
        User user = dao.findById(ThreadVariable.me().getAdmin());
        if (!user.getPassword().equals(HashKit.sha256(user.getSalt() + password))) {
            return To.fail("原密码不正确!");
        }
        String salt = StrKit.getRandomUUID();
        newPassword = HashKit.sha256(salt + newPassword);
        user.setPassword(newPassword);
        user.setSalt(salt);
        return To.to(user.update());

    }
}
