package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 岗位信息表
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class Post extends DBModel<Post> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_post";
    /**主键*/
    public final static String TABLE_PKS="id";
    /**岗位ID*/
    public final static String ID="id";
    /**岗位编码*/
    public final static String CODE="code";
    /**岗位名称*/
    public final static String NAME="name";
    /**显示顺序*/
    public final static String SORT="sort";
    /**状态@0:正常,1:停用*/
    public final static String STATE="state";
    /**删除标识@0:正常,1:删除*/
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
            +("<tr><td>id</td><td>岗位ID</td></tr>")
            +("<tr><td>code</td><td>岗位编码</td></tr>")
            +("<tr><td>name</td><td>岗位名称</td></tr>")
            +("<tr><td>sort</td><td>显示顺序</td></tr>")
            +("<tr><td>state</td><td>状态@0:正常,1:停用</td></tr>")
            +("<tr><td>is_del</td><td>删除标识@0:正常,1:删除</td></tr>")
            +("<tr><td>remark</td><td>备注</td></tr>")
            +("<tr><td>create_by</td><td>创建者</td></tr>")
            +("<tr><td>create_time</td><td>创建时间</td></tr>")
            +("<tr><td>modify_by</td><td>更新者</td></tr>")
            +("<tr><td>modify_time</td><td>更新时间</td></tr>")
            +"</table>";

	/**
	 * 岗位ID
	 */
	public Post setId(Integer id) {
		set("id", id);
		return this;
	}

	/**
	 * 岗位ID
	 */
	public Integer getId() {
		return getInt("id");
	}

	/**
	 * 岗位编码
	 */
	public Post setCode(String code) {
		set("code", code);
		return this;
	}

	/**
	 * 岗位编码
	 */
	public String getCode() {
		return getStr("code");
	}

	/**
	 * 岗位名称
	 */
	public Post setName(String name) {
		set("name", name);
		return this;
	}

	/**
	 * 岗位名称
	 */
	public String getName() {
		return getStr("name");
	}

	/**
	 * 显示顺序
	 */
	public Post setSort(Integer sort) {
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
	 * 状态@0:正常,1:停用
	 */
	public Post setState(String state) {
		set("state", state);
		return this;
	}

	/**
	 * 状态@0:正常,1:停用
	 */
	public String getState() {
		return getStr("state");
	}

	/**
	 * 删除标识@0:正常,1:删除
	 */
	public Post setIsDel(Integer isDel) {
		set("is_del", isDel);
		return this;
	}

	/**
	 * 删除标识@0:正常,1:删除
	 */
	public Integer getIsDel() {
		return getInt("is_del");
	}

	/**
	 * 备注
	 */
	public Post setRemark(String remark) {
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
	public Post setCreateBy(Integer createBy) {
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
	public Post setCreateTime(java.util.Date createTime) {
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
	public Post setModifyBy(Integer modifyBy) {
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
	public Post setModifyTime(java.util.Date modifyTime) {
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
