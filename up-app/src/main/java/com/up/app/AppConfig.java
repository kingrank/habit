package com.up.app;

import com.up.app.service.InstallService;
import com.up.app.service.cm.MenuService;
import com.up.app.service.monitor.JobService;
import com.up.habit.HabitConfig;

/**
 * TODO:
 * <p>
 * Created by 王剑洪 on 2020/1/23 22:02
 */
public class AppConfig extends HabitConfig {
    @Override
    public void onStart() {
        /**系统安装*/
        InstallService.install();
        /**权限同步*/
        MenuService.me.sync();
        super.onStart();
        /**任务调度启动*/
        JobService.me.start();
    }
}
