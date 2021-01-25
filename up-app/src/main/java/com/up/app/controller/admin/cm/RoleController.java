package com.up.app.controller.admin.cm;

import com.jfinal.aop.Clear;
import com.up.app.config.interceptor.AdminAuthInterceptor;
import com.up.app.controller.admin.AdminController;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.AuthType;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.anno.Directory;
import com.up.app.model.cm.Role;
import com.up.app.service.cm.RoleService;
import com.up.habit.controller.render.To;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/3 17:20
 */
@Directory(value = "系统设置", icon = "icon iconfont icon-setting-fill")
@Ctr(name = "角色管理", icon = "icon iconfont icon-team")

public class RoleController extends AdminController {

    @ApiMethod("角色列表")
    public void page() {
        render(RoleService.me.page(getModelAddTime(Role.class), num(), size()));
    }

    @ApiMethod(value = "添加角色", auth = AuthType.BUTTON)
    public void add() {
        render(RoleService.me.add(getModel(Role.class)));
    }

    @ApiMethod(value = "编辑角色", auth = AuthType.BUTTON)
    public void edit() {
        render(RoleService.me.edit(getModel(Role.class)));
    }

    @ApiMethod(value = "移除角色", auth = AuthType.BUTTON)
    public void delete() {
        render(RoleService.me.remove(getIntArray("ids")));
    }

    @ApiMethod(value = "获取角色菜单权限以及数据权限")
    public void getAuth() {
        render(RoleService.me.getAuth(getInt("role_id")));
    }

    @ApiMethod(value = "编辑权限", auth = AuthType.BUTTON)
    public void editAuth() {
        render(RoleService.me.editAuth(getInt("id"), getInt("scope"), getIntArray("menus"), getIntArray("orgs")));
    }

    @ApiMethod(value = "角色选择")
    public void select() {
        render(RoleService.me.select());
    }
}
