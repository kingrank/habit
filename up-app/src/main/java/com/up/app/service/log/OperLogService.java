package com.up.app.service.log;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.Ctr;
import com.up.habit.controller.render.To;
import com.up.habit.config.interceptor.UnifiedInterceptor;
import com.up.habit.service.HabitService;
import com.up.app.model.log.OperLog;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/7 14:33
 */
public class OperLogService extends HabitService<OperLog> {
    public static OperLogService me = new OperLogService();

    public void add(Controller controller, Object userId, HttpServletRequest request, To to) {
        Long requestTime = controller.getAttr(UnifiedInterceptor.ATTR_KEY_REQUEST_TIME);
        Long respondTime = controller.getAttr(UnifiedInterceptor.ATTR_KEY_RESPONSE_TIME);
        Method method = controller.getAttr(UnifiedInterceptor.ATTR_KEY_METHOD);
        Ctr ctr = controller.getClass().getAnnotation(Ctr.class);
        try {
            Enumeration<String> headNames = request.getHeaderNames();
            Enumeration<String> parameters = request.getParameterNames();
            OperLog log = new OperLog();
            if (userId != null) {
                log.setUserId((Integer) userId);
            }
            if (ctr != null) {
                log.setModule(ctr.name());
            }
            ApiMethod apiMethod = method.getAnnotation(ApiMethod.class);
            if (apiMethod != null) {
                log.setName(apiMethod.value());
                log.setAction(method.getName());
            }
            log.setController(controller.getClass().getName());
            log.setRequestTime(new Date(requestTime));

            Map<String, String> header = new HashMap<>();
            while (headNames.hasMoreElements()) {
                String headName = headNames.nextElement();
                header.put(headName, request.getHeader(headName));
            }
            log.setRequestHeader(JsonKit.toJson(header));
            Map<String, Object> parameter = new HashMap<>();
            while (parameters.hasMoreElements()) {
                String paraName = parameters.nextElement();
                parameter.put(paraName, request.getParameter(paraName));
            }
            log.setRequestParameter(JsonKit.toJson(parameter));
            log.setRequestIp(IpKit.getRealIp(request));
            log.setResponseTime(new Date(respondTime));
            log.setResponseBody(JsonKit.toJson(to));
            log.setCode(to.getCode());
            int dispose = (int) (respondTime - requestTime);
            log.setDispose(dispose);
            log.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
