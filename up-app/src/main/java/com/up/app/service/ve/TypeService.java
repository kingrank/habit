package com.up.app.service.ve;

import com.up.app.model.ve.Type;
import com.up.habit.service.HabitService;
import com.jfinal.log.Log;
import com.up.habit.controller.render.To;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.StrKit;
import com.up.habit.kit.ValidatorKit;


/**
 * 场地类型 业务类
 * @author 场地类型 on 2021-01-12 09:23:49
 */
public class TypeService extends HabitService<Type> {
    private final Log log=Log.getLog(TypeService.class);

    public static TypeService me = new TypeService();

    /**
     * TODO:新增场地类型
     *
     * @param type
     * @return
     */
    public To add(Type type) {
        if(StrKit.isBlank(type.getVename())){
            return To.fail("场馆名称不能为空!");
        }
        if(StrKit.isBlank(type.getOrgid())){
            return To.fail("所属组织机构不能为空!");
        }
        if(type.getStatus()==null){
            return To.fail("状态不能为空!");
        }
        return To.to(type.save());
    }

    /**
     * TODO:更新
     *
     * @param type
     * @return
     */
    public To edit(Type type) {
        if(type.getId()==null){
            return To.fail("编号不能为空!");
        }
        Type old=dao.findById(type.getId());
        if(old==null){
            return To.fail("记录不存在!");
        }
        old.setVename(StrKit.isBlank(type.getVename())?old.getVename():type.getVename());
        old.setOrgid(StrKit.isBlank(type.getOrgid())?old.getOrgid():type.getOrgid());
        old.setPic(StrKit.isBlank(type.getPic())?old.getPic():type.getPic());
        old.setStatus(type.getStatus()==null?old.getStatus():type.getStatus());
        old.setDesc(StrKit.isBlank(type.getDesc())?old.getDesc():type.getDesc());
        old.setLogdate(type.getLogdate()==null?old.getLogdate():type.getLogdate());
        return To.to(old.update());
    }

}
