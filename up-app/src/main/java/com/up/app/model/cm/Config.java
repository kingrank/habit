package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 参数配置
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class Config extends DBModel<Config> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_config";
    /**主键*/
    public final static String TABLE_PKS="id";
    /**参数主键*/
    public final static String ID="id";
    /**参数组*/
    public final static String GROUP="group";
    /**参数名*/
    public final static String NAME="name";
    /**参数值*/
    public final static String VALUE="value";
    /**是否可编辑@0:不可编辑,1:可编辑*/
    public final static String EDITABLE="editable";
    /**排序@0-9999*/
    public final static String SORT="sort";
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
            +("<tr><td>id</td><td>参数主键</td></tr>")
            +("<tr><td>group</td><td>参数组</td></tr>")
            +("<tr><td>name</td><td>参数名</td></tr>")
            +("<tr><td>value</td><td>参数值</td></tr>")
            +("<tr><td>editable</td><td>是否可编辑@0:不可编辑,1:可编辑</td></tr>")
            +("<tr><td>sort</td><td>排序@0-9999</td></tr>")
            +("<tr><td>remark</td><td>备注</td></tr>")
            +("<tr><td>create_by</td><td>创建者</td></tr>")
            +("<tr><td>create_time</td><td>创建时间</td></tr>")
            +("<tr><td>modify_by</td><td>更新者</td></tr>")
            +("<tr><td>modify_time</td><td>更新时间</td></tr>")
            +"</table>";

	/**
	 * 参数主键
	 */
	public Config setId(String id) {
		set("id", id);
		return this;
	}

	/**
	 * 参数主键
	 */
	public String getId() {
		return getStr("id");
	}

	/**
	 * 参数组
	 */
	public Config setGroup(String group) {
		set("group", group);
		return this;
	}

	/**
	 * 参数组
	 */
	public String getGroup() {
		return getStr("group");
	}

	/**
	 * 参数名
	 */
	public Config setName(String name) {
		set("name", name);
		return this;
	}

	/**
	 * 参数名
	 */
	public String getName() {
		return getStr("name");
	}

	/**
	 * 参数值
	 */
	public Config setValue(String value) {
		set("value", value);
		return this;
	}

	/**
	 * 参数值
	 */
	public String getValue() {
		return getStr("value");
	}

	/**
	 * 是否可编辑@0:不可编辑,1:可编辑
	 */
	public Config setEditable(Integer editable) {
		set("editable", editable);
		return this;
	}

	/**
	 * 是否可编辑@0:不可编辑,1:可编辑
	 */
	public Integer getEditable() {
		return getInt("editable");
	}

	/**
	 * 排序@0-9999
	 */
	public Config setSort(Integer sort) {
		set("sort", sort);
		return this;
	}

	/**
	 * 排序@0-9999
	 */
	public Integer getSort() {
		return getInt("sort");
	}

	/**
	 * 备注
	 */
	public Config setRemark(String remark) {
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
	public Config setCreateBy(Integer createBy) {
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
	public Config setCreateTime(java.util.Date createTime) {
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
	public Config setModifyBy(Integer modifyBy) {
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
	public Config setModifyTime(java.util.Date modifyTime) {
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
