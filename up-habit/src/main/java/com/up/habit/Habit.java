package com.up.habit;

import com.jfinal.config.JFinalConfig;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.server.undertow.UndertowServer;
import com.up.habit.expand.cache.CacheManager;
import com.up.habit.expand.cache.IHabitCache;

/**
 * TODO:
 * <p>
 * @author 王剑洪 on 2019/11/14 10:26
 */
public class Habit {
    public final static String UTF8 = "utf-8";

    private static Habit habit = new Habit();

    public static void start(Class<? extends JFinalConfig> configClass) {
        start(configClass, 80);
    }

    public static void start(Class<? extends JFinalConfig> configClass, int port) {
        PropKit.use("app.properties");
        String module = PathKit.getWebRootPath().replace(System.getProperty("user.dir"), "").replace("\\", "");
        String resourcePath = module + "/src/main/webapp, classpath:webapp,habit/src/main/webapp";
        UndertowServer server = UndertowServer.create(configClass);
        server.setResourcePath(resourcePath);
        server.setPort(port);
        server.setDevMode(true);
        server.start();
    }

    /**
     * 开发模式
     *
     * @return
     */
    public static boolean dev() {
        try{
            return PropKit.getBoolean("app.dev", true);
        }catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }


    public static boolean showSql() {
        return PropKit.getBoolean("app.sql", true);
    }


    /**
     * TODO:获取缓存
     *
     * @return
     */
    public static IHabitCache getCache() {
        return CacheManager.me().getCache();
    }

    public static void startLog(){
        String line = "------------------------------------------------------------";
        String proName = PropKit.get("app.name", "Habit") + " is start!";
        int length = (60 - proName.length()) / 2;
        String out = line.substring(0, length) + proName;
        out += line.substring(0, 60 - out.length());
        String startLog = String.format("\n\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n", line, line, line, out, line, line, line);
        System.out.println(startLog);
    }


}
