package com.up.habit.controller;

import com.jfinal.core.paragetter.Para;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.anno.Param;
import com.up.habit.config.anno.Params;
import com.up.habit.service.CommonService;

/**
 * TODO:通用控制器
 *
 * @author 王剑洪 on 2019/12/16 23:49
 */
@Ctr("/com")
public class CommonController extends HabitController {
    @ApiMethod("获取图片验证码")
    @Params({
            @Param(name = "length", des = "验证码长度", dataType = "Integer", required = false),
            @Param(name = "width", des = "验证码图片宽度", dataType = "Integer", required = false),
            @Param(name = "height", des = "验证码图片高度", dataType = "Integer", required = false)})
    public void captcha() {
        render(CommonService.me.captcha(getInt("length"), getInt("width"), getInt("height")));
    }

}
