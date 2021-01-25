package com.up.app.controller.admin.cm;

import com.up.app.controller.admin.AdminController;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.AuthType;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.anno.Directory;
import com.up.app.model.cm.Menu;
import com.up.app.service.cm.MenuService;

/**
 * TODO:菜单模块
 *
 * @author 王剑洪 on 2019/12/19 21:01
 */
@Directory(value = "系统设置", icon = "icon iconfont icon-setting-fill")
@Ctr(name = "菜单模块", icon = "icon iconfont icon-menu")
public class MenuController extends AdminController {


    @ApiMethod("获取菜单列表")
    public void list() {
        render(MenuService.me.list(getModel(Menu.class)));
    }

    @ApiMethod("根据菜单ID获取详情")
    public void info() {
        render(MenuService.me.infoById(get("id")));
    }

    @ApiMethod(value = "新增菜单", auth = AuthType.BUTTON)
    public void add() {
        render(MenuService.me.add(getModel(Menu.class)));
    }

    @ApiMethod(value = "编辑菜单", auth = AuthType.BUTTON)
    public void edit() {
        render(MenuService.me.edit(getModel(Menu.class)));
    }

    @ApiMethod(value = "删除菜单", auth = AuthType.BUTTON)
    public void delete() {
        render(MenuService.me.delete(get("id")));
    }

    @ApiMethod("前端选择树结构")
    public void treeSelect() {
        render(MenuService.me.treeSelect());
    }


}
