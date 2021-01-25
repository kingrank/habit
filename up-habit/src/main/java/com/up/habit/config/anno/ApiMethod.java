package com.up.habit.config.anno;

import java.lang.annotation.*;

/**
 * TODO:方法注解
 * <p>
 *
 * @author 王剑洪 on 2019/11/13 12:02
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ApiMethod {
    /**
     * 方法名称
     *
     * @return
     */
    String value() default "";

    /**
     * 请求方法
     *
     * @return
     */
    String httpMethod() default "post";

    /**
     * 返回内容
     *
     * @return
     */
    String response() default "";

    /**
     * 访问路径,空表示未系统默认路径
     *
     * @return
     */
    String path() default "";

    /**
     * 方法说明
     *
     * @return
     */
    String des() default "";

    /**
     * 权限
     */
    AuthType auth() default AuthType.NULL;

}
