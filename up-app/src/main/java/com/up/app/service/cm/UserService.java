package com.up.app.service.cm;

import com.jfinal.kit.HashKit;
import com.jfinal.kit.Kv;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.up.app.service.IndexService;
import com.up.habit.Habit;
import com.up.habit.config.ThreadVariable;
import com.up.habit.controller.HabitController;
import com.up.habit.controller.render.To;
import com.up.habit.expand.db.listener.Batch;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.RequestKit;
import com.up.habit.kit.StrKit;
import com.up.habit.service.CommonService;
import com.up.habit.service.HabitService;
import com.up.app.model.cm.Org;
import com.up.app.model.cm.Session;
import com.up.app.model.cm.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO:用户 业务层
 *
 * @author 王剑洪 on 2019/12/16 22:13
 */
public class UserService extends HabitService<User> {
    public static UserService me = new UserService();


    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    public To add(User user, Integer[] roles, Integer[] posts) {
        if (user.getOrgId() == null) {
            return To.fail("机构不能为空!");
        }
        if (StrKit.isBlank(user.getAccount())) {
            return To.fail("用户账号不能为空!");
        }
        if (StrKit.isBlank(user.getNickname())) {
            return To.fail("用户昵称不能为空!");
        }
        if (!NumberKit.has(user.getSex(), 0, 1, 2)) {
            return To.fail("用户性别不正确!");
        }
        if (!NumberKit.has(user.getState(), 0, 1)) {
            return To.fail("用户状态不正确!");
        }
        User old = dao.template("queryByAccount", user.getAccount()).findFirst();
        if (old != null) {
            return To.fail("账号已存在!");
        }
        if (new Org().dao().findById(user.getOrgId()) == null) {
            return To.fail("机构不存在!");
        }
        if (user.getAvatar() != null) {
            if (!FileService.me.has(user.getAvatar())) {
                return To.fail("头像不存在");
            }
        }
        if (!RoleService.me.verCount(roles)) {
            return To.fail("角色已删除,请重新选择!");
        }
        if (!PostService.me.verCount(posts)) {
            return To.fail("岗位已删除,请重新选择!");
        }
        String salt = StrKit.getRandomUUID();
        String password = "123456";
        user.setSalt(salt);
        user.setIsDel(0);
        user.setState(0);
        user.setPassword(HashKit.sha256(user.getSalt() + password));
        boolean res = db().tx(() -> {
            if (user.save()) {
                FileService.me.usedNonTransaction(user.getAvatar(), null);
                Kv kv = Kv.by("id", user.getId()).set("roles", roles).set("posts", posts);
                return dao.template("editUserRoleAndPost", kv).batchNonTransaction();
            }
            return false;
        });
        return To.to(res);
    }

    /**
     * 编辑用户
     *
     * @param user
     * @return
     */
    public To edit(User user, Integer[] roles, Integer[] posts) {
        if (user.getId() == null) {
            return To.fail("用户不存在!");
        }
        User old = dao.findById(user.getId());
        if (old == null) {
            return To.fail("用户不存在!");
        }
        if (StrKit.notBlank(user.getAccount()) && !old.getAccount().equals(user.getAccount())) {
            User tmp = dao.template("queryByAccount", user.getAccount()).findFirst();
            if (tmp != null) {
                return To.fail("账号已存在!");
            }
            old.setAccount(user.getAccount());
        }
        if (user.getOrgId() != null && new Org().dao().findById(user.getOrgId()) == null) {
            return To.fail("机构不存在!");
        }
        if (!RoleService.me.verCount(roles)) {
            return To.fail("角色已删除,请重新选择!");
        }
        if (!PostService.me.verCount(posts)) {
            return To.fail("岗位已删除,请重新选择!");
        }
        if (user.getAvatar() != null) {
            if (!FileService.me.has(user.getAvatar())) {
                return To.fail("头像不存在");
            }
        }
        Integer oldFileId = old.getAvatar();
        old.setOrgId(user.getOrgId() == null ? old.getOrgId() : user.getOrgId());
        old.setNickname(StrKit.notBlank(user.getNickname()) ? user.getNickname() : old.getNickname());
        old.setEmail(StrKit.notBlank(user.getEmail()) ? user.getEmail() : old.getEmail());
        old.setPhone(StrKit.notBlank(user.getPhone()) ? user.getPhone() : old.getPhone());
        old.setState(NumberKit.has(user.getState(), 0, 1) ? user.getState() : old.getState());
        old.setSex(NumberKit.has(user.getSex(), 0, 1, 2) ? user.getSex() : old.getSex());
        old.setAvatar(user.getAvatar() == null ? old.getAvatar() : user.getAvatar());
        Kv kv = Kv.by("id", user.getId()).set("roles", roles).set("posts", posts);
        boolean res = dao.template("editUserRoleAndPost", kv).batch(new Batch() {
            @Override
            public boolean batchBefore() {
                FileService.me.usedNonTransaction(old.getAvatar(), oldFileId);
                return old.update();
            }
        });

        return To.to(res);
    }

    /**
     * TODO:重置用户密码
     *
     * @param userId
     * @return
     */
    public To resetPassword(Integer userId) {
        User user = dao.findById(userId);
        if (user == null) {
            return To.fail("用户不存在!");
        }
        String salt = StrKit.getRandomUUID();
        String password = HashKit.sha256(salt + IndexService.DEF_PASSWORD);
        user.setPassword(password);
        user.setSalt(salt);
        return To.to(user.update());
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id
     * @return
     */
    public To getInfoById(int id) {
        User user = dao.template("queryById", Kv.by("id", id).set("host", ThreadVariable.me().getHost())).findFirst();
        if (user == null) {
            return To.fail("用户不存在!");
        }
        user.remove("salt", "password");
        List<Integer> roleList = dao.template("queryRoles", id).queryIntList();
        List<Integer> orgList = dao.template("queryPosts", id).queryIntList();
        user.put("roles", roleList);
        user.put("posts", orgList);
        return To.ok(user);
    }
}
