package com.up.app.service.cm;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.up.habit.controller.render.To;
import com.up.habit.expand.db.kit.TreeKit;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.StrKit;
import com.up.habit.service.HabitService;
import com.up.app.model.cm.Org;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:机构
 *
 * @author 王剑洪 on 2019/12/19 23:05
 */
public class OrgService extends HabitService<Org> {

    public static OrgService me = new OrgService();

    /**
     * TODO:查询列表
     *
     * @param org
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:02
     **/
    @Override
    public To list(Org org) {
        List<Org> list = dao.template("queryList", org).findByCache();
        list = TreeKit.buildTree(list, "p_id", "id");
        return To.ok(list);
    }


    /**
     * TODO:获取机构下拉书列表
     *
     * @param
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:01
     **/
    public To treeSelect() {
        List<Org> list = dao.template("treeSelect").findByCache();
        list = TreeKit.buildTree(list, "p_id", "id");
        return To.ok(list);
    }
    /**
     * TODO:获取机构下拉书列表
     *
     * @param
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:01
     **/
    public To treeSelectPid(int id) {
        if(id==-1){
            return treeSelect();
        }
        List<Org> list = dao.template("treeSelect").findByCache();
        list = TreeKit.buildTree(list, "p_id", "id");
        List<Org> listPid = new ArrayList<Org>();
        eachTree(list,id,listPid);
        return To.ok(listPid);
    }
    public void eachTree(List<Org> list,int id,List<Org> newlist){
        for(Org o:list) {
            if (o.getId() == id) {
                newlist.add(o);
            } else {
                if (o.getBoolean("hasChildren")) {
                    eachTree((List<Org>) o.get("children"), id,newlist);
                }
            }
        }
    }
    /**
     * TODO:新增机构
     *
     * @param org
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:02
     **/
    public To add(Org org) {
        if (org.getId() != null) {
            org.remove("id");
        }
        if (org.getPId() == null) {
            return To.fail("上级机构不能为空");
        }
        if (StrKit.isBlank(org.getName())) {
            return To.fail("机构名称不能为空!");
        }
        if (!NumberKit.isScope(org.getState(), 0, 1)) {
            return To.fail("机构状态不正确");
        }
        if (!NumberKit.isScope(org.getSort(), SORT_MIN, SORT_MAX)) {
            return To.fail("排序值不正确");
        }
        if (org.getPId().intValue()==0){
            org.setAncestors("0");
        }else {
            Org parent = dao.findById(org.getPId());
            if (parent == null) {
                return To.fail("上级机构不存在");
            }
            org.setAncestors(parent.getAncestors() + "," + org.getPId());
        }

        org.setIsDel(0);
        return To.to(org.save());
    }

    /**
     * TODO:编辑机构
     *
     * @param org
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:02
     **/
    public To edit(Org org) {
        if (org.getId() == null) {
            return To.fail("记录不存在!");
        }
        if (org.getPId() == null) {
            return To.fail("上级机构不能为空");
        } else {
            if (org.getPId().intValue()==0){
                org.setAncestors("0");
            }else {
                Org parent = dao.findById(org.getPId());
                if (parent == null) {
                    return To.fail("上级机构不存在!");
                }
                org.setAncestors(parent.getAncestors() + "," + org.getPId());
            }

        }
        Org old = dao.findById(org.getId());
        if (old == null) {
            return To.fail("记录不存在");
        }
        if (StrKit.notBlank(org.getName())) {
            old.setName(org.getName());
        }
        if (NumberKit.isScope(org.getState(), 0, 1)) {
            old.setState(org.getState());
        }
        if (!NumberKit.isScope(org.getSort(), SORT_MIN, SORT_MAX)) {
            old.setSort(org.getSort());
        }

        return To.to(org.update());
    }

    /**
     * TODO:移除机构
     *
     * @param ids
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:02
     **/
    @Override
    public To remove(Object[] ids) {

        return super.remove(ids);
    }
}
