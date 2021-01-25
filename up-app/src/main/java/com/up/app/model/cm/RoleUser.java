package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 用户和角色关联表
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class RoleUser extends DBModel<RoleUser> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_role_user";
    /**主键*/
    public final static String TABLE_PKS="role_id,user_id";
    /**角色ID*/
    public final static String ROLE_ID="role_id";
    /**用户ID*/
    public final static String USER_ID="user_id";
    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>role_id</td><td>角色ID</td></tr>")
            +("<tr><td>user_id</td><td>用户ID</td></tr>")
            +"</table>";

	/**
	 * 角色ID
	 */
	public RoleUser setRoleId(Integer roleId) {
		set("role_id", roleId);
		return this;
	}

	/**
	 * 角色ID
	 */
	public Integer getRoleId() {
		return getInt("role_id");
	}

	/**
	 * 用户ID
	 */
	public RoleUser setUserId(Integer userId) {
		set("user_id", userId);
		return this;
	}

	/**
	 * 用户ID
	 */
	public Integer getUserId() {
		return getInt("user_id");
	}


}
