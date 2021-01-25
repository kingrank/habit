package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 部门用户关联表
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class DeptUser extends DBModel<DeptUser> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_dept_user";
    /**主键*/
    public final static String TABLE_PKS="dept_id,user_id";
    /***/
    public final static String DEPT_ID="dept_id";
    /***/
    public final static String USER_ID="user_id";
    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>dept_id</td><td></td></tr>")
            +("<tr><td>user_id</td><td></td></tr>")
            +"</table>";

	/**
	 * 
	 */
	public DeptUser setDeptId(Integer deptId) {
		set("dept_id", deptId);
		return this;
	}

	/**
	 *
	 */
	public Integer getDeptId() {
		return getInt("dept_id");
	}

	/**
	 *
	 */
	public DeptUser setUserId(Integer userId) {
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
