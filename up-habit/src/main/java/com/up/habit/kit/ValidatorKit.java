package com.up.habit.kit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO:验证
 * <p>
 * @author 王剑洪 on 2019/11/27 11:02
 */
public class ValidatorKit {
    //邮箱正则
    protected static final String emailAddressPattern = "\\b(^['_A-Za-z0-9-]+(\\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

    /**
     * TODO:判断是否手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        return mobile.length() == 11;
    }

    public static boolean isEmail(String email) {
        return regex(email, emailAddressPattern, false);
    }


    /**
     * TODO:正则验证
     *
     * @param value           验证字符串
     * @param regExpression   正则表达式
     * @param isCaseSensitive 是否大小写敏感
     * @return
     */
    public static boolean regex(String value, String regExpression, boolean isCaseSensitive) {
        if (StrKit.isBlank(value)) {
            return false;
        }
        Pattern pattern = isCaseSensitive ? Pattern.compile(regExpression) : Pattern.compile(regExpression, 2);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }


    public static boolean isNull(Object o) {
        return o == null;
    }

    public static String genConditionIN(String str) {
        if (StrKit.isBlank(str)) {
            return "";
        }
        return str.replace(",", "','");
    }

    public static Boolean has(Object value, Object... values) {
        for (Object o : values) {
            if (o.toString().equals(value.toString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasNull(Object... obs) {
        for (Object o : obs) {
            if (o instanceof String) {
                if (StrKit.isBlank((String) o)) {
                    return true;
                }
            } else {
                if (o == null) {
                    return true;
                }

            }
        }
        return false;
    }
}
