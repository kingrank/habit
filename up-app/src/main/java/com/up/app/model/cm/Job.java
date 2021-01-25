package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class Job extends DBModel<Job> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_job";
    /**主键*/
    public final static String TABLE_PKS="id";
    /**任务ID*/
    public final static String ID="id";
    /**任务名称*/
    public final static String NAME="name";
    /**任务组名*/
    public final static String GROUP="group";
    /**cron执行表达式*/
    public final static String CORN="corn";
    /**执行任务类*/
    public final static String CLAZZ="clazz";
    /**任务状态@0:未启动,1:启动*/
    public final static String STATE="state";
    /**执行的sql*/
    public final static String SQL="sql";
    /**最后一次执行时间*/
    public final static String LAST_TIME="last_time";
    /**创建者*/
    public final static String CREATE_BY="create_by";
    /**创建时间*/
    public final static String CREATE_TIME="create_time";
    /**更新者*/
    public final static String MODIDY_BY="modidy_by";
    /**更新时间*/
    public final static String MODIFY_TIME="modify_time";
    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>id</td><td>任务ID</td></tr>")
            +("<tr><td>name</td><td>任务名称</td></tr>")
            +("<tr><td>group</td><td>任务组名</td></tr>")
            +("<tr><td>corn</td><td>cron执行表达式</td></tr>")
            +("<tr><td>clazz</td><td>执行任务类</td></tr>")
            +("<tr><td>state</td><td>任务状态@0:未启动,1:启动</td></tr>")
            +("<tr><td>sql</td><td>执行的sql</td></tr>")
            +("<tr><td>last_time</td><td>最后一次执行时间</td></tr>")
            +("<tr><td>create_by</td><td>创建者</td></tr>")
            +("<tr><td>create_time</td><td>创建时间</td></tr>")
            +("<tr><td>modidy_by</td><td>更新者</td></tr>")
            +("<tr><td>modify_time</td><td>更新时间</td></tr>")
            +"</table>";

	/**
	 * 任务ID
	 */
	public Job setId(Integer id) {
		set("id", id);
		return this;
	}

	/**
	 * 任务ID
	 */
	public Integer getId() {
		return getInt("id");
	}

	/**
	 * 任务名称
	 */
	public Job setName(String name) {
		set("name", name);
		return this;
	}

	/**
	 * 任务名称
	 */
	public String getName() {
		return getStr("name");
	}

	/**
	 * 任务组名
	 */
	public Job setGroup(String group) {
		set("group", group);
		return this;
	}

	/**
	 * 任务组名
	 */
	public String getGroup() {
		return getStr("group");
	}

	/**
	 * cron执行表达式
	 */
	public Job setCorn(String corn) {
		set("corn", corn);
		return this;
	}

	/**
	 * cron执行表达式
	 */
	public String getCorn() {
		return getStr("corn");
	}

	/**
	 * 执行任务类
	 */
	public Job setClazz(String clazz) {
		set("clazz", clazz);
		return this;
	}

	/**
	 * 执行任务类
	 */
	public String getClazz() {
		return getStr("clazz");
	}

	/**
	 * 任务状态@0:未启动,1:启动
	 */
	public Job setState(Integer state) {
		set("state", state);
		return this;
	}

	/**
	 * 任务状态@0:未启动,1:启动
	 */
	public Integer getState() {
		return getInt("state");
	}

	/**
	 * 执行的sql
	 */
	public Job setSql(String sql) {
		set("sql", sql);
		return this;
	}

	/**
	 * 执行的sql
	 */
	public String getSql() {
		return getStr("sql");
	}

	/**
	 * 最后一次执行时间
	 */
	public Job setLastTime(java.util.Date lastTime) {
		set("last_time", lastTime);
		return this;
	}

	/**
	 * 最后一次执行时间
	 */
	public java.util.Date getLastTime() {
		return get("last_time");
	}

	/**
	 * 创建者
	 */
	public Job setCreateBy(Integer createBy) {
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
	public Job setCreateTime(java.util.Date createTime) {
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
	public Job setModidyBy(Integer modidyBy) {
		set("modidy_by", modidyBy);
		return this;
	}

	/**
	 * 更新者
	 */
	public Integer getModidyBy() {
		return getInt("modidy_by");
	}

	/**
	 * 更新时间
	 */
	public Job setModifyTime(java.util.Date modifyTime) {
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
