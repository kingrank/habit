package com.up.habit.kit;

import com.up.habit.expand.db.model.DBModel;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * TODO:
 * create by 王剑洪 on 2019/10/27 21:39
 */
public class ArrayKit {

    public static boolean isNotEmpty(Collection list) {
        return list != null && list.size() > 0;
    }

    public static boolean isNotEmpty(Map map) {
        return map != null && map.size() > 0;
    }

    public static boolean isNotEmpty(Object[] objects) {
        return objects != null && objects.length > 0;
    }

    public static boolean isNullOrEmpty(Collection list) {
        return list == null || list.size() == 0;
    }

    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNullOrEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    public static <T> T[] concat(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * TODO:转换为String数组
     *
     * @param split 分隔符
     * @param str   被转换的值
     * @return java.lang.String[]
     * @Author 王剑洪 on 2019/12/15 22:41
     **/
    public static String[] toStrArray(String str, String split) {
        if (StrKit.isBlank(str)) {
            return new String[0];
        }
        return str.split(split);
    }

    public static String[] toStrArray(String str) {
        return toStrArray(str, ",");
    }


}
