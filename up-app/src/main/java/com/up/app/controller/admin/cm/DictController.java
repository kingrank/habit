package com.up.app.controller.admin.cm;

import com.up.app.controller.admin.AdminController;
import com.up.habit.config.anno.*;
import com.up.app.model.cm.Dict;
import com.up.app.service.cm.DictService;

/**
 * TODO:
 *
 * @author 王剑洪 on 2019/12/19 23:15
 */
@Directory(value = "系统设置", icon = "icon iconfont icon-setting-fill")
@Ctr(name = "字典模块", icon = "icon iconfont icon-read-fill")
public class DictController extends AdminController {

    @ApiMethod("字典列表")
    @Params({
            @Param(name = "id", des = "字典ID"),
            @Param(name = "p_id", des = "父节点ID"),
            @Param(name = "name", des = "参数名"),
            @Param(name = "name", des = "参数名"),
            @Param(name = "editable", des = "是否内置,0-是,1-否", dataType = "Integer"),
            @Param(name = "state", des = "0-正常,1-禁用", dataType = "Integer"),
            @Param(name = "start_create_time", des = "开始时间"),
            @Param(name = "end_create_time", des = "结束时间"),
    })
    public void list() {
        render(DictService.me.list(getModelAddTime(Dict.class)));
    }

    @ApiMethod("字典列表-分页")
    @Params({
            @Param(name = "id", des = "字典ID"),
            @Param(name = "p_id", des = "父节点ID"),
            @Param(name = "name", des = "参数名"),
            @Param(name = "name", des = "参数名"),
            @Param(name = "editable", des = "是否内置,0-是,1-否", dataType = "Integer"),
            @Param(name = "state", des = "0-正常,1-禁用", dataType = "Integer"),
            @Param(name = "start_create_time", des = "开始时间"),
            @Param(name = "end_create_time", des = "结束时间"),
            @Param(name = "pageNo", des = "页码", dataType = "Integer"),
            @Param(name = "pageSize", des = "数量", dataType = "Integer"),
    })
    public void page() {
        render(DictService.me.page(getModelAddTime(Dict.class), num(), size()));
    }

    @ApiMethod(value = "新增字典", auth = AuthType.BUTTON)
    public void add() {
        render(DictService.me.add(getModel(Dict.class)));
    }

    @ApiMethod(value = "编辑字典", auth = AuthType.BUTTON)
    public void edit() {
        render(DictService.me.edit(getModel(Dict.class)));
    }

    @ApiMethod(value = "删除字典", auth = AuthType.BUTTON)
    public void delete() {
        render(DictService.me.delete(getArray("ids")));
    }

    @ApiMethod("获取对应类型字典")
    public void byPId() {
        render(DictService.me.getByPId(get("pId")));
    }

    public void treeSelect() {
        render(DictService.me.treeSelect());
    }
}
