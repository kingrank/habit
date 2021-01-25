package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 角色信息表
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class Role extends DBModel<Role> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_role";
    /**主键*/
    public final static String TABLE_PKS="id";
    /**角色ID*/
    public final static String ID="id";
    /**角色名称*/
    public final static String NAME="name";
    /**显示顺序@0-9999*/
    public final static String SORT="sort";
    /**数据范围@1:全部数据权限,2:自定数据权限,3:本部门数据权限,4:本部门及以下数据权限*/
    public final static String SCOPE="scope";
    /**角色状态@0:正常,1:停用*/
    public final static String STATE="state";
    /**删除标志@0:存在,1:删除*/
    public final static String IS_DEL="is_del";
    /**备注*/
    public final static String REMARK="remark";
    /**创建者*/
    public final static String CREATE_BY="create_by";
    /**创建时间*/
    public final static String CREATE_TIME="create_time";
    /**更新者*/
    public final static String MODIFY_BY="modify_by";
    /**更新时间*/
    public final static String MODIFY_TIME="modify_time";
    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>id</td><td>角色ID</td></tr>")
            +("<tr><td>name</td><td>角色名称</td></tr>")
            +("<tr><td>sort</td><td>显示顺序@0-9999</td></tr>")
            +("<tr><td>scope</td><td>数据范围@1:全部数据权限,2:自定数据权限,3:本部门数据权限,4:本部门及以下数据权限</td></tr>")
            +("<tr><td>state</td><td>角色状态@0:正常,1:停用</td></tr>")
            +("<tr><td>is_del</td><td>删除标志@0:存在,1:删除</td></tr>")
            +("<tr><td>remark</td><td>备注</td></tr>")
            +("<tr><td>create_by</td><td>创建者</td></tr>")
            +("<tr><td>create_time</td><td>创建时间</td></tr>")
            +("<tr><td>modify_by</td><td>更新者</td></tr>")
            +("<tr><td>modify_time</td><td>更新时间</td></tr>")
            +"</table>";

	/**
	 * 角色ID
	 */
	public Role setId(Integer id) {
		set("id", id);
		return this;
	}

	/**
	 * 角色ID
	 */
	public Integer getId() {
		return getInt("id");
	}

	/**
	 * 角色名称
	 */
	public Role setName(String name) {
		set("name", name);
		return this;
	}

	/**
	 * 角色名称
	 */
	public String getName() {
		return getStr("name");
	}

	/**
	 * 显示顺序@0-9999
	 */
	public Role setSort(Integer sort) {
		set("sort", sort);
		return this;
	}

	/**
	 * 显示顺序@0-9999
	 */
	public Integer getSort() {
		return getInt("sort");
	}

	/**
	 * 数据范围@1:全部数据权限,2:自定数据权限,3:本部门数据权限,4:本部门及以下数据权限
	 */
	public Role setScope(Integer scope) {
		set("scope", scope);
		return this;
	}

	/**
	 * 数据范围@1:全部数据权限,2:自定数据权限,3:本部门数据权限,4:本部门及以下数据权限
	 */
	public Integer getScope() {
		return getInt("scope");
	}

	/**
	 * 角色状态@0:正常,1:停用
	 */
	public Role setState(Integer state) {
		set("state", state);
		return this;
	}

	/**
	 * 角色状态@0:正常,1:停用
	 */
	public Integer getState() {
		return getInt("state");
	}

	/**
	 * 删除标志@0:存在,1:删除
	 */
	public Role setIsDel(Integer isDel) {
		set("is_del", isDel);
		return this;
	}

	/**
	 * 删除标志@0:存在,1:删除
	 */
	public Integer getIsDel() {
		return getInt("is_del");
	}

	/**
	 * 备注
	 */
	public Role setRemark(String remark) {
		set("remark", remark);
		return this;
	}

	/**
	 * 备注
	 */
	public String getRemark() {
		return getStr("remark");
	}

	/**
	 * 创建者
	 */
	public Role setCreateBy(Integer createBy) {
		set("create_by", createBy);
		return this;
	}

	/**
	 * 创建者
	 */
	public Integer getCreateBy() {
		return getInt("create_by");
	}

	/**
	 * 创建时间
	 */
	public Role setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return this;
	}

	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	/**
	 * 更新者
	 */
	public Role setModifyBy(Integer modifyBy) {
		set("modify_by", modifyBy);
		return this;
	}

	/**
	 * 更新者
	 */
	public Integer getModifyBy() {
		return getInt("modify_by");
	}

	/**
	 * 更新时间
	 */
	public Role setModifyTime(java.util.Date modifyTime) {
		set("modify_time", modifyTime);
		return this;
	}

	/**
	 * 更新时间
	 */
	public java.util.Date getModifyTime() {
		return get("modify_time");
	}


}
