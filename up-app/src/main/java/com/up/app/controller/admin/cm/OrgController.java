package com.up.app.controller.admin.cm;

import com.up.app.controller.admin.AdminController;
import com.up.app.model.cm.User;
import com.up.app.service.cm.MenuService;
import com.up.habit.Habit;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.AuthType;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.anno.Directory;
import com.up.app.model.cm.Org;
import com.up.app.service.cm.OrgService;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/3 12:00
 */
@Directory(value = "系统设置", icon = "icon iconfont icon-setting-fill")
@Ctr(name = "机构管理", icon = "icon iconfont icon-group")
public class OrgController extends AdminController {

    @ApiMethod("机构下拉选项")
    public void treeSelect() {
        render(OrgService.me.treeSelect());
    }
    @ApiMethod("前端选择树结构根据父ID")
    public void treeSelectPid() {
        int id = get("id")!=null?getInt("id"):-1;
        if(id==-1){
            User u = Habit.getCache().get("loginUser",this.getToken());
            Object og = u.getOrgId();
            if(og!=null){
                id = (int)og;
            }
        }
        render(OrgService.me.treeSelectPid(id));
    }
    @ApiMethod(value = "获取机构列表")
    public void list() {
        render(OrgService.me.list(getModel(Org.class)));
    }


    @ApiMethod(value = "新增机构", auth = AuthType.BUTTON)
    public void add() {
        render(OrgService.me.add(getModel(Org.class)));
    }

    @ApiMethod(value = "编辑机构", auth = AuthType.BUTTON)
    public void edit() {
        render(OrgService.me.edit(getModel(Org.class)));
    }

    @ApiMethod(value = "删除机构", auth = AuthType.BUTTON)
    public void delete() {
        render(OrgService.me.remove(getIntArray("ids")));
    }
}
