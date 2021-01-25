package com.up.habit.config;

import com.jfinal.aop.Invocation;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.template.Engine;
import com.up.habit.Habit;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.Ctr;
import com.up.habit.controller.HabitController;
import com.up.habit.controller.render.To;
import com.up.habit.kit.ArrayKit;
import com.up.habit.kit.ClassKit;
import com.up.habit.kit.FileKit;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

/**
 * TODO:
 * <p>
 *
 * @author 王剑洪 on 2019/11/14 11:28
 */
public class ConfigKit {

    private static Log log = Log.getLog(ConfigKit.class);

    public static final String PACKAGE_ALL = "app.ctrl.all";
    public static final String PACKAGE_ADMIN = "app.ctrl.admin";
    public static final String PACKAGE_API = "app.ctrl.api";
    public static final String PACKAGE_WEB = "app.ctrl.web";
    public static final String PACKAGE_WITHOUT = "app.ctrl.without";

    /**
     * TODO:载入对用目录下的所有配置文件
     *
     * @param folder
     */
    public static void initProperties(String folder) {
        String configPath = new File(FileKit.class.getResource("/").getFile()).getPath() + "/";
        if (StrKit.notBlank(folder)) {
            folder = folder + "/";
            configPath = configPath + folder;
        }
        List<String> pathList = FileKit.find(configPath, "properties");
        for (String path : pathList) {
            PropKit.append(folder + path);
        }
    }

    /**
     * TODO:扫描指定包名下的Controller加入路由
     *
     * @param routes
     */
    public static void scanController(Routes routes) {
        Set<Class<?>> classes = getClazz(getPackages(PACKAGE_ALL));
        if (classes == null || classes.isEmpty()) {
            return;
        }
        for (Class<?> clazz : classes) {
            Ctr ctr = clazz.getAnnotation(Ctr.class);
            //没有控制器注解,过滤,
            if (ctr == null || StrKit.isBlank(ctr.name())) {
                continue;
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                ApiMethod apiMethod = method.getAnnotation(ApiMethod.class);
                if (apiMethod == null || StrKit.isBlank(apiMethod.value())) {
                    log.debug(clazz.getName() + "." + method + " 未设置ApiMethod说明");
                }
            }
            String viewPath = ctr.view();
            String controllerKey = getAction(ctr, clazz);
            if (StrKit.notBlank(viewPath)) {
                routes.add(controllerKey, (Class<? extends Controller>) clazz, viewPath);
            } else {
                routes.add(controllerKey, (Class<? extends Controller>) clazz);
            }
        }
    }

    /**
     * TODO:路由访问规则
     * 最后一个包名+控制器缩写
     *
     * @param ctr
     * @param clazz
     * @return
     */
    public static String getAction(Ctr ctr, Class<?> clazz) {
        String value = ctr.value().trim();
        if (StrKit.isBlank(value)) {
            String name = clazz.getSimpleName().replace("Controller", "").replace("Ctrl", "").replace("Ctr", "");
            value = StrKit.firstCharToLowerCase(name);
            String packageName = clazz.getPackage().getName();
            String packageKey = "/";
            if (packageName.contains(".controller.")) {
                packageKey += packageName.split("\\.controller\\.")[1];
            } else if (packageName.contains(".ctrl.")) {
                packageKey += packageName.split("\\.ctrl\\.")[1];
            } else if (packageName.contains(".ctr.")) {
                packageKey += packageName.split("\\.ctr\\.")[1];
            }
            if (!"/".equals(packageKey)) {
                packageKey = packageKey.replace(".", "/");
            }
            value = "index".equals(value) ? "" : ("/" + value);
            return packageKey + value;

        }
        return value;
    }

    /**
     * TODO:不需要加入的控制器
     *
     * @return
     */
    public static List<String> getWithout() {
        String[] nameArray = ArrayKit.toStrArray(getPackages(PACKAGE_WITHOUT));
        return Arrays.asList(nameArray);
    }

    /**
     * TODO:扫描指定包名下的类文件
     *
     * @param packageNames
     * @return
     */
    public static Set<Class<?>> getClazz(String packageNames) {
        String[] packageArray = packageNames.split(",");
        Set<Class<?>> classes = new HashSet<>();
        List<String> without = getWithout();
        for (String pageName : packageArray) {
            pageName = pageName.trim();
            if (StrKit.isBlank(pageName)) {
                continue;
            }
            for (Class<?> clazz : ClassKit.getClass(pageName)) {
                if (without.contains(clazz.getPackage().getName()) || without.contains(clazz.getName()) || !Controller.class.isAssignableFrom(clazz)) {
                    continue;
                }
                classes.add(clazz);
            }
        }
        return classes;
    }

    /**
     * TODO:获取指定类型的控制器
     *
     * @param name
     * @return
     */
    public static String getPackages(String name) {
        String packages = "";
        if (PACKAGE_ALL.equals(name)) {
            packages = PropKit.get(PACKAGE_ADMIN) + "," + PropKit.get(PACKAGE_API) + "," + PropKit.get(PACKAGE_WEB);
        } else {
            packages = PropKit.get(name);
        }
        return packages;
    }

    /**
     * TODO:获取最后一个包名
     *
     * @param clazz
     * @return
     */
    public static String getLastPackage(Class<?> clazz) {
        String packageName = clazz.getPackage().getName();
        return packageName.substring(packageName.lastIndexOf("."));
    }


}
