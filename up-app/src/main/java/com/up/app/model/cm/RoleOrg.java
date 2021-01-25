package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 角色机构权限
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class RoleOrg extends DBModel<RoleOrg> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_role_org";
    /**主键*/
    public final static String TABLE_PKS="role_id,org_id";
    /***/
    public final static String ROLE_ID="role_id";
    /***/
    public final static String ORG_ID="org_id";
    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>role_id</td><td></td></tr>")
            +("<tr><td>org_id</td><td></td></tr>")
            +"</table>";

	/**
	 * 
	 */
	public RoleOrg setRoleId(Integer roleId) {
		set("role_id", roleId);
		return this;
	}

	/**
	 *
	 */
	public Integer getRoleId() {
		return getInt("role_id");
	}

	/**
	 *
	 */
	public RoleOrg setOrgId(Integer orgId) {
		set("org_id", orgId);
		return this;
	}

	/**
	 *
	 */
	public Integer getOrgId() {
		return getInt("org_id");
	}


}
