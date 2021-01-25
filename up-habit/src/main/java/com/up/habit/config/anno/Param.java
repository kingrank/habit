package com.up.habit.config.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO:参数说明
 * <p>
 * @author 王剑洪 on 2019/11/13 13:55
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    /**
     * 参数名
     * @return
     */
    String name() default "";
    /**
     * 参数类型
     * @return
     */
    String dataType() default "string";
    /**
     * 参数说明
     * @return
     */
    String des() default "";
    /**
     * 参数默认值
     * @return
     */
    String defaultValue() default "";
    /**
     * 参数格式
     * @return
     */
    String format() default "";
    /**
     * 是否必传参数
     * @return
     */
    boolean required() default true;
}
