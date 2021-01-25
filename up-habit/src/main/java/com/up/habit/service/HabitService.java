package com.up.habit.service;

import com.jfinal.kit.Kv;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Page;
import com.up.habit.Habit;
import com.up.habit.controller.render.To;
import com.up.habit.expand.db.model.DBModel;
import com.up.habit.kit.ClassKit;
import com.up.habit.kit.StrKit;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;


/**
 * TODO:
 * <p>
 *
 * @author 王剑洪 on 2019/11/26 17:06
 */
public class HabitService<M extends DBModel<M>> {
    public M dao;
    protected int SORT_MIN = 1;
    protected int SORT_MAX = 99999;

    public HabitService() {
        dao = initDao().dao();
    }

    /**
     * 初始化 DAO
     * 子类可以复写 自定义自己的DAO
     *
     * @return
     */
    protected M initDao() {
        Type type = ClassKit.getUsefulClass(getClass()).getGenericSuperclass();
        Class<M> modelClass = (Class<M>) ((ParameterizedType) type).getActualTypeArguments()[0];
        if (modelClass == null) {
            throw new RuntimeException("can not get model class name in HabitService");
        }
        //默认不通过AOP构建DAO，提升性能，若特殊需要重写initDao()方法即可
        return ClassKit.newInstance(modelClass, false);
    }


    public DbPro db() {
        return Db.use();
    }


    /**
     * Todo:分页查找
     *
     * @param m
     * @param pageNo
     * @param pageSize
     * @return
     */
    public To page(M m, int pageNo, int pageSize) {
        Page<M> page = dao.template("queryList", m).paginate(pageNo, pageSize);
        return To.ok(page);
    }
    /**
     * Todo:分页查找 限定组织架构
     *
     * @param m
     * @param pageNo
     * @param pageSize
     * @return
     */
    public To pageByOrg(M m, int pageNo, int pageSize,String token) {
        if(StrKit.isBlank(m.getStr("orgid"))){
            M u = Habit.getCache().get("loginUser",token);
            m.set("orgid",u.getStr("org_id"));
        }
        Page<M> page = dao.template("queryList", m).paginate(pageNo, pageSize);
        return To.ok(page);
    }

    /**
     * TODO:列表查询
     *
     * @param m
     * @return
     */
    public To list(M m) {
        List<M> list = dao.template("queryList", m).find();
        return To.ok(list);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    public To delete(Object[] ids) {
        if (ids.length < 1) {
            return To.fail("删除对象不能为空!");
        }
        String deleteByIds = "delete from " + dao.getTableName() + " where id in (#for(x:ids)#para(x)#(for.last?'':',')#end)";
        return To.to(dao.templateByString(deleteByIds, Kv.by("ids", ids)).update());
    }

    /**
     * @param ids
     * @return
     */
    public To remove(Object[] ids) {
        if (ids.length < 1) {
            return To.fail("删除对象不能为空!");
        }
        String removeByIds = "update " + dao.getTableName() + " set is_del=1 where id in (#for(x:ids)#para(x)#(for.last?'':',')#end)";
        return To.to(dao.templateByString(removeByIds, Kv.by("ids", ids)).update());
    }

    public To info(Object id) {
        return To.ok(dao.findById(id));
    }


}
