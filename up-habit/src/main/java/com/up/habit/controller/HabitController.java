package com.up.habit.controller;

import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.up.habit.config.anno.Ctr;
import com.up.habit.controller.render.To;
import com.up.habit.expand.db.model.DBModel;
import com.up.habit.kit.StrKit;

import java.util.*;
import java.util.regex.Pattern;

/**
 * TODO:
 * <p>
 *
 * @author 王剑洪 on 2019/11/18 11:26
 */
@Ctr
public class HabitController extends Controller {

    @NotAction
    public String getToken() {
        return getHeader("Accept-Token");
    }


    /**
     * TODO:获取页码
     *
     * @return
     */
    @NotAction
    public int num() {
        return getInt("pageNo", 1);
    }

    /**
     * TODO:获取数量
     *
     * @return
     */
    @NotAction
    public int size() {
        return getInt("pageSize", 10);
    }


    @NotAction
    @Override
    public <T> T getModel(Class<T> modelClass) {
        return getModel(modelClass, "", true);
    }

    @NotAction
    public <T> T getModelAddTime(Class<T> modelClass) {
        T t = getModel(modelClass);
        ((DBModel) t).put("start_create_time", get("start_create_time"));
        ((DBModel) t).put("end_create_time", get("end_create_time"));
        return t;
    }

    @NotAction
    @Override
    public <T> T getBean(Class<T> beanClass) {
        return super.getBean(beanClass, "", true);
    }

    @NotAction
    public void render(To to) {
        getResponse().addHeader("Access-Control-Allow-Origin", "*");//跨域处理
        this.renderJson(to);
    }

    @NotAction
    private TreeMap<Integer, String> getArrayTreeMap(String name) {
        String regex = name + "\\[\\d*\\]";
        Pattern pattern = Pattern.compile(regex);
        TreeMap<Integer, String> treeMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                return integer - t1;
            }
        });
        Enumeration<String> paraNames = getRequest().getParameterNames();
        for (Enumeration<String> e = paraNames; e.hasMoreElements(); ) {
            String thisName = e.nextElement().toString();
            if (pattern.matcher(thisName).matches()) {
                int index = Integer.parseInt(thisName.replace(name + "[", "").replace("]", ""));
                treeMap.put(index, getRequest().getParameter(thisName));
            }
        }
        if (treeMap.size() == 0 && StrKit.notBlank(getRequest().getParameter(name))) {
            String[] array = getRequest().getParameter(name).split(",");
            for (int i = 0; i < array.length; i++) {
                treeMap.put(0, array[i]);
            }
        }
        return treeMap;
    }

    /**
     * TODO:获取String数组参数
     *
     * @param name
     * @return java.lang.String[]
     * @Author 王剑洪 on 2020/1/5 20:45
     **/
    @NotAction
    public String[] getArray(String name) {
        TreeMap<Integer, String> treeMap = getArrayTreeMap(name);
        Iterator iter = treeMap.entrySet().iterator();
        String[] para = new String[treeMap.size()];
        while (iter.hasNext()) {
            Map.Entry<Integer, String> entry = (Map.Entry) iter.next();
            para[entry.getKey()] = entry.getValue();
        }
        return para;
    }

    /**
     * TODO:获取Int数组参数
     *
     * @param name
     * @return java.lang.Integer[]
     * @Author 王剑洪 on 2020/1/5 20:45
     **/
    @NotAction
    public Integer[] getIntArray(String name) {
        TreeMap<Integer, String> treeMap = getArrayTreeMap(name);
        Iterator iter = treeMap.entrySet().iterator();
        Integer[] para = new Integer[treeMap.size()];
        while (iter.hasNext()) {
            Map.Entry<Integer, String> entry = (Map.Entry) iter.next();
            para[entry.getKey()] = Integer.parseInt(entry.getValue());
        }
        return para;
    }


}
