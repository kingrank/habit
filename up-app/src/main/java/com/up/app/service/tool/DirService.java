package com.up.app.service.tool;

import com.up.habit.controller.render.To;
import com.up.habit.expand.gen.model.Directory;
import com.up.habit.kit.StrKit;
import com.up.habit.service.HabitService;

import java.util.List;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/14 17:30
 */
public class DirService extends HabitService<Directory> {
    public static DirService me = new DirService();

    public To add(Directory directory) {
        if (StrKit.isBlank(directory.getName())) {
            return To.fail("目录名不能为空");
        }
        if (StrKit.isBlank(directory.getPrefix())) {
            return To.fail("数据库表前缀不能为空!");
        }
        if (StrKit.isBlank(directory.getPath())) {
            directory.setPath(StrKit.toCamelCase(directory.getPrefix()));
        }
        if (StrKit.isBlank(directory.getIcon())) {
            directory.setIcon("icon iconfont icon-folder-fill");
        }
        return To.to(directory.save());
    }

    public To edit(Directory directory) {
        if (directory.getId() == null) {
            return To.fail("记录不存在!");
        }
        Directory old = dao.findById(directory.getId());
        if (old == null) {
            return To.fail("记录不存在!");
        }
        old.setName(StrKit.isBlank(directory.getName()) ? old.getName() : directory.getName());
        old.setPath(StrKit.isBlank(directory.getPath()) ? old.getPath() : directory.getPath());
        old.setIcon(StrKit.isBlank(directory.getIcon()) ? old.getIcon() : directory.getIcon());
        old.setPrefix(StrKit.isBlank(directory.getPrefix()) ? old.getPrefix() : directory.getPrefix());
        return To.to(old.update());
    }

    public List<Directory> getTablePrefix() {
        List<Directory> list = dao.template("queryPrefix").find();
        return list;
    }


}
