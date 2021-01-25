package com.up.app.controller.admin;

import com.jfinal.aop.Before;
import com.jfinal.core.NotAction;
import com.up.app.config.interceptor.AdminAuthInterceptor;
import com.up.habit.config.ThreadVariable;
import com.up.habit.config.anno.AuthType;
import com.up.habit.config.anno.Ctr;
import com.up.habit.controller.HabitController;
import com.up.habit.controller.render.To;
import com.up.habit.config.interceptor.UnifiedInterceptor;
import com.up.app.service.log.OperLogService;

/**
 * TODO:
 *
 * @author 王剑洪 on 2019/12/15 21:52
 */


@Before(AdminAuthInterceptor.class)
public class AdminController extends HabitController {

    @NotAction
    @Override
    public void renderJson(Object o) {
        setAttr(UnifiedInterceptor.ATTR_KEY_RESPONSE_TIME, System.currentTimeMillis());
        super.renderJson(o);
        OperLogService.me.add(this, ThreadVariable.me().getAdmin(), getRequest(), (To) o);
    }


}
