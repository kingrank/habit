package com.up.app.controller.admin.monitor;

import com.up.app.controller.admin.AdminController;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.anno.Directory;
import com.up.habit.controller.render.To;
import com.up.habit.kit.SystemKit;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/8 0:00
 */
@Directory(value = "系统监控", icon = "icon iconfont icon-Report")
@Ctr(name = "服务监控", icon = "icon iconfont icon-View")
public class ServiceController extends AdminController {

    @ApiMethod("获取服务信息")
    public void getInfo() {
        render(To.ok(SystemKit.info()));
    }

}
