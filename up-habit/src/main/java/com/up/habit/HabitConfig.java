package com.up.habit;

import com.jfinal.config.*;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.template.Engine;
import com.up.habit.config.ConfigKit;
import com.up.habit.controller.CommonController;
import com.up.habit.expand.cache.CachePlugin;
import com.up.habit.expand.db.DBPlugin;
import com.up.habit.expand.log.LogbackBuilder;
import com.up.habit.expand.log.LogbackFactory;
import com.up.habit.expand.quartz.HabitQuartzManager;
import com.up.habit.expand.swagger.SwaggerKit;
import com.up.habit.expand.template.ColumnDirective;
import com.up.habit.config.interceptor.UnifiedInterceptor;
import com.up.habit.kit.StrKit;

/**
 * TODO:
 * <p>
 * @author 王剑洪 on 2019/11/14 11:18
 */
public class HabitConfig extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        ConfigKit.initProperties(HabitConstant.DEF_PROP_PATH);
        me.setDevMode(Habit.dev());
        me.setLogFactory(new LogbackFactory());
        LogbackBuilder.actionReporter();
    }

    @Override
    public void configRoute(Routes me) {
        me.setMappingSuperClass(true);
        me.add("/common", CommonController.class);
        ConfigKit.scanController(me);
        SwaggerKit.addRoute(me);
    }

    @Override
    public void configEngine(Engine me) {
        me.setDevMode(Habit.dev());
        me.addSharedMethod(new StrKit());
        me.addDirective("column", ColumnDirective.class);

    }

    @Override
    public void configPlugin(Plugins me) {
        me.add(new DBPlugin());
        me.add(new CachePlugin());

    }

    @Override
    public void configInterceptor(Interceptors me) {
        //统一异常拦截器
        me.addGlobalActionInterceptor(new UnifiedInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {
        DruidStatViewHandler dvh = new DruidStatViewHandler("/druid");
        me.add(dvh);
    }

    @Override
    public void onStart() {
        super.onStart();
        HabitQuartzManager.me().start();
        Habit.startLog();
    }

    @Override
    public void onStop() {
        super.onStop();
        HabitQuartzManager.me().shutdown();
    }


}
