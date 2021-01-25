package com.up.habit.expand.db.kit;

import com.up.habit.expand.db.model.DBModel;
import com.up.habit.kit.StrKit;

import java.util.*;

/**
 * TODO:树
 *
 * @author 王剑洪 on 2020/1/3 14:02
 */
public class TreeKit {


    public static <T extends DBModel> List<T> buildTree(List<T> list, String pIdKey, String idKey) {
        TreeMap<Object, List<T>> map = new TreeMap();
        List<Object> tmpList = new ArrayList<>();
        for (T model : list) {
            tmpList.add(model.get(idKey));
            Object key = model.get(pIdKey);
            if (key != null && StrKit.notBlank(String.valueOf(key))) {
                List<T> tmp = map.get(key) == null ? new ArrayList<>() : map.get(key);
                tmp.add(model);
                map.put(key, tmp);
            }
        }
        List<T> rootList = new ArrayList<>();
        for (Iterator<T> iterator = list.iterator(); iterator.hasNext(); ) {
            T model = iterator.next();
            if (!tmpList.contains(model.get(pIdKey))) {
                rootList.add(model);
            }
        }
        rootList = getNextList(rootList, map, idKey);
        return rootList;
    }

    /**
     * TODO:递归获取下级模块
     *
     * @param list
     * @param map
     **/
    private static <T extends DBModel> List<T> getNextList(List<T> list, TreeMap<Object, List<T>> map, String idKey) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        for (int i = 0; i < list.size(); i++) {
            List<T> childrenList = map.get(list.get(i).get(idKey));
            childrenList = getNextList(childrenList, map, idKey);
            boolean hasChildren = !childrenList.isEmpty();
            list.get(i).put("hasChildren", hasChildren);
            if (hasChildren) {
                list.get(i).put("children", childrenList);
            }
        }
        return list;
    }


}
