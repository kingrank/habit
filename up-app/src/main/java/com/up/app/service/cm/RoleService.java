package com.up.app.service.cm;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.up.habit.controller.render.To;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.StrKit;
import com.up.habit.service.HabitService;
import com.up.app.model.cm.Role;

import java.util.List;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/3 17:33
 */
public class RoleService extends HabitService<Role> {
    public static RoleService me = new RoleService();

    /**
     * TODO:添加角色
     *
     * @param role
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:09
     **/
    public To add(Role role) {
        if (role.getId() != null) {
            role.remove("id");
        }
        if (StrKit.isBlank(role.getName())) {
            return To.fail("角色名称不能为空!");
        }
        if (!NumberKit.isScope(role.getSort(), SORT_MIN, SORT_MAX)) {
            return To.fail("排序值不正确");
        }
        if (!NumberKit.isScope(role.getState(), 0, 1)) {
            return To.fail("角色状态不正确");
        }
        //默认全部数据权限
        role.setScope(1);
        role.setIsDel(0);
        return To.to(role.save());
    }

    /**
     * TODO:编辑角色
     *
     * @param role
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:09
     **/
    public To edit(Role role) {
        if (role.getId() == null) {
            return To.fail("角色不存在!");
        }
        Role old = dao.findById(role.getId());
        if (old == null) {
            return To.fail("角色不存在");
        }
        if (StrKit.notBlank(role.getName())) {
            old.setName(role.getName());
        }
        if (NumberKit.isScope(role.getSort(), SORT_MIN, SORT_MAX)) {
            old.setSort(role.getSort());
        }
        if (NumberKit.isScope(role.getScope(), 1, 4)) {
            old.setScope(role.getScope());
        }
        if (NumberKit.isScope(role.getState(), 0, 1)) {
            old.setState(role.getState());
        }
        old.setRemark(role.getRemark());
        return To.to(old.update());
    }

    /**
     * TODO:删除角色
     *
     * @param ids
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:10
     **/
    public To remove(Integer[] ids) {
        long count = dao.template("queryRoleUserCount", Kv.by("ids", ids)).queryLong();
        if (count > 0) {
            return To.fail("还有该角色的用户未删除!");
        }
        return super.remove(ids);
    }

    /**
     * 获取角色菜单权限和机构权限
     *
     * @param roleId
     * @return
     */
    public To getAuth(int roleId) {
        Role role = dao.findById(roleId);
        if (role == null) {
            return To.fail("角色不存在!");
        }
        Kv kv = Kv.create().set("scope", role.getScope());
        List<Integer> menuList = dao.template("queryAuthMenu", roleId).queryIntList();
        kv.set("menus", menuList);
        if (role.getScope().intValue() == 2) {
            List<Integer> orgList = dao.template("queryAuthOrg", roleId).queryIntList();
            kv.set("orgs", orgList);
        }
        return To.ok(kv);

    }

    /**
     * 编辑角色权限
     *
     * @param roleId
     * @param scope
     * @param menus
     * @param orgs
     * @return
     */
    public To editAuth(int roleId, int scope, Integer[] menus, Integer[] orgs) {
        Role role = dao.findById(roleId);
        if (role == null) {
            return To.fail("角色不存在!");
        }
        if (!NumberKit.isScope(scope, 1, 5)) {
            return To.fail("数据权限不正确!");
        }
        Kv kv = Kv.by("roleId", roleId)
                .set("menus", menus)
                .set("scope", scope)
                .set("orgs", orgs);
        boolean res = dao.template("editAuth", kv).batch();
        return To.ok(res);
    }

    /**
     * 角色选择数据
     *
     * @return
     */
    public To select() {
        List<Role> list = dao.template("select").find();
        return To.ok(list);
    }

    /**
     * 查询角色对应的按钮权限
     *
     * @param roleIds
     * @return
     */
    public List<String> buttonPermission(List<Integer> roleIds) {
        return dao.template("queryAuthButtonAction", Kv.by("roles", roleIds)).queryListByCache();
    }

    public boolean verCount(Integer[] ids) {
        if (ids == null || ids.length == 0) {
            return false;
        }
        long count = dao.template("queryCountByIds", Kv.by("ids", ids)).queryLong();
        return count - ids.length == 0;
    }
}
