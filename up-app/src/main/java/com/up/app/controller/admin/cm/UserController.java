package com.up.app.controller.admin.cm;

import com.jfinal.aop.Clear;
import com.up.app.config.interceptor.AdminAuthInterceptor;
import com.up.app.controller.admin.AdminController;
import com.up.habit.config.anno.*;
import com.up.habit.controller.render.To;
import com.up.app.model.cm.User;
import com.up.app.service.cm.FileService;
import com.up.app.service.cm.MenuService;
import com.up.app.service.cm.UserService;

/**
 * TODO:
 *
 * @author 王剑洪 on 2019/12/21 21:49
 */
@Directory(value = "系统设置", icon = "icon iconfont icon-setting-fill")
@Ctr(name = "系统用户", icon = "icon iconfont icon-user")
public class UserController extends AdminController {


    @ApiMethod("获取用户列表")
    public void page() {
        User user = getModelAddTime(User.class);
        render(UserService.me.page(user, num(), size()));
    }

    @ApiMethod(value = "添加用户", auth = AuthType.BUTTON)
    public void add() {
        render(UserService.me.add(getModel(User.class), getIntArray("roles"), getIntArray("posts")));
    }

    @ApiMethod("获取用户信息")
    public void getInfoById() {
        render(UserService.me.getInfoById(getInt("id")));
    }

    @ApiMethod(value = "编辑用户", auth = AuthType.BUTTON)
    public void edit() {
        render(UserService.me.edit(getModel(User.class), getIntArray("roles"), getIntArray("posts")));
    }

    @ApiMethod(value = "重置密码", auth = AuthType.BUTTON)
    public void resetPassword() {
        render(UserService.me.resetPassword(getInt("id")));
    }

    @ApiMethod(value = "删除用户", auth = AuthType.BUTTON)
    public void delete() {
        render(UserService.me.remove(getIntArray("ids")));
    }

    @ApiMethod(value = "文件上传")
    public void upload() {
        render(FileService.me.batchUpload(getRequest(), getFiles(""), get("type")));
    }


}
