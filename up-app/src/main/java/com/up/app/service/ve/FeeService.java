package com.up.app.service.ve;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.IAtom;
import com.up.app.model.ve.Fee;
import com.up.habit.expand.gen.HabitGenerator;
import com.up.habit.expand.gen.model.Directory;
import com.up.habit.expand.gen.model.Table;
import com.up.habit.expand.gen.model.TableColumn;
import com.up.habit.service.HabitService;
import com.jfinal.log.Log;
import com.up.habit.controller.render.To;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.StrKit;
import com.up.habit.kit.ValidatorKit;

import java.sql.SQLException;
import java.util.List;


/**
 * 收费规则 业务类
 * @author 收费规则 on 2021-01-14 15:38:51
 */
public class FeeService extends HabitService<Fee> {
    private final Log log=Log.getLog(FeeService.class);

    public static FeeService me = new FeeService();

    /**
     * TODO:新增收费规则
     *
     * @param fee
     * @return
     */
    public To add(Fee fee) {
        if(StrKit.isBlank(fee.getOrgid())){
            return To.fail("请选择组织机构!");
        }
        if(StrKit.isBlank(fee.getName())){
            return To.fail("费率名称不能为空!");
        }
        if(fee.getStatus()==null){
            return To.fail("状态不能为空!");
        }
        return To.to(fee.save());
    }

    /**
     * TODO:更新
     *
     * @param fee
     * @return
     */
    public To edit(Fee fee) {
        if(fee.getId()==null){
            return To.fail("ID不能为空!");
        }
        if(StrKit.isBlank(fee.getOrgid())){
            return To.fail("请选择组织机构!");
        }
        Fee old=dao.findById(fee.getId());
        if(old==null){
            return To.fail("记录不存在!");
        }
        old.setName(StrKit.isBlank(fee.getName())?old.getName():fee.getName());
        old.setStatus(fee.getStatus()==null?old.getStatus():fee.getStatus());
        old.setOrgid(fee.getOrgid()==null?old.getOrgid():fee.getOrgid());
        return To.to(old.update());
    }
    /**
     * 删除
     *
     * @param ids
     * @return
     */
    public To deleteAll(Object[] ids) {
        if (ids.length < 1) {
            return To.fail("删除对象不能为空!");
        }
        //是否存在被使用
        String hasUsed = "select count(1) ct from ve_area where feeid in (#for(x:ids)#para(x)#(for.last?'':',')#end)";
        long useCount = dao.templateByString(hasUsed,Kv.by("ids", ids)).queryLong();
        if (useCount>0) {
            return To.fail("计费被使用，不能删除!");
        }
        boolean res = db().tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String deleteByIds = "delete from ve_feeinterval where feeid in (#for(x:ids)#para(x)#(for.last?'':',')#end)";
                if(dao.templateByString(deleteByIds, Kv.by("ids", ids)).update()){
                    deleteByIds = "delete from ve_feescope where feeid in (#for(x:ids)#para(x)#(for.last?'':',')#end)";
                    if(dao.templateByString(deleteByIds, Kv.by("ids", ids)).update()){
                        deleteByIds = "delete from " + dao.getTableName() + " where id in (#for(x:ids)#para(x)#(for.last?'':',')#end)";
                        return dao.templateByString(deleteByIds, Kv.by("ids", ids)).update();
                    }
                }
                return false;
            }
        });
        return To.to(res);
    }
}
