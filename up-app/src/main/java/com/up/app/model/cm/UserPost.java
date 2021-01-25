package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 用户与岗位关联表
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class UserPost extends DBModel<UserPost> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_user_post";
    /**主键*/
    public final static String TABLE_PKS="user_id,post_id";
    /**用户ID*/
    public final static String USER_ID="user_id";
    /**岗位ID*/
    public final static String POST_ID="post_id";
    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>user_id</td><td>用户ID</td></tr>")
            +("<tr><td>post_id</td><td>岗位ID</td></tr>")
            +"</table>";

	/**
	 * 用户ID
	 */
	public UserPost setUserId(Integer userId) {
		set("user_id", userId);
		return this;
	}

	/**
	 * 用户ID
	 */
	public Integer getUserId() {
		return getInt("user_id");
	}

	/**
	 * 岗位ID
	 */
	public UserPost setPostId(Integer postId) {
		set("post_id", postId);
		return this;
	}

	/**
	 * 岗位ID
	 */
	public Integer getPostId() {
		return getInt("post_id");
	}


}
