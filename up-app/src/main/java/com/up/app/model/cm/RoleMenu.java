package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 角色菜单权限
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class RoleMenu extends DBModel<RoleMenu> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_role_menu";
    /**主键*/
    public final static String TABLE_PKS="role_id,menu_id";
    /**角色ID*/
    public final static String ROLE_ID="role_id";
    /**菜单ID*/
    public final static String MENU_ID="menu_id";
    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>role_id</td><td>角色ID</td></tr>")
            +("<tr><td>menu_id</td><td>菜单ID</td></tr>")
            +"</table>";

	/**
	 * 角色ID
	 */
	public RoleMenu setRoleId(Integer roleId) {
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
	 * 菜单ID
	 */
	public RoleMenu setMenuId(String menuId) {
		set("menu_id", menuId);
		return this;
	}

	/**
	 * 菜单ID
	 */
	public String getMenuId() {
		return getStr("menu_id");
	}


}
