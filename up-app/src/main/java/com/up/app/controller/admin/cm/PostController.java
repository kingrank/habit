package com.up.app.controller.admin.cm;

import com.up.app.controller.admin.AdminController;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.AuthType;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.anno.Directory;
import com.up.app.model.cm.Post;
import com.up.app.service.cm.PostService;

/**
 * TODO:岗位信息控制器
 *
 * @author 王剑洪 on 2020-01-19 01:04:51
 */
@Directory(value = "系统设置", icon = "icon iconfont icon-setting-fill")
@Ctr(name = "岗位信息", icon = "icon iconfont icon-insertrowbelow")
public class PostController extends AdminController {

    @ApiMethod(value = "新增岗位信息", auth = AuthType.BUTTON)
    public void add() {
        render(PostService.me.add(getModel(Post.class)));
    }

    @ApiMethod(value = "删除岗位信息", auth = AuthType.BUTTON)
    public void delete() {
        render(PostService.me.delete(getArray("ids")));
    }

    @ApiMethod(value = "编辑岗位信息", auth = AuthType.BUTTON)
    public void edit() {
        render(PostService.me.edit(getModel(Post.class)));
    }

    @ApiMethod("岗位信息列表-分页")
    public void page(){
        render(PostService.me.page(getModelAddTime(Post.class),num(),size()));
    }

    @ApiMethod("岗位信息详情")
    public void info(){
        render(PostService.me.info(get("id")));
    }

    @ApiMethod("岗位选择")
    public void select(){
        render(PostService.me.select());
    }
}