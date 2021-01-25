package com.up.app.service.cm;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.up.habit.controller.render.To;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.StrKit;
import com.up.habit.service.HabitService;
import com.up.app.model.cm.Config;

/**
 * TODO:系统配置业务
 *
 * @author 王剑洪 on 2020/1/7 16:09
 */
public class ConfigService extends HabitService<Config> {

    public static ConfigService me = new ConfigService();

    public To add(Config config) {
        if (StrKit.isBlank(config.getId())) {
            return To.fail("配置参数编号不能为空!");
        }
        if (StrKit.isBlank(config.getName())) {
            return To.fail("配置参数名不能为空!");
        }
        if (StrKit.isBlank(config.getName())) {
            return To.fail("配置参数健值不能为空!");
        }
        if (!NumberKit.isSwitch(config.getEditable())) {
            return To.fail("编辑状态不正确!");
        }
        Config old = dao.findById(config.getId());
        if (old != null) {
            return To.fail("参数编号已存在");
        }
        return To.to(config.save());
    }

    public To edit(Config config) {
        if (StrKit.isBlank(config.getId())) {
            return To.fail("记录不存在!");
        }
        Config old = dao.findById(config.getId());
        if (old == null) {
            return To.fail("记录不存在!");
        }
        if (old.getEditable().intValue() == 1) {
            old.setGroup(StrKit.notBlank(config.getGroup()) ? config.getGroup() : old.getGroup());
            old.setName(StrKit.notBlank(config.getName()) ? config.getName() : old.getName());
            old.setValue(StrKit.notBlank(config.getValue()) ? config.getValue() : old.getValue());
            old.setEditable(NumberKit.isScope(config.getEditable(), 0, 1) ? config.getEditable() : old.getEditable());
            old.setRemark(StrKit.notBlank(config.getRemark()) ? config.getRemark() : old.getRemark());
        }
        old.setRemark(StrKit.notBlank(config.getRemark()) ? config.getRemark() : old.getRemark());
        old.setSort(NumberKit.isScope(config.getSort(), SORT_MIN, SORT_MAX) ? config.getSort() : old.getSort());
        return To.to(old.update());
    }

    public String getById(String id) {
        Config config = dao.findByIdByCache(id);
        if (config == null) {
            return null;
        }
        return config.getValue();
    }
}
