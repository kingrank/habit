package com.up.app.controller.admin;

import com.jfinal.aop.Clear;
import com.up.app.config.interceptor.AdminAuthInterceptor;
import com.up.app.model.cm.User;
import com.up.app.service.IndexService;
import com.up.app.service.cm.MenuService;
import com.up.habit.config.anno.*;
import com.up.habit.controller.render.To;
import com.up.habit.service.CommonService;

/**
 * TODO:
 * <p>
 * Created by 王剑洪 on 2020/1/24 0:44
 */
@Ctr(name = "系统", icon = "icon iconfont icon-user", auth = AuthType.NULL)
public class IndexController extends AdminController {

    @ApiMethod("获取图片验证码")
    @Params({
            @Param(name = "length", des = "验证码长度", dataType = "Integer", required = false),
            @Param(name = "width", des = "验证码图片宽度", dataType = "Integer", required = false),
            @Param(name = "height", des = "验证码图片高度", dataType = "Integer", required = false)})
    @Clear(AdminAuthInterceptor.class)
    public void captcha() {
        render(CommonService.me.captcha(getInt("length"), getInt("width"), getInt("height")));
    }

    @ApiMethod("系统登录")
    @Params({
            @Param(name = "account", des = "账号"),
            @Param(name = "password", des = "密码"),
            @Param(name = "code", des = "验证码"),
            @Param(name = "uuId", des = "验证码uuid"),
    })
    @Clear(AdminAuthInterceptor.class)
    public void login() {
        render(IndexService.me.login(get("account"), get("password"), get("code"), get("uuid"), this));
    }


    @ApiMethod("退出登录")
    public void logout() {
        render(IndexService.me.logout(getToken()));
    }


    @ApiMethod("获取用户信息")
    public void getInfo() {
        User user = IndexService.me.getUserInfo(getToken());
        if (user != null) {
            render(To.ok(user));
        } else {
            render(new To(To.CODE_LOGIN_OVERDUE, "登录凭证已失效"));
        }
    }

    @ApiMethod("获取用户角色岗位")
    public void getUserRoleAndPost() {
        render(IndexService.me.getUserRoleAndPost());
    }


    @ApiMethod("更新信息")
    @Params({
            @Param(name = "avatar", dataType = "Integer", des = "头像文件ID"),
            @Param(name = "sex", dataType = "Integer", des = "性别,0-保密,1-男,2-女"),
            @Param(name = "nickname", des = "昵称"),
            @Param(name = "email", des = "邮箱"),
            @Param(name = "phone", des = "手机号"),
    })
    public void update() {
        render(IndexService.me.edit(getInt("avatar"), get("nickname"), get("email"), get("phone"), getInt("sex"), getToken()));
    }

    @ApiMethod("设置密码")
    @Params({
            @Param(name = "password", des = "旧密码"),
            @Param(name = "newPassword", des = "新密码")
    })
    public void setPassword() {
        render(IndexService.me.setPassword(get("password"), get("newPassword")));
    }


    @ApiMethod("获取系统路由")
    public void getRouters() {
        render(MenuService.me.getRouters(getIntArray("roleIds")));
    }


}
