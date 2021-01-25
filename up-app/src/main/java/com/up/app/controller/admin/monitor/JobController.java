package com.up.app.controller.admin.monitor;

import com.up.app.controller.admin.AdminController;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.AuthType;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.anno.Directory;
import com.up.app.model.cm.Job;
import com.up.app.service.monitor.JobService;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/9 16:38
 */
@Directory(value = "系统监控", icon = "icon iconfont icon-Report")
@Ctr(name = "定时任务", icon = "icon iconfont icon-Field-time")
public class JobController extends AdminController {

    public void page() {
        render(JobService.me.page(getModelAddTime(Job.class), num(), size()));
    }

    @ApiMethod(value = "添加任务", auth = AuthType.BUTTON)
    public void add() {
        render(JobService.me.add(getModel(Job.class)));
    }

    @ApiMethod(value = "编辑任务", auth = AuthType.BUTTON)
    public void edit() {
        render(JobService.me.edit(getModel(Job.class)));
    }

    @ApiMethod(value = "删除任务", auth = AuthType.BUTTON)
    public void delete() {
        render(JobService.me.delete(getIntArray("ids")));
    }

    @ApiMethod(value = "启停任务", auth = AuthType.BUTTON)
    public void startOrStop() {
        render(JobService.me.startOrStop(getInt("id")));
    }

}
