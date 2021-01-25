package com.up.app.service.dt;

import com.up.app.model.dt.Orderdetail;
import com.up.habit.service.HabitService;
import com.jfinal.log.Log;
import com.up.habit.controller.render.To;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.StrKit;
import com.up.habit.kit.ValidatorKit;


/**
 * 订单明细 业务类
 * @author 订单明细 on 2021-01-21 16:33:21
 */
public class OrderdetailService extends HabitService<Orderdetail> {
    private final Log log=Log.getLog(OrderdetailService.class);

    public static OrderdetailService me = new OrderdetailService();

    /**
     * TODO:新增订单明细
     *
     * @param orderdetail
     * @return
     */
    public To add(Orderdetail orderdetail) {
        if(StrKit.isBlank(orderdetail.getId())){
            return To.fail("清单编号不能为空!");
        }
        if(StrKit.isBlank(orderdetail.getOrderid())){
            return To.fail("订单编号不能为空!");
        }
        if(orderdetail.getProductid()==null){
            return To.fail("商品编号不能为空!");
        }
        if(StrKit.isBlank(orderdetail.getProductname())){
            return To.fail("商品名称不能为空!");
        }
        if(orderdetail.getPrice()==null){
            return To.fail("单价不能为空!");
        }
        if(orderdetail.getNum()==null){
            return To.fail("数量不能为空!");
        }
        if(orderdetail.getPayment()==null){
            return To.fail("应收不能为空!");
        }
        if(orderdetail.getPaidin()==null){
            return To.fail("实收不能为空!");
        }
        if(orderdetail.getDiscount()==null){
            return To.fail("优惠不能为空!");
        }
        if(orderdetail.getDiscountdesc()==null){
            return To.fail("优惠说明不能为空!");
        }
        if(orderdetail.getPointpayment()==null){
            return To.fail("积分抵扣不能为空!");
        }
        if(orderdetail.getPointadd()==null){
            return To.fail("积分增加不能为空!");
        }
        if(orderdetail.getStatus()==null){
            return To.fail("订单状态不能为空!");
        }
        if(orderdetail.getPaystate()==null){
            return To.fail("支付状态不能为空!");
        }
        if(orderdetail.getOvertime()==null){
            return To.fail("完成时间不能为空!");
        }
        if(orderdetail.getLogdate()==null){
            return To.fail("创建时间不能为空!");
        }
        if(StrKit.isBlank(orderdetail.getDesc())){
            return To.fail("备注不能为空!");
        }
        return To.to(orderdetail.save());
    }

    /**
     * TODO:更新
     *
     * @param orderdetail
     * @return
     */
    public To edit(Orderdetail orderdetail) {
        if(StrKit.isBlank(orderdetail.getId())){
            return To.fail("清单编号不能为空!");
        }
        Orderdetail old=dao.findById(orderdetail.getId());
        if(old==null){
            return To.fail("记录不存在!");
        }
        old.setOrderid(StrKit.isBlank(orderdetail.getOrderid())?old.getOrderid():orderdetail.getOrderid());
        old.setProductid(orderdetail.getProductid()==null?old.getProductid():orderdetail.getProductid());
        old.setProductname(StrKit.isBlank(orderdetail.getProductname())?old.getProductname():orderdetail.getProductname());
        old.setPrice(orderdetail.getPrice()==null?old.getPrice():orderdetail.getPrice());
        old.setNum(orderdetail.getNum()==null?old.getNum():orderdetail.getNum());
        old.setPayment(orderdetail.getPayment()==null?old.getPayment():orderdetail.getPayment());
        old.setPaidin(orderdetail.getPaidin()==null?old.getPaidin():orderdetail.getPaidin());
        old.setDiscount(orderdetail.getDiscount()==null?old.getDiscount():orderdetail.getDiscount());
        old.setDiscountdesc(orderdetail.getDiscountdesc()==null?old.getDiscountdesc():orderdetail.getDiscountdesc());
        old.setPointpayment(orderdetail.getPointpayment()==null?old.getPointpayment():orderdetail.getPointpayment());
        old.setPointadd(orderdetail.getPointadd()==null?old.getPointadd():orderdetail.getPointadd());
        old.setStatus(orderdetail.getStatus()==null?old.getStatus():orderdetail.getStatus());
        old.setPaystate(orderdetail.getPaystate()==null?old.getPaystate():orderdetail.getPaystate());
        old.setOvertime(orderdetail.getOvertime()==null?old.getOvertime():orderdetail.getOvertime());
        old.setLogdate(orderdetail.getLogdate()==null?old.getLogdate():orderdetail.getLogdate());
        old.setDesc(StrKit.isBlank(orderdetail.getDesc())?old.getDesc():orderdetail.getDesc());
        return To.to(old.update());
    }

}
