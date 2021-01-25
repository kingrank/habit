package com.up.app.controller.admin.dt;

import com.up.app.model.dt.Order;
import com.up.app.model.dt.Orderdetail;
import com.up.app.service.dt.OrderService;
import com.up.app.service.dt.OrderdetailService;
import com.up.habit.config.anno.*;
import com.up.app.controller.admin.AdminController;

/**
* TODO:订单总览控制器
*
* @author 盛楠 on 2021-01-21 16:31:00
*/
@Directory(value = "数据管理", icon = "icon iconfont icon-detail")
@Ctr(name = "订单总览", icon = "icon iconfont icon-filedone")
public class OrderController extends AdminController {

    @ApiMethod(value = "新增订单总览", auth = AuthType.BUTTON)
    public void add() {
        render(OrderService.me.add(getModel(Order.class)));
    }

    @ApiMethod(value = "删除订单总览", auth = AuthType.BUTTON)
    public void delete() {
        render(OrderService.me.delete(getArray("ids")));
    }

    @ApiMethod(value = "编辑订单总览", auth = AuthType.BUTTON)
    public void edit() {
        render(OrderService.me.edit(getModel(Order.class)));
    }

    @ApiMethod("订单总览列表-分页")
    public void page(){
        render(OrderService.me.pageByOrg(getModelAddTime(Order.class),num(),size(),this.getToken()));
    }

    @ApiMethod("订单总览详情")
    public void info(){
        render(OrderService.me.info(get("id")));
    }

    @ApiMethod(value = "新增订单明细", auth = AuthType.BUTTON)
    public void addDetail() {
        render(OrderdetailService.me.add(getModel(Orderdetail.class)));
    }

    @ApiMethod(value = "删除订单明细", auth = AuthType.BUTTON)
    public void deleteDetail() {
        render(OrderdetailService.me.delete(getArray("ids")));
    }

    @ApiMethod(value = "编辑订单明细", auth = AuthType.BUTTON)
    public void editDetail() {
        render(OrderdetailService.me.edit(getModel(Orderdetail.class)));
    }

    @ApiMethod("订单明细列表-分页")
    public void pageDetail(){
        render(OrderdetailService.me.page(getModelAddTime(Orderdetail.class),num(),size()));
    }

    @ApiMethod("订单明细详情")
    public void infoDetail(){
        render(OrderdetailService.me.info(get("id")));
    }
}