package com.up.habit.config.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.up.habit.config.ThreadVariable;
import com.up.habit.controller.render.To;
import com.up.habit.kit.RequestKit;
import com.up.habit.kit.StrKit;

/**
 * TODO:通用拦截器
 *
 * @author 王剑洪 on 2019/12/12 23:23
 */
public class UnifiedInterceptor implements Interceptor {

    public final Log log = Log.getLog(UnifiedInterceptor.class);

    public static final String ATTR_KEY_CONTROLLER = "controller";
    public static final String ATTR_KEY_METHOD = "method";
    public static final String ATTR_KEY_ACTION_NAME = "action";
    /**
     * 请求时间
     */
    public static final String ATTR_KEY_REQUEST_TIME = "requestTime";
    /**
     * 响应时间
     */
    public static final String ATTR_KEY_RESPONSE_TIME = "responseTime";

    @Override
    public void intercept(Invocation inv) {
        ThreadVariable.me().setHost(RequestKit.getHost(inv.getController().getRequest()));
        inv.getController().set(ATTR_KEY_REQUEST_TIME, System.currentTimeMillis());
        inv.getController().set(ATTR_KEY_CONTROLLER, inv.getController().getClass().getName());
        inv.getController().set(ATTR_KEY_METHOD, inv.getMethod());
        inv.getController().set(ATTR_KEY_ACTION_NAME, inv.getMethodName());
        inv.getController().set("BASE_URL", RequestKit.getHost(inv.getController().getRequest()));
        formatHeader(inv);
        try {
            inv.invoke();
        } catch (Exception e) {
            log.error("system error!", e);
            inv.getController().renderJson(To.fail().set("ex", e));
        } finally {
            ThreadVariable.me().removeHost();
        }
    }

    private void formatHeader(Invocation inv) {
        Controller controller = inv.getController();
        String contentType = controller.getRequest().getHeader("Content-Type");
        if (StrKit.notBlank(contentType)) {
            if (contentType.contains("multipart/form-data")) {
                controller.getFile(StrKit.getRandomUUID());
            }
        }

    }
}
