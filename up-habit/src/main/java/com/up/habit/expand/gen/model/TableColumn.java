package com.up.habit.expand.gen.model;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 代码生成业务表字段
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class TableColumn extends DBModel<TableColumn> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="gen_table_column";
    /**主键*/
    public final static String TABLE_PKS="id";
    /**编号*/
    public final static String ID="id";
    /**归属表编号*/
    public final static String TABLE_ID="table_id";
    /**列名称*/
    public final static String NAME="name";
    /**列描述*/
    public final static String COMMENT="comment";
    /**业务名称*/
    public final static String BUSINESS="business";
    /**列类型*/
    public final static String TYPE="type";
    /**JAVA类型*/
    public final static String JAVA_TYPE="java_type";
    /**是否主键（0是）*/
    public final static String IS_PK="is_pk";
    /**是否自增（0是）*/
    public final static String IS_INCREMENT="is_increment";
    /**是否必填（0是）*/
    public final static String IS_REQUIRED="is_required";
    /**是否为插入字段（0是）*/
    public final static String IS_INSERT="is_insert";
    /**是否编辑字段（0是）*/
    public final static String IS_EDIT="is_edit";
    /**是否列表字段（0是）*/
    public final static String IS_LIST="is_list";
    /**是否查询字段（0是）*/
    public final static String IS_QUERY="is_query";
    /**查询方式（等于、不等于、大于、小于、范围）*/
    public final static String QUERY_TYPE="query_type";
    /**显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）*/
    public final static String HTML_TYPE="html_type";
    /**字典类型*/
    public final static String DICT_TYPE="dict_type";
    /**排序*/
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
            +("<tr><td>id</td><td>编号</td></tr>")
            +("<tr><td>table_id</td><td>归属表编号</td></tr>")
            +("<tr><td>name</td><td>列名称</td></tr>")
            +("<tr><td>comment</td><td>列描述</td></tr>")
            +("<tr><td>business</td><td>业务名称</td></tr>")
            +("<tr><td>type</td><td>列类型</td></tr>")
            +("<tr><td>java_type</td><td>JAVA类型</td></tr>")
            +("<tr><td>is_pk</td><td>是否主键（0是）</td></tr>")
            +("<tr><td>is_increment</td><td>是否自增（0是）</td></tr>")
            +("<tr><td>is_required</td><td>是否必填（0是）</td></tr>")
            +("<tr><td>is_insert</td><td>是否为插入字段（0是）</td></tr>")
            +("<tr><td>is_edit</td><td>是否编辑字段（0是）</td></tr>")
            +("<tr><td>is_list</td><td>是否列表字段（0是）</td></tr>")
            +("<tr><td>is_query</td><td>是否查询字段（0是）</td></tr>")
            +("<tr><td>query_type</td><td>查询方式（等于、不等于、大于、小于、范围）</td></tr>")
            +("<tr><td>html_type</td><td>显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）</td></tr>")
            +("<tr><td>dict_type</td><td>字典类型</td></tr>")
            +("<tr><td>sort</td><td>排序</td></tr>")
            +("<tr><td>create_by</td><td>创建者</td></tr>")
            +("<tr><td>create_time</td><td>创建时间</td></tr>")
            +("<tr><td>modify_by</td><td>更新者</td></tr>")
            +("<tr><td>modify_time</td><td>更新时间</td></tr>")
            +"</table>";

	/**
	 * 编号
	 */
	public TableColumn setId(Integer id) {
		set("id", id);
		return this;
	}

	/**
	 * 编号
	 */
	public Integer getId() {
		return getInt("id");
	}

	/**
	 * 归属表编号
	 */
	public TableColumn setTableId(Integer tableId) {
		set("table_id", tableId);
		return this;
	}

	/**
	 * 归属表编号
	 */
	public Integer getTableId() {
		return getInt("table_id");
	}

	/**
	 * 列名称
	 */
	public TableColumn setName(String name) {
		set("name", name);
		return this;
	}

	/**
	 * 列名称
	 */
	public String getName() {
		return getStr("name");
	}

	/**
	 * 列描述
	 */
	public TableColumn setComment(String comment) {
		set("comment", comment);
		return this;
	}

	/**
	 * 列描述
	 */
	public String getComment() {
		return getStr("comment");
	}

	/**
	 * 业务名称
	 */
	public TableColumn setBusiness(String business) {
		set("business", business);
		return this;
	}

	/**
	 * 业务名称
	 */
	public String getBusiness() {
		return getStr("business");
	}

	/**
	 * 列类型
	 */
	public TableColumn setType(String type) {
		set("type", type);
		return this;
	}

	/**
	 * 列类型
	 */
	public String getType() {
		return getStr("type");
	}

	/**
	 * JAVA类型
	 */
	public TableColumn setJavaType(String javaType) {
		set("java_type", javaType);
		return this;
	}

	/**
	 * JAVA类型
	 */
	public String getJavaType() {
		return getStr("java_type");
	}

	/**
	 * 是否主键（0是）
	 */
	public TableColumn setIsPk(Integer isPk) {
		set("is_pk", isPk);
		return this;
	}

	/**
	 * 是否主键（0是）
	 */
	public Integer getIsPk() {
		return getInt("is_pk");
	}

	/**
	 * 是否自增（0是）
	 */
	public TableColumn setIsIncrement(Integer isIncrement) {
		set("is_increment", isIncrement);
		return this;
	}

	/**
	 * 是否自增（0是）
	 */
	public Integer getIsIncrement() {
		return getInt("is_increment");
	}

	/**
	 * 是否必填（0是）
	 */
	public TableColumn setIsRequired(Integer isRequired) {
		set("is_required", isRequired);
		return this;
	}

	/**
	 * 是否必填（0是）
	 */
	public Integer getIsRequired() {
		return getInt("is_required");
	}

	/**
	 * 是否为插入字段（0是）
	 */
	public TableColumn setIsInsert(Integer isInsert) {
		set("is_insert", isInsert);
		return this;
	}

	/**
	 * 是否为插入字段（0是）
	 */
	public Integer getIsInsert() {
		return getInt("is_insert");
	}

	/**
	 * 是否编辑字段（0是）
	 */
	public TableColumn setIsEdit(Integer isEdit) {
		set("is_edit", isEdit);
		return this;
	}

	/**
	 * 是否编辑字段（0是）
	 */
	public Integer getIsEdit() {
		return getInt("is_edit");
	}

	/**
	 * 是否列表字段（0是）
	 */
	public TableColumn setIsList(Integer isList) {
		set("is_list", isList);
		return this;
	}

	/**
	 * 是否列表字段（0是）
	 */
	public Integer getIsList() {
		return getInt("is_list");
	}

	/**
	 * 是否查询字段（0是）
	 */
	public TableColumn setIsQuery(Integer isQuery) {
		set("is_query", isQuery);
		return this;
	}

	/**
	 * 是否查询字段（0是）
	 */
	public Integer getIsQuery() {
		return getInt("is_query");
	}

	/**
	 * 查询方式（等于、不等于、大于、小于、范围）
	 */
	public TableColumn setQueryType(String queryType) {
		set("query_type", queryType);
		return this;
	}

	/**
	 * 查询方式（等于、不等于、大于、小于、范围）
	 */
	public String getQueryType() {
		return getStr("query_type");
	}

	/**
	 * 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
	 */
	public TableColumn setHtmlType(String htmlType) {
		set("html_type", htmlType);
		return this;
	}

	/**
	 * 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
	 */
	public String getHtmlType() {
		return getStr("html_type");
	}

	/**
	 * 字典类型
	 */
	public TableColumn setDictType(String dictType) {
		set("dict_type", dictType);
		return this;
	}

	/**
	 * 字典类型
	 */
	public String getDictType() {
		return getStr("dict_type");
	}

	/**
	 * 排序
	 */
	public TableColumn setSort(Integer sort) {
		set("sort", sort);
		return this;
	}

	/**
	 * 排序
	 */
	public Integer getSort() {
		return getInt("sort");
	}

	/**
	 * 创建者
	 */
	public TableColumn setCreateBy(Integer createBy) {
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
	public TableColumn setCreateTime(java.util.Date createTime) {
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
	public TableColumn setModifyBy(Integer modifyBy) {
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
	public TableColumn setModifyTime(java.util.Date modifyTime) {
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
