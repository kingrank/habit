package com.up.app.controller.admin.dt;

import com.up.app.model.dt.Member;
import com.up.app.service.dt.MemberService;
import com.up.habit.config.anno.*;
import com.up.app.controller.admin.AdminController;

/**
* TODO:会员信息控制器
*
* @author 盛楠 on 2021-01-20 09:54:45
*/
@Directory(value = "数据管理", icon = "icon iconfont icon-detail")
@Ctr(name = "会员信息", icon = "icon iconfont icon-team")
public class MemberController extends AdminController {

    @ApiMethod(value = "新增会员信息", auth = AuthType.BUTTON)
    public void add() {
        render(MemberService.me.add(getModel(Member.class)));
    }

    @ApiMethod(value = "删除会员信息", auth = AuthType.BUTTON)
    public void delete() {
        render(MemberService.me.delete(getArray("ids")));
    }

    @ApiMethod(value = "编辑会员信息", auth = AuthType.BUTTON)
    public void edit() {
        render(MemberService.me.edit(getModel(Member.class)));
    }

    @ApiMethod("会员信息列表-分页")
    public void page(){
        render(MemberService.me.pageByOrg(getModelAddTime(Member.class),num(),size(),this.getToken()));
    }
    @ApiMethod("会员信息详情")
    public void info(){
        render(MemberService.me.info(get("id")));
    }
}