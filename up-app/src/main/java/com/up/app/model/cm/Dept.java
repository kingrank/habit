package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 部门表
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class Dept extends DBModel<Dept> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_dept";
    /**主键*/
    public final static String TABLE_PKS="id";
    /**部门id*/
    public final static String ID="id";
    /**父部门id*/
    public final static String P_ID="p_id";
    /**部门名称*/
    public final static String NAME="name";
    /**负责人*/
    public final static String LEADER="leader";
    /**联系电话*/
    public final static String PHONE="phone";
    /**邮箱*/
    public final static String EMAIL="email";
    /**部门状态@0:正常,1:停用*/
    public final static String STATE="state";
    /**删除标志@0:存在,1:删除*/
    public final static String IS_DEL="is_del";
    /**显示顺序*/
    public final static String SORT="sort";
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
            +("<tr><td>id</td><td>部门id</td></tr>")
            +("<tr><td>p_id</td><td>父部门id</td></tr>")
            +("<tr><td>name</td><td>部门名称</td></tr>")
            +("<tr><td>leader</td><td>负责人</td></tr>")
            +("<tr><td>phone</td><td>联系电话</td></tr>")
            +("<tr><td>email</td><td>邮箱</td></tr>")
            +("<tr><td>state</td><td>部门状态@0:正常,1:停用</td></tr>")
            +("<tr><td>is_del</td><td>删除标志@0:存在,1:删除</td></tr>")
            +("<tr><td>sort</td><td>显示顺序</td></tr>")
            +("<tr><td>create_by</td><td>创建者</td></tr>")
            +("<tr><td>create_time</td><td>创建时间</td></tr>")
            +("<tr><td>modify_by</td><td>更新者</td></tr>")
            +("<tr><td>modify_time</td><td>更新时间</td></tr>")
            +"</table>";

	/**
	 * 部门id
	 */
	public Dept setId(Integer id) {
		set("id", id);
		return this;
	}

	/**
	 * 部门id
	 */
	public Integer getId() {
		return getInt("id");
	}

	/**
	 * 父部门id
	 */
	public Dept setPId(Integer pId) {
		set("p_id", pId);
		return this;
	}

	/**
	 * 父部门id
	 */
	public Integer getPId() {
		return getInt("p_id");
	}

	/**
	 * 部门名称
	 */
	public Dept setName(String name) {
		set("name", name);
		return this;
	}

	/**
	 * 部门名称
	 */
	public String getName() {
		return getStr("name");
	}

	/**
	 * 负责人
	 */
	public Dept setLeader(String leader) {
		set("leader", leader);
		return this;
	}

	/**
	 * 负责人
	 */
	public String getLeader() {
		return getStr("leader");
	}

	/**
	 * 联系电话
	 */
	public Dept setPhone(String phone) {
		set("phone", phone);
		return this;
	}

	/**
	 * 联系电话
	 */
	public String getPhone() {
		return getStr("phone");
	}

	/**
	 * 邮箱
	 */
	public Dept setEmail(String email) {
		set("email", email);
		return this;
	}

	/**
	 * 邮箱
	 */
	public String getEmail() {
		return getStr("email");
	}

	/**
	 * 部门状态@0:正常,1:停用
	 */
	public Dept setState(Integer state) {
		set("state", state);
		return this;
	}

	/**
	 * 部门状态@0:正常,1:停用
	 */
	public Integer getState() {
		return getInt("state");
	}

	/**
	 * 删除标志@0:存在,1:删除
	 */
	public Dept setIsDel(Integer isDel) {
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
	 * 显示顺序
	 */
	public Dept setSort(Integer sort) {
		set("sort", sort);
		return this;
	}

	/**
	 * 显示顺序
	 */
	public Integer getSort() {
		return getInt("sort");
	}

	/**
	 * 创建者
	 */
	public Dept setCreateBy(Integer createBy) {
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
	public Dept setCreateTime(java.util.Date createTime) {
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
	public Dept setModifyBy(Integer modifyBy) {
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
	public Dept setModifyTime(java.util.Date modifyTime) {
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
