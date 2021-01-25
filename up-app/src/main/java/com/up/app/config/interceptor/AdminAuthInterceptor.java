package com.up.app.config.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.up.app.controller.admin.AdminController;
import com.up.app.model.cm.User;
import com.up.app.service.IndexService;
import com.up.app.service.cm.UserService;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.AuthType;
import com.up.habit.config.ThreadVariable;
import com.up.habit.controller.render.To;
import com.up.habit.kit.StrKit;

import java.util.List;

/**
 * TODO:后台拦截器
 *
 * @author 王剑洪 on 2019/12/26 10:21
 */
public class AdminAuthInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        AdminController controller = (AdminController) inv.getController();
        String token = controller.getToken();
        if (StrKit.isBlank(token)) {
            controller.render(new To(To.CODE_LOGIN_NULL, "登录凭证不能为空"));
            return;
        }
        User user = IndexService.me.getUserInfo(token);
        if (user == null) {
            controller.render(new To(To.CODE_LOGIN_OVERDUE, "登录凭证已失效"));
            return;
        }
        //////权限判断
        if (!verifyPermissions(user, inv)) {
            controller.render(new To(To.CODE_NOT_PERMISSION, "没有权限操作!"));
            return;
        }
        //////权限判断
        try {
            ThreadVariable.me().setAdmin(user.getId());
            inv.invoke();
        } finally {
            ThreadVariable.me().removeAdmin();
        }


    }

    /**
     * TODO:权限验证
     *
     * @param user
     * @param inv
     * @return
     */
    public boolean verifyPermissions(User user, Invocation inv) {
        ApiMethod method = inv.getMethod().getAnnotation(ApiMethod.class);
        if (method != null) {
            AuthType authType = method.auth();
            if (authType != AuthType.NULL) {
                List<String> permissions = user.get(IndexService.KEY_PERM);
                if (!permissions.contains(IndexService.PERM_ALL)) {
                    return permissions.contains(inv.getActionKey());
                }
            }
        }
        return true;
    }
}
