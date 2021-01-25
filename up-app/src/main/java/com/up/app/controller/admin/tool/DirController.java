package com.up.app.controller.admin.tool;

import com.up.app.controller.admin.AdminController;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.Ctr;
import com.up.habit.expand.gen.model.Directory;
import com.up.app.service.tool.DirService;

/**
 * TODO:代码生成目录管理
 *
 * @author 王剑洪 on 2020/1/14 17:31
 */
@com.up.habit.config.anno.Directory(value = "系统工具", icon = "icon iconfont icon-batchfolding-fill")
@Ctr(name = "目录管理", icon = "icon iconfont icon-folder-fill")
public class DirController extends AdminController {

    @ApiMethod("获取列表")
    public void page() {
        render(DirService.me.page(getModelAddTime(Directory.class), num(), size()));
    }

    @ApiMethod("获取详情")
    public void info() {
        render(DirService.me.info(get("id")));
    }

    @ApiMethod("新增目录")
    public void add() {
        render(DirService.me.add(getModel(Directory.class)));
    }

    @ApiMethod("编辑目录")
    public void edit() {
        render(DirService.me.edit(getModel(Directory.class)));
    }

    @ApiMethod("删除目录")
    public void delete() {
        render(DirService.me.remove(getArray("ids")));
    }

}
