package com.up.app.service.cm;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.up.habit.Habit;
import com.up.habit.controller.render.To;
import com.up.habit.expand.db.kit.TreeKit;
import com.up.habit.expand.db.model.DaoTemplate;
import com.up.habit.kit.ArrayKit;
import com.up.habit.kit.NumberKit;
import com.up.habit.service.HabitService;
import com.up.app.model.cm.Dict;
import com.up.app.model.cm.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:
 *
 * @author 王剑洪 on 2019/12/19 23:05
 */
public class DictService extends HabitService<Dict> {

    public static DictService me = new DictService();


    /**
     * TODO:新增字典
     *
     * @param dict
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:03
     **/
    public To add(Dict dict) {
        if (StrKit.isBlank(dict.getId())) {
            return To.fail("字典编号不能为空");
        }
        if (!NumberKit.isSwitch(dict.getEditable())) {
            return To.fail("内置类型不正确");
        }
        if (!NumberKit.isSwitch(dict.getState())) {
            return To.fail("状态不正确");
        }
        if (dao.findById(dict.getId()) != null) {
            return To.fail("字典编号已存在!");
        }
        return To.to(dict.save());
    }

    /**
     * TODO:删除字典
     *
     * @param ids
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:03
     **/

    public To delete(String[] ids) {
        if (ids.length <= 0) {
            return To.fail("字典编号不能为空!");
        }
        long count = dao.template("queryCountByIdAndEditable", Kv.by("ids", ids)).queryLong();
        if (count > 0) {
            return To.fail("系统内置字典不可删除!");
        }
        Long childrenCount = dao.template("queryChildCount", Kv.by("ids", ids)).queryLong();
        if (childrenCount != null && childrenCount.longValue() > 0) {
            return To.fail("请先删除改字典底下类型");
        }
        return super.delete(ids);
    }

    /**
     * TODO:编辑字典
     *
     * @param dict
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:03
     **/
    public To edit(Dict dict) {
        if (StrKit.isBlank(dict.getId())) {
            return To.fail("字典编号不能为空!");
        }
        Dict oldDict = dao.findById(dict.getId());
        if (oldDict == null) {
            return To.fail("对应字典不存在!");
        }
        if (oldDict.getEditable().intValue() == 0) {
            //不可编辑状态
            oldDict.setSort(dict.getSort() == null ? oldDict.getSort() : dict.getSort());
            oldDict.setRemark(StrKit.isBlank(dict.getRemark()) ? oldDict.getRemark() : dict.getRemark());
        } else {
            //可编辑状态
            oldDict.setName(StrKit.isBlank(dict.getName()) ? oldDict.getName() : dict.getName());
            oldDict.setValue(StrKit.isBlank(dict.getValue()) ? oldDict.getValue() : dict.getValue());
            oldDict.setEditable(!NumberKit.isSwitch(dict.getEditable()) ? oldDict.getEditable() : dict.getEditable());
            oldDict.setState(!NumberKit.isSwitch(dict.getState()) ? oldDict.getState() : dict.getState());
            oldDict.setSort(dict.getSort() == null ? oldDict.getSort() : dict.getSort());
            oldDict.setRemark(StrKit.isBlank(dict.getRemark()) ? oldDict.getRemark() : dict.getRemark());
        }
        return To.to(oldDict.update());
    }


    /**
     * TODO:获取指定字典选项
     *
     * @param pId
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:04
     **/
    public To getByPId(String pId) {
        List<Dict> list = dao.template("queryByPId", Kv.by("pId", pId)).findByCache();
        return To.ok(list);
    }

    public To treeSelect() {
        List<Dict> list = dao.template("treeSelect").find();
        list = TreeKit.buildTree(list, "p_id", "id");
        return To.ok(list);
    }
}
