package com.up.app.service.cm;

import com.jfinal.kit.Kv;
import com.up.app.model.cm.Post;
import com.up.habit.service.HabitService;
import com.jfinal.log.Log;
import com.up.habit.controller.render.To;
import com.up.habit.kit.StrKit;

import java.util.List;


/**
 * 岗位信息 业务类
 *
 * @author 岗位信息 on 2020-01-19 01:04:51
 */
public class PostService extends HabitService<Post> {
    private final Log log = Log.getLog(PostService.class);

    public static PostService me = new PostService();

    /**
     * TODO:新增岗位信息
     *
     * @param post
     * @return
     */
    public To add(Post post) {
        if (StrKit.isBlank(post.getCode())) {
            return To.fail("编码不能为空!");
        }
        if (StrKit.isBlank(post.getName())) {
            return To.fail("名称不能为空!");
        }
        if (post.getSort() == null) {
            return To.fail("顺序不能为空!");
        }
        if (post.getState() == null) {
            return To.fail("状态不能为空!");
        }
        post.setIsDel(0);

        return To.to(post.save());
    }

    /**
     * TODO:更新
     *
     * @param post
     * @return
     */
    public To edit(Post post) {
        if (post.getId() == null) {
            return To.fail("岗位ID不能为空!");
        }
        Post old = dao.findById(post.getId());
        if (old == null) {
            return To.fail("记录不存在!");
        }
        old.setCode(StrKit.isBlank(post.getCode()) ? old.getCode() : post.getCode());
        old.setName(StrKit.isBlank(post.getName()) ? old.getName() : post.getName());
        old.setSort(post.getSort() == null ? old.getSort() : post.getSort());
        old.setState(post.getState() == null ? old.getState() : post.getState());
        old.setIsDel(post.getIsDel() == null ? old.getIsDel() : post.getIsDel());
        old.setRemark(StrKit.isBlank(post.getRemark()) ? old.getRemark() : post.getRemark());
        old.setCreateBy(post.getCreateBy() == null ? old.getCreateBy() : post.getCreateBy());
        old.setCreateTime(post.getCreateTime() == null ? old.getCreateTime() : post.getCreateTime());
        old.setModifyBy(post.getModifyBy() == null ? old.getModifyBy() : post.getModifyBy());
        old.setModifyTime(post.getModifyTime() == null ? old.getModifyTime() : post.getModifyTime());
        return To.to(old.update());
    }

    public To select() {
        List<Post> list = dao.template("select").findByCache();
        return To.ok(list);
    }

    public boolean verCount(Integer[] ids) {
        if (ids==null||ids.length==0){
            return false;
        }
        long count = dao.template("queryCountByIds", Kv.by("ids", ids)).queryLong();
        return count - ids.length == 0;
    }

}
