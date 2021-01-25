package com.up.app.controller.admin.monitor;

import com.up.app.controller.admin.AdminController;
import com.up.app.model.log.OperLog;
import com.up.app.service.monitor.JobLogService;
import com.up.app.service.monitor.OperLogService;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.anno.Directory;

/**
 * TODO:
 * <p>
 * Created by 王剑洪 on 2020/1/24 13:02
 */
@Directory(value = "系统监控", icon = "icon iconfont icon-Report")
@Ctr(name = "系统日志", icon = "icon iconfont icon-snippets-fill",view = "monitor/log/operLog")
public class OperLogController extends AdminController {

    @ApiMethod("系统日志列表")
    public void page(){
        render(OperLogService.me.page(getModelAddTime(OperLog.class),num(),size()));
    }


    public void info(){
        render(OperLogService.me.info(get("id")));
    }
}
