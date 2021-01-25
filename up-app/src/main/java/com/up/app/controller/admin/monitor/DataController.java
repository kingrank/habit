package com.up.app.controller.admin.monitor;

import com.jfinal.aop.Clear;
import com.up.app.config.interceptor.AdminAuthInterceptor;
import com.up.app.controller.admin.AdminController;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.anno.Directory;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/7 22:57
 */
@Directory(value = "系统监控", icon = "icon iconfont icon-Report")
@Ctr(name = "数据监控", icon = "icon iconfont icon-Console-SQL")
public class DataController extends AdminController {

    @Clear(AdminAuthInterceptor.class)
    public void druid() {
        redirect("/druid/index.html");
    }

}
