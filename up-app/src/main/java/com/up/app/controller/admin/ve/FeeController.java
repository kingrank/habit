package com.up.app.controller.admin.ve;

import com.up.app.model.ve.Fee;
import com.up.app.model.ve.Feeinterval;
import com.up.app.model.ve.Feescope;
import com.up.app.service.ve.FeeService;
import com.up.app.service.ve.FeeintervalService;
import com.up.app.service.ve.FeescopeService;
import com.up.habit.config.anno.*;
import com.up.app.controller.admin.AdminController;

/**
* TODO:收费规则控制器
*
* @author 盛楠 on 2021-01-14 15:38:51
*/
@Directory(value = "场地管理", icon = "icon iconfont icon-earth")
@Ctr(name = "收费规则", icon = "icon iconfont icon-redenvelope")
public class FeeController extends AdminController {

    @ApiMethod(value = "新增收费规则", auth = AuthType.BUTTON)
    public void add() {
        render(FeeService.me.add(getModel(Fee.class)));
    }

    @ApiMethod(value = "删除收费规则", auth = AuthType.BUTTON)
    public void delete() {
        render(FeeService.me.delete(getArray("ids")));
    }
    @ApiMethod(value = "删除收费规则全部", auth = AuthType.BUTTON)
    public void deleteFee() {
        render(FeeService.me.deleteAll(getArray("ids")));
    }
    @ApiMethod(value = "编辑收费规则", auth = AuthType.BUTTON)
    public void edit() {
        render(FeeService.me.edit(getModel(Fee.class)));
    }

    @ApiMethod("收费规则列表-分页")
    public void page(){
        render(FeeService.me.pageByOrg(getModelAddTime(Fee.class),num(),size(),this.getToken()));
    }

    @ApiMethod("收费规则详情")
    public void info(){
        render(FeeService.me.info(get("id")));
    }


    @ApiMethod(value = "新增计时费率", auth = AuthType.BUTTON)
    public void addInterval() {
        render(FeeintervalService.me.add(getModel(Feeinterval.class)));
    }

    @ApiMethod(value = "删除计时费率", auth = AuthType.BUTTON)
    public void deleteInterval() {
        render(FeeintervalService.me.delete(getArray("ids")));
    }

    @ApiMethod(value = "编辑计时费率", auth = AuthType.BUTTON)
    public void editInterval() {
        render(FeeintervalService.me.edit(getModel(Feeinterval.class)));
    }

    @ApiMethod("计时费率列表-分页")
    public void pageInterval(){
        render(FeeintervalService.me.page(getModelAddTime(Feeinterval.class),num(),size()));
    }

    @ApiMethod("计时费率详情")
    public void infoInterval(){
        render(FeeintervalService.me.info(get("id")));
    }


    @ApiMethod(value = "新增包段费率", auth = AuthType.BUTTON)
    public void addScope() {
        render(FeescopeService.me.add(getModel(Feescope.class)));
    }

    @ApiMethod(value = "删除包段费率", auth = AuthType.BUTTON)
    public void deleteScope() {
        render(FeescopeService.me.delete(getArray("ids")));
    }

    @ApiMethod(value = "编辑包段费率", auth = AuthType.BUTTON)
    public void editScope() {
        render(FeescopeService.me.edit(getModel(Feescope.class)));
    }

    @ApiMethod("包段费率列表-分页")
    public void pageScope(){
        render(FeescopeService.me.page(getModelAddTime(Feescope.class),num(),size()));
    }

    @ApiMethod("包段费率详情")
    public void infoScope(){
        render(FeescopeService.me.info(get("id")));
    }
}