package com.up.app.service.dt;

import com.up.app.model.dt.Order;
import com.up.app.util.CodeFactory;
import com.up.habit.service.HabitService;
import com.jfinal.log.Log;
import com.up.habit.controller.render.To;
import com.up.habit.kit.StrKit;


/**
 * 订单总览 业务类
 * @author 订单总览 on 2021-01-21 16:31:00
 */
public class OrderService extends HabitService<Order> {
    private final Log log=Log.getLog(OrderService.class);

    public static OrderService me = new OrderService();

    /**
     * TODO:新增订单总览
     *
     * @param order
     * @return
     */
    public To add(Order order) {
        //生成订单编号
        String orderId = CodeFactory.getOrderCode(order.getMemberid());
        order.setId(orderId);
        if(order.getOrgid()==null){
            return To.fail("所属机构不能为空!");
        }
        if(StrKit.isBlank(order.getMemberid())){
            return To.fail("会员编号不能为空!");
        }
        if(StrKit.isBlank(order.getMembername())){
            return To.fail("会员姓名不能为空!");
        }
        if(StrKit.isBlank(order.getMembertel())){
            return To.fail("联系方式不能为空!");
        }
        if(order.getProducttype()==null){
            return To.fail("消费类型不能为空!");
        }
        if(order.getPayment()==null){
            return To.fail("应收不能为空!");
        }
        if(order.getPaidin()==null){
            return To.fail("实收不能为空!");
        }
        if(order.getDiscount()==null){
            return To.fail("优惠不能为空!");
        }
        /*if(order.getStatus()==null){
            return To.fail("订单状态不能为空!");
        }
        if(order.getPaystate()==null){
            return To.fail("支付状态不能为空!");
        }
        if(order.getLogdate()==null){
            return To.fail("生成时间不能为空!");
        }
        if(order.getOverdate()==null){
            return To.fail("完成时间不能为空!");
        }*/
        return To.to(order.save());
    }

    /**
     * TODO:更新
     *
     * @param order
     * @return
     */
    public To edit(Order order) {
        if(StrKit.isBlank(order.getId())){
            return To.fail("订单编号不能为空!");
        }
        Order old=dao.findById(order.getId());
        if(old==null){
            return To.fail("记录不存在!");
        }
        old.setOrgid(order.getOrgid()==null?old.getOrgid():order.getOrgid());
        old.setMemberid(StrKit.isBlank(order.getMemberid())?old.getMemberid():order.getMemberid());
        old.setMembername(StrKit.isBlank(order.getMembername())?old.getMembername():order.getMembername());
        old.setMembertel(StrKit.isBlank(order.getMembertel())?old.getMembertel():order.getMembertel());
        old.setProducttype(order.getProducttype()==null?old.getProducttype():order.getProducttype());
        old.setPayment(order.getPayment()==null?old.getPayment():order.getPayment());
        old.setPaidin(order.getPaidin()==null?old.getPaidin():order.getPaidin());
        old.setDiscount(order.getDiscount()==null?old.getDiscount():order.getDiscount());
        old.setStatus(order.getStatus()==null?old.getStatus():order.getStatus());
        old.setPaystate(order.getPaystate()==null?old.getPaystate():order.getPaystate());
        old.setLogdate(order.getLogdate()==null?old.getLogdate():order.getLogdate());
        old.setOverdate(order.getOverdate()==null?old.getOverdate():order.getOverdate());
        return To.to(old.update());
    }

}
