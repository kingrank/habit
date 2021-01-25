package com.up.app.service.ve;

import com.up.app.model.ve.Area;
import com.up.habit.service.HabitService;
import com.jfinal.log.Log;
import com.up.habit.controller.render.To;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.StrKit;
import com.up.habit.kit.ValidatorKit;


/**
 * 场地管理 业务类
 * @author 场地管理 on 2021-01-13 17:22:04
 */
public class AreaService extends HabitService<Area> {
    private final Log log=Log.getLog(AreaService.class);

    public static AreaService me = new AreaService();

    /**
     * TODO:新增场地管理
     *
     * @param area
     * @return
     */
    public To add(Area area) {
        if(StrKit.isBlank(area.getOrgid())){
            return To.fail("请选择组织机构!");
        }
        if(StrKit.isBlank(area.getName())){
            return To.fail("场地名称不能为空!");
        }
        if(area.getType()==null){
            return To.fail("场地类型不能为空!");
        }
        if(area.getStatus()==null){
            return To.fail("状态不能为空!");
        }
        if(area.getIsshow()==null){
            return To.fail("是否公开不能为空!");
        }
        if(area.getFeeid()==null){
            return To.fail("收费规则不能为空!");
        }
        return To.to(area.save());
    }

    /**
     * TODO:更新
     *
     * @param area
     * @return
     */
    public To edit(Area area) {
        if(area.getId()==null){
            return To.fail("主键不能为空!");
        }
        Area old=dao.findById(area.getId());
        if(old==null){
            return To.fail("记录不存在!");
        }
        old.setOrgid(StrKit.isBlank(area.getOrgid())?old.getOrgid():area.getOrgid());
        old.setName(StrKit.isBlank(area.getName())?old.getName():area.getName());
        old.setType(area.getType()==null?old.getType():area.getType());
        old.setStatus(area.getStatus()==null?old.getStatus():area.getStatus());
        old.setIsshow(area.getIsshow()==null?old.getIsshow():area.getIsshow());
        old.setFeeid(area.getFeeid()==null?old.getFeeid():area.getFeeid());
        old.setPic(StrKit.isBlank(area.getPic())?old.getPic():area.getPic());
        old.setDes(StrKit.isBlank(area.getDes())?old.getDes():area.getDes());
        old.setBusinesstime(StrKit.isBlank(area.getBusinesstime())?old.getBusinesstime():area.getBusinesstime());
        return To.to(old.update());
    }

}
