package com.up.habit.config.anno;

import java.lang.annotation.*;

/**
 * TODO:控制器注解
 * <p>
 *
 * @author 王剑洪 on 2019/11/13 12:00
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Ctr {
    /**
     * 控制器访问路径
     *
     * @return
     */
    String value() default "";

    /**
     * 控制器视图陆路径
     *
     * @return
     */
    String view() default "";

    /**
     * 菜单图标
     *
     * @return
     */
    String icon() default "";

    /**
     * 控制器名称
     *
     * @return
     */
    String name() default "";

    /**
     * 控制器说明
     *
     * @return
     */
    String des() default "";

    AuthType auth() default AuthType.MENU;
}
