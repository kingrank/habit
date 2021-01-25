package com.up.app.controller.admin.ve;

import com.up.app.model.cm.User;
import com.up.app.model.ve.Type;
import com.up.app.service.ve.TypeService;
import com.up.habit.Habit;
import com.up.habit.config.anno.*;
import com.up.app.controller.admin.AdminController;
import com.up.habit.kit.StrKit;

/**
* TODO:场地类型控制器
*
* @author 盛楠 on 2021-01-12 09:23:49
*/
@Directory(value = "场地管理", icon = "icon iconfont icon-earth")
@Ctr(name = "场地类型", icon = "icon iconfont icon-insertrowbelow")
public class TypeController extends AdminController {

    @ApiMethod(value = "新增场地类型", auth = AuthType.BUTTON)
    public void add() {
        render(TypeService.me.add(getModel(Type.class)));
    }

    @ApiMethod(value = "删除场地类型", auth = AuthType.BUTTON)
    public void delete() {
        render(TypeService.me.delete(getArray("ids")));
    }

    @ApiMethod(value = "编辑场地类型", auth = AuthType.BUTTON)
    public void edit() {
        render(TypeService.me.edit(getModel(Type.class)));
    }

    @ApiMethod("场地类型列表-分页")
    public void page(){
        render(TypeService.me.pageByOrg(getModelAddTime(Type.class),num(),size(),this.getToken()));
    }
    @ApiMethod("场地类型详情")
    public void info(){
        render(TypeService.me.info(get("id")));
    }
}