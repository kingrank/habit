package com.up.app.service.ve;

import com.up.app.model.ve.Feescope;
import com.up.habit.service.HabitService;
import com.jfinal.log.Log;
import com.up.habit.controller.render.To;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.StrKit;
import com.up.habit.kit.ValidatorKit;


/**
 * 包段费率 业务类
 * @author 包段费率 on 2021-01-14 15:47:43
 */
public class FeescopeService extends HabitService<Feescope> {
    private final Log log=Log.getLog(FeescopeService.class);

    public static FeescopeService me = new FeescopeService();

    /**
     * TODO:新增包段费率
     *
     * @param feescope
     * @return
     */
    public To add(Feescope feescope) {
        //自动生成
        feescope.setId(null);
        if(feescope.getFeeid()==null){
            return To.fail("所属收费规则不能为空!");
        }
        if(feescope.getType()==null){
            return To.fail("费率类型不能为空!");
        }
        if(feescope.getStartdate()==null){
            return To.fail("开始日期不能为空!");
        }
        if(feescope.getEnddate()==null){
            return To.fail("结束日期不能为空!");
        }
        if(feescope.getStarttiime()==null){
            return To.fail("开始时间不能为空!");
        }
        if(feescope.getEndtime()==null){
            return To.fail("结束时间不能为空!");
        }
        if(feescope.getPrice()==null){
            return To.fail("单价不能为空!");
        }
        if(feescope.getVipprice()==null){
            return To.fail("会员价不能为空!");
        }
        if(feescope.getPointstype()==null){
            return To.fail("积分类型不能为空!");
        }
        if(feescope.getPointstype()==1){
            if(feescope.getPointsval()==null){
                return To.fail("积分值不能为空!");
            }
            if(feescope.getPointstop()==null){
                return To.fail("积分上限不能为空!");
            }
        }
        if(feescope.getPointstype()==1){
            if(feescope.getPointsval()==null){
                return To.fail("积分值不能为空!");
            }
        }
        if(feescope.getPointdeduction()==null){
            return To.fail("抵扣类型不能为空!");
        }
        if(feescope.getPointdeduction()==1){
            if(feescope.getPointdeductionval()==null){
                return To.fail("抵扣值不能为空!");
            }
            if(feescope.getPointdeductiontop()==null){
                return To.fail("抵扣上限不能为空!");
            }
        }
        if(feescope.getStatus()==null){
            return To.fail("状态不能为空!");
        }
        return To.to(feescope.save());
    }

    /**
     * TODO:更新
     *
     * @param feescope
     * @return
     */
    public To edit(Feescope feescope) {
        if(feescope.getId()==null){
            return To.fail("ID不能为空!");
        }
        Feescope old=dao.findById(feescope.getId());
        if(old==null){
            return To.fail("记录不存在!");
        }
        old.setFeeid(feescope.getFeeid()==null?old.getFeeid():feescope.getFeeid());
        old.setType(feescope.getType()==null?old.getType():feescope.getType());
        old.setStartdate(feescope.getStartdate()==null?old.getStartdate():feescope.getStartdate());
        old.setEnddate(feescope.getEnddate()==null?old.getEnddate():feescope.getEnddate());
        old.setStarttiime(feescope.getStarttiime()==null?old.getStarttiime():feescope.getStarttiime());
        old.setEndtime(feescope.getEndtime()==null?old.getEndtime():feescope.getEndtime());
        old.setPrice(feescope.getPrice()==null?old.getPrice():feescope.getPrice());
        old.setVipprice(feescope.getVipprice()==null?old.getVipprice():feescope.getVipprice());
        old.setPointstype(feescope.getPointstype()==null?old.getPointstype():feescope.getPointstype());
        old.setPointsval(feescope.getPointsval()==null?old.getPointsval():feescope.getPointsval());
        old.setPointstop(feescope.getPointstop()==null?old.getPointstop():feescope.getPointstop());
        old.setPointdeduction(feescope.getPointdeduction()==null?old.getPointdeduction():feescope.getPointdeduction());
        old.setPointdeductionval(feescope.getPointdeductionval()==null?old.getPointdeductionval():feescope.getPointdeductionval());
        old.setPointdeductiontop(feescope.getPointdeductiontop()==null?old.getPointdeductiontop():feescope.getPointdeductiontop());
        old.setStatus(feescope.getStatus()==null?old.getStatus():feescope.getStatus());
        return To.to(old.update());
    }

}
