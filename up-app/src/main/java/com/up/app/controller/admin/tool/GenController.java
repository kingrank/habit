package com.up.app.controller.admin.tool;

import com.jfinal.render.Render;
import com.up.app.controller.admin.AdminController;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.AuthType;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.anno.Directory;
import com.up.habit.expand.gen.model.Table;
import com.up.app.service.tool.GenService;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * TODO:
 *
 * @author 王剑洪 on 2019/12/15 21:52
 */
@Directory(value = "系统工具", icon = "icon iconfont icon-batchfolding-fill")
@Ctr(name = "代码生成", icon = "icon iconfont icon-code")
public class GenController extends AdminController {

    @ApiMethod(value = "获取已导入的列表")
    public void page() {
        render(GenService.me.page(getModelAddTime(Table.class), num(), size()));
    }

    @ApiMethod(value = "获取所有数据库表格")
    public void allTable() {
        render(GenService.me.getAllTable(getModelAddTime(Table.class), num(), size()));
    }

    @ApiMethod(value = "数据库表导入", auth = AuthType.BUTTON)
    public void addImport() {
        render(GenService.me.addBatch(getArray("ids")));
    }

    @ApiMethod("获取详情")
    public void info() {
        render(GenService.me.tableInfo(getInt("tableId")));
    }

    @ApiMethod(value = "编辑", auth = AuthType.BUTTON)
    public void edit() {
        render(GenService.me.edit(getModel(Table.class), get("columns")));
    }

    @ApiMethod(value = "预览", auth = AuthType.BUTTON)
    public void preview() {
        render(GenService.me.preview(getInt("tableId")));
    }

    @ApiMethod(value = "代码生成", auth = AuthType.BUTTON)
    public void genCode() {
        byte[] data = GenService.me.genCode(get("tableIds"));
        String fileName = "habit.zip";
        render(new Render() {
            @Override
            public void render() {
                response.reset();
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                response.addHeader("Content-Length", "" + data.length);
                response.setContentType("application/octet-stream; charset=UTF-8");
                try {
                    IOUtils.write(data, response.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @ApiMethod(value = "删除", auth = AuthType.BUTTON)
    public void delete() {
        render(GenService.me.delete(getArray("ids")));
    }

}
