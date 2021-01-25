package com.up.app.service.ve;

import com.up.app.model.ve.Feeinterval;
import com.up.habit.service.HabitService;
import com.jfinal.log.Log;
import com.up.habit.controller.render.To;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.StrKit;
import com.up.habit.kit.ValidatorKit;


/**
 * 计时费率 业务类
 * @author 计时费率 on 2021-01-14 15:46:51
 */
public class FeeintervalService extends HabitService<Feeinterval> {
    private final Log log=Log.getLog(FeeintervalService.class);

    public static FeeintervalService me = new FeeintervalService();

    /**
     * TODO:新增计时费率
     *
     * @param feeinterval
     * @return
     */
    public To add(Feeinterval feeinterval) {
        //自动生成
        feeinterval.setId(null);
        if(feeinterval.getFeeid()==null){
            return To.fail("所属收费规则不能为空!");
        }
        if(feeinterval.getType()==null){
            return To.fail("费率类型不能为空!");
        }
        if(feeinterval.getStartdate()==null){
            return To.fail("开始日期不能为空!");
        }
        if(feeinterval.getEnddate()==null){
            return To.fail("结束日期不能为空!");
        }
        if(feeinterval.getStarttiime()==null){
            return To.fail("开始时间不能为空!");
        }
        if(feeinterval.getEndtime()==null){
            return To.fail("结束时间不能为空!");
        }
        if(feeinterval.getFeecut()==null){
            return To.fail("收费界限不能为空!");
        }
        if(feeinterval.getUnit()==null){
            return To.fail("收费单位不能为空!");
        }
        if(feeinterval.getUnitprice()==null){
            return To.fail("单价不能为空!");
        }
        if(feeinterval.getVipunitprice()==null){
            return To.fail("会员价不能为空!");
        }
        if(feeinterval.getPointstype()==null){
            return To.fail("积分类型不能为空!");
        }
        if(feeinterval.getPointstype()==1){
            if(feeinterval.getPointsval()==null){
                return To.fail("积分值不能为空!");
            }
            if(feeinterval.getPointstop()==null){
                return To.fail("积分上限不能为空!");
            }
        }
        if(feeinterval.getPointstype()==2){
            if(feeinterval.getPointsval()==null){
                return To.fail("积分值不能为空!");
            }
        }

        if(feeinterval.getPointdeduction()==null){
            return To.fail("抵扣类型不能为空!");
        }
        if(feeinterval.getPointdeduction()==1){
            if(feeinterval.getPointdeductionval()==null){
                return To.fail("抵扣值不能为空!");
            }
            if(feeinterval.getPointdeductiontop()==null){
                return To.fail("抵扣上限不能为空!");
            }
        }
        if(feeinterval.getStatus()==null){
            return To.fail("状态不能为空!");
        }
        return To.to(feeinterval.save());
    }

    /**
     * TODO:更新
     *
     * @param feeinterval
     * @return
     */
    public To edit(Feeinterval feeinterval) {
        if(feeinterval.getId()==null){
            return To.fail("ID不能为空!");
        }
        Feeinterval old=dao.findById(feeinterval.getId());
        if(old==null){
            return To.fail("记录不存在!");
        }
        old.setFeeid(feeinterval.getFeeid()==null?old.getFeeid():feeinterval.getFeeid());
        old.setType(feeinterval.getType()==null?old.getType():feeinterval.getType());
        old.setStartdate(feeinterval.getStartdate()==null?old.getStartdate():feeinterval.getStartdate());
        old.setEnddate(feeinterval.getEnddate()==null?old.getEnddate():feeinterval.getEnddate());
        old.setStarttiime(feeinterval.getStarttiime()==null?old.getStarttiime():feeinterval.getStarttiime());
        old.setEndtime(feeinterval.getEndtime()==null?old.getEndtime():feeinterval.getEndtime());
        old.setFreetime(feeinterval.getFreetime()==null?old.getFreetime():feeinterval.getFreetime());
        old.setFeecut(feeinterval.getFeecut()==null?old.getFeecut():feeinterval.getFeecut());
        old.setUnit(feeinterval.getUnit()==null?old.getUnit():feeinterval.getUnit());
        old.setUnitprice(feeinterval.getUnitprice()==null?old.getUnitprice():feeinterval.getUnitprice());
        old.setVipunitprice(feeinterval.getVipunitprice()==null?old.getVipunitprice():feeinterval.getVipunitprice());
        old.setPointstype(feeinterval.getPointstype()==null?old.getPointstype():feeinterval.getPointstype());
        old.setPointsval(feeinterval.getPointsval()==null?old.getPointsval():feeinterval.getPointsval());
        old.setPointstop(feeinterval.getPointstop()==null?old.getPointstop():feeinterval.getPointstop());
        old.setPointdeduction(feeinterval.getPointdeduction()==null?old.getPointdeduction():feeinterval.getPointdeduction());
        old.setPointdeductionval(feeinterval.getPointdeductionval()==null?old.getPointdeductionval():feeinterval.getPointdeductionval());
        old.setPointdeductiontop(feeinterval.getPointdeductiontop()==null?old.getPointdeductiontop():feeinterval.getPointdeductiontop());
        old.setStatus(feeinterval.getStatus()==null?old.getStatus():feeinterval.getStatus());
        return To.to(old.update());
    }

}
