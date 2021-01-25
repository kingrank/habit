package com.up.app.controller.admin.ve;

import com.up.app.model.ve.Area;
import com.up.app.service.ve.AreaService;
import com.up.habit.config.anno.*;
import com.up.app.controller.admin.AdminController;

/**
* TODO:场地管理控制器
*
* @author 盛楠 on 2021-01-13 17:22:04
*/
@Directory(value = "场地管理", icon = "icon iconfont icon-location")
@Ctr(name = "场地", icon = "icon iconfont icon-location")
public class AreaController extends AdminController {

    @ApiMethod(value = "新增场地", auth = AuthType.BUTTON)
    public void add() {
        render(AreaService.me.add(getModel(Area.class)));
    }

    @ApiMethod(value = "删除场地", auth = AuthType.BUTTON)
    public void delete() {
        render(AreaService.me.delete(getArray("ids")));
    }

    @ApiMethod(value = "编辑场地", auth = AuthType.BUTTON)
    public void edit() {
        render(AreaService.me.edit(getModel(Area.class)));
    }

    @ApiMethod("场地列表-分页")
    public void page(){
        render(AreaService.me.pageByOrg(getModelAddTime(Area.class),num(),size(),this.getToken()));
    }

    @ApiMethod("场地详情")
    public void info(){
        render(AreaService.me.info(get("id")));
    }
}