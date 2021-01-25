package com.up.app.service;

import com.jfinal.kit.HashKit;
import com.up.habit.kit.StrKit;
import com.up.app.model.cm.*;

import java.util.Date;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/3 15:12
 */
public class InstallService {

    public static void install() {
        //默认头像
        File file = new File().dao().findById(1);
        if (file == null) {
            file = new File().setId(1L).setType("jpg").setUsed(1).setPath("upload/avatar-def.jpg").setCreateTime(new Date()).setCreateBy(1);
            file.save();
        }
        //机构数据
        Org org = new Org().dao().findById(1);
        if (org == null) {
            org = new Org().setId(1).setPId(0).setName("UP科技").setState(0).setIsDel(0).setSort(0).setCreateBy(1).setCreateTime(new Date()).setAncestors("0");
            org.save();
        }
        //角色数据
        Role role = new Role().dao().findById(1);
        if (role == null) {
            role = new Role().setId(1).setName("超级管理员").setSort(1).setScope(1).setState(0).setIsDel(0).setRemark("超级管理员").setCreateBy(1).setCreateTime(new Date());
            role.save();
        }
        User user = new User().dao().findById(1);
        if (user == null) {
            String salt = StrKit.getRandomUUID();
            String password = HashKit.sha256(salt + "111111");
            user = new User().setOrgId(1).setAvatar(1).setId(1).setAccount("admin").setNickname("超级管理员").setSex(0).setSalt(salt).setPassword(password)
                    .setState(0).setIsDel(0).setCreateBy(1).setCreateTime(new Date());
            user.save();
        }
        RoleUser roleUser = new RoleUser().dao().findFirst("select * from cm_role_user where role_id=? and user_id=?", 1, 1);
        if (roleUser == null) {
            roleUser = new RoleUser().setUserId(1).setRoleId(1);
            roleUser.save();
        }

    }
}
