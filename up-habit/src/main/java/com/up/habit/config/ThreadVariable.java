package com.up.habit.config;

import com.jfinal.kit.Kv;

/**
 * TODO:系统变量使用
 * <p>
 *
 * @author 王剑洪 on 2019/11/26 9:07
 */
public class ThreadVariable {

    private static ThreadVariable me = new ThreadVariable();

    private static ThreadLocal USER_ADMIN_ID = new ThreadLocal();
    private static ThreadLocal<String> HOST_URL = new ThreadLocal();

    public static ThreadVariable me() {
        return me;
    }

    public void setAdmin(Object o) {
        USER_ADMIN_ID.set(o);
    }

    public Object getAdmin() {
        return USER_ADMIN_ID.get();
    }

    public void removeAdmin() {
        USER_ADMIN_ID.remove();
    }

    public String getHost() {
        return HOST_URL.get() + "/";
    }

    public Kv host() {
        return Kv.by("host", HOST_URL.get());
    }

    public void setHost(String host) {
        HOST_URL.set(host);
    }

    public void removeHost() {
        HOST_URL.remove();
    }
}
