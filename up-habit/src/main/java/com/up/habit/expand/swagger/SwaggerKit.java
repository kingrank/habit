package com.up.habit.expand.swagger;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Maps;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.core.NotAction;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PropKit;
import com.jfinal.render.Render;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSource;
import com.jfinal.template.source.ClassPathSourceFactory;
import com.jfinal.template.source.ISource;
import com.up.habit.HabitConstant;
import com.up.habit.kit.StrKit;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.anno.Param;
import com.up.habit.config.anno.Params;
import com.up.habit.config.ConfigKit;
import com.up.habit.expand.swagger.bean.*;
import com.up.habit.kit.RequestKit;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * TODO:
 * <p>
 *
 * @author 王剑洪 on 2019/10/23 17:55
 */
public class SwaggerKit {

    public final static String ASSETS_PATH = "/com/up/habit/expand/swagger/assets/";

    public static List<Parameter> commonPara = new ArrayList<>();

    public static void addCommonParameter(Parameter parameter) {
        commonPara.add(parameter);
    }

    /**
     * TODO:swagger添加到路由
     *
     * @param routes
     */
    public static void addRoute(Routes routes) {
        if (PropKit.getBoolean("swagger.open", true)) {
            routes.add("/doc", SwaggerController.class, "/swagger");
        }
    }

    /**
     * TODO:获取文档信息
     *
     * @param request
     * @param isAdmin
     * @return
     */
    public static String getApiDoc(HttpServletRequest request, boolean isAdmin) {
        Swagger swagger = getSwagger(isAdmin);
        Info info = new Info(get("title"), get("description"), get("version"));
        swagger.setInfo(info);
        String host = RequestKit.getHost(request);
        List<String> schemes = new ArrayList<>();
        if (host.startsWith("http://")) {
            schemes.add("http");
        } else if (host.startsWith("https://")) {
            schemes.add("https");
        }
        swagger.setSchemes(schemes);
        swagger.setHost(host.replace("http://", "").replace("https://", ""));
        swagger.setBasePath("/");
        String json = JSON.toJSONString(swagger);
        json = json.replaceAll("\"defaultValue\"", "\"default\"");
        return json;

    }

    private static Swagger getSwagger(boolean isAdmin) {
        Swagger swagger = new Swagger();
        Set<Class<?>> classes = ConfigKit.getClazz(ConfigKit.getPackages(isAdmin ? ConfigKit.PACKAGE_ADMIN : ConfigKit.PACKAGE_API));
        if (classes != null && !classes.isEmpty()) {
            for (Class<?> clazz : classes) {
                Ctr ctr = clazz.getAnnotation(Ctr.class);
                if (ctr == null || StrKit.isBlank(ctr.name())) {
                    continue;
                }
                swagger.addTag(new Tag(ctr.name(), ctr.des()));
                String controllerKey = ConfigKit.getAction(ctr, clazz);
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    ApiMethod api = method.getAnnotation(ApiMethod.class);
                    NotAction notAction = method.getAnnotation(NotAction.class);
                    if (api == null || notAction != null) {
                        continue;
                    }
                    Path path = new Path();
                    String action = controllerKey + "/" + method.getName();
                    Operation operation = new Operation(api.value(), ctr.des(), action);
                    operation.addConsume("application/x-www-form-urlencoded");
                    operation.addTag(ctr.name());
                    operation.addProduce("application/json");
                    //参数
                    Params params = method.getAnnotation(Params.class);
                    //公共参数
                    if (!commonPara.isEmpty()) {
                        for (int i = 0; i < commonPara.size(); i++) {
                            operation.addParameter(commonPara.get(i));
                        }
                    }
                    boolean hasPageNo = false;
                    boolean hasPageSize = false;
                    if (params != null) {
                        Param[] paramArray = params.value();
                        if (paramArray.length > 0) {

                            for (int i = 0; i < paramArray.length; i++) {
                                if ("pageNo".equals(paramArray[i].name())) {
                                    hasPageNo = true;
                                }
                                if ("pageSize".equals(paramArray[i].name())) {
                                    hasPageSize = true;
                                }
                                operation.addParameter(new Parameter(paramArray[i].name(), paramArray[i].des(), paramArray[i].dataType(), paramArray[i].required(), paramArray[i].format(), paramArray[i].defaultValue()));
                            }
                        }
                    }
                    //分页接口自动添加页码,数量参数
                    if (method.getName().startsWith("page")) {
                        if (!hasPageNo) {
                            operation.addParameter(new Parameter("pageNo", "页码", DataType.Integer.getValue(), true, null, "1"));
                        }
                        if (!hasPageSize) {
                            operation.addParameter(new Parameter("pageSize", "数量", DataType.Integer.getValue(), true, null, "20"));
                        }
                    }
                    if (isAdmin) {
                        operation.addParameter(new Parameter("Accept-Token", "header", "用户凭证", "String", false, null, "1"));
                    }
                    operation.addResponse("data", new Response(api.response()));
                    path.setPost(operation);
                    swagger.addPath(action, path);
                }
            }
        }
        return swagger;
    }

    public static String get(String name) {
        return PropKit.get("swagger." + name);
    }

    public static InputStream getAssets(String name) throws FileNotFoundException {
        AssetsSource source = new AssetsSource(ASSETS_PATH + name);
        URL url = source.getUrl();
        return new FileInputStream(new File(url.getFile()));
    }

    public static Render renderAssets(String resource, HttpServletResponse response) {
        AssetsSource source = new AssetsSource(ASSETS_PATH + resource);
        URL url = source.getUrl();
        return new Render() {
            @Override
            public void render() {
                InputStream in = null;
                try {
                    in = new FileInputStream(new File(url.getFile()));
                    IOUtils.copy(in, response.getOutputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(404);
                }
            }
        };
    }

}
