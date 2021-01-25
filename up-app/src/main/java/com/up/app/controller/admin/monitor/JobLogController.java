package com.up.app.controller.admin.monitor;

import com.up.app.controller.admin.AdminController;
import com.up.app.model.cm.JobLog;
import com.up.app.service.monitor.JobLogService;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.anno.Directory;

/**
 * TODO:
 * <p>
 * Created by 王剑洪 on 2020/1/24 13:02
 */
@Directory(value = "系统监控", icon = "icon iconfont icon-Report")
@Ctr(name = "任务日志", icon = "icon iconfont icon-file-text-fill",view = "monitor/log/jobLog")
public class JobLogController extends AdminController {

    public void page() {
        render(JobLogService.me.page(getModelAddTime(JobLog.class), num(), size()));
    }

    public void info(){
        render(JobLogService.me.info(get("id")));
    }
}
