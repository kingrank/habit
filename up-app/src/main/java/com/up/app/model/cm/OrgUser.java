package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 部门用户关联表
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class OrgUser extends DBModel<OrgUser> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_org_user";
    /**主键*/
    public final static String TABLE_PKS="org_id,user_id";
    /***/
    public final static String ORG_ID="org_id";
    /***/
    public final static String USER_ID="user_id";
    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>org_id</td><td></td></tr>")
            +("<tr><td>user_id</td><td></td></tr>")
            +"</table>";

	/**
	 * 
	 */
	public OrgUser setOrgId(Integer orgId) {
		set("org_id", orgId);
		return this;
	}

	/**
	 *
	 */
	public Integer getOrgId() {
		return getInt("org_id");
	}

	/**
	 *
	 */
	public OrgUser setUserId(Integer userId) {
		set("user_id", userId);
		return this;
	}

	/**
	 *
	 */
	public Integer getUserId() {
		return getInt("user_id");
	}


}
