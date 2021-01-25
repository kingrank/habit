package com.up.app.controller.admin.cm;

import com.up.app.controller.admin.AdminController;
import com.up.habit.config.anno.*;
import com.up.app.model.cm.Config;
import com.up.app.service.cm.ConfigService;

/**
 * TODO:
 *
 * @author 王剑洪 on 2019/12/19 23:15
 */
@Directory(value = "系统设置", icon = "icon iconfont icon-setting-fill")
@Ctr(name = "系统参数", icon = "icon iconfont icon-detail-fill")
public class ConfigController extends AdminController {

    @ApiMethod("参数列表")
    @Params({
            @Param(name = "group", des = "组名"),
            @Param(name = "name", des = "参数名"),
            @Param(name = "editable", des = "是否内置,0-是,1-否", dataType = "Integer"),
            @Param(name = "start_create_time", des = "开始时间"),
            @Param(name = "end_create_time", des = "结束时间"),
    })
    public void page() {
        render(ConfigService.me.page(getModelAddTime(Config.class), num(), size()));
    }

    @ApiMethod(value = "新增配置", auth = AuthType.BUTTON)
    @Params({
            @Param(name = "group", des = "组名"),
            @Param(name = "name", des = "参数名"),
            @Param(name = "value", des = "参数名"),
            @Param(name = "editable", des = "是否内置,0-是,1-否", dataType = "Integer"),
            @Param(name = "sort", des = "排序", dataType = "Integer"),
            @Param(name = "remark", des = "备注")
    })
    public void add() {
        render(ConfigService.me.add(getModel(Config.class)));
    }

    @ApiMethod(value = "编辑配置", auth = AuthType.BUTTON)
    @Params({
            @Param(name = "id", des = "配置ID"),
            @Param(name = "group", des = "组名", required = false),
            @Param(name = "name", des = "参数名", required = false),
            @Param(name = "value", des = "参数名", required = false),
            @Param(name = "editable", des = "是否内置,0-是,1-否", dataType = "Integer", required = false),
            @Param(name = "sort", des = "排序", dataType = "Integer", required = false),
            @Param(name = "remark", des = "备注", required = false)
    })
    public void edit() {
        render(ConfigService.me.edit(getModel(Config.class)));
    }

    @ApiMethod(value = "删除配置", auth = AuthType.BUTTON)
    @Params({
            @Param(name = "ids", des = "配置ID数组"),
    })
    public void delete() {
        render(ConfigService.me.delete(getArray("ids")));
    }

}
