package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 数据字典
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class Dict extends DBModel<Dict> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_dict";
    /**主键*/
    public final static String TABLE_PKS="id";
    /**字典主键*/
    public final static String ID="id";
    /**字典父模块*/
    public final static String P_ID="p_id";
    /**字典名称*/
    public final static String NAME="name";
    /**字典值*/
    public final static String VALUE="value";
    /**是否可编辑@0:不可编辑,1:可编辑*/
    public final static String EDITABLE="editable";
    /**状态@0:正常,1:禁用*/
    public final static String STATE="state";
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
            +("<tr><td>id</td><td>字典主键</td></tr>")
            +("<tr><td>p_id</td><td>字典父模块</td></tr>")
            +("<tr><td>name</td><td>字典名称</td></tr>")
            +("<tr><td>value</td><td>字典值</td></tr>")
            +("<tr><td>editable</td><td>是否可编辑@0:不可编辑,1:可编辑</td></tr>")
            +("<tr><td>state</td><td>状态@0:正常,1:禁用</td></tr>")
            +("<tr><td>sort</td><td>排序@0-9999</td></tr>")
            +("<tr><td>remark</td><td>备注</td></tr>")
            +("<tr><td>create_by</td><td>创建者</td></tr>")
            +("<tr><td>create_time</td><td>创建时间</td></tr>")
            +("<tr><td>modify_by</td><td>更新者</td></tr>")
            +("<tr><td>modify_time</td><td>更新时间</td></tr>")
            +"</table>";

	/**
	 * 字典主键
	 */
	public Dict setId(String id) {
		set("id", id);
		return this;
	}

	/**
	 * 字典主键
	 */
	public String getId() {
		return getStr("id");
	}

	/**
	 * 字典父模块
	 */
	public Dict setPId(String pId) {
		set("p_id", pId);
		return this;
	}

	/**
	 * 字典父模块
	 */
	public String getPId() {
		return getStr("p_id");
	}

	/**
	 * 字典名称
	 */
	public Dict setName(String name) {
		set("name", name);
		return this;
	}

	/**
	 * 字典名称
	 */
	public String getName() {
		return getStr("name");
	}

	/**
	 * 字典值
	 */
	public Dict setValue(String value) {
		set("value", value);
		return this;
	}

	/**
	 * 字典值
	 */
	public String getValue() {
		return getStr("value");
	}

	/**
	 * 是否可编辑@0:不可编辑,1:可编辑
	 */
	public Dict setEditable(Integer editable) {
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
	 * 状态@0:正常,1:禁用
	 */
	public Dict setState(Integer state) {
		set("state", state);
		return this;
	}

	/**
	 * 状态@0:正常,1:禁用
	 */
	public Integer getState() {
		return getInt("state");
	}

	/**
	 * 排序@0-9999
	 */
	public Dict setSort(Integer sort) {
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
	public Dict setRemark(String remark) {
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
	public Dict setCreateBy(Integer createBy) {
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
	public Dict setCreateTime(java.util.Date createTime) {
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
	public Dict setModifyBy(Integer modifyBy) {
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
	public Dict setModifyTime(java.util.Date modifyTime) {
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
