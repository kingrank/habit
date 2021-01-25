package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class JobLog extends DBModel<JobLog> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="log_job_log";
    /**主键*/
    public final static String TABLE_PKS="id";
    /***/
    public final static String ID="id";
    /***/
    public final static String GROUP="group";
    /***/
    public final static String NAME="name";
    /***/
    public final static String START_TIME="start_time";
    /***/
    public final static String END_TIME="end_time";
    /***/
    public final static String RES="res";
    /***/
    public final static String MESSAGE="message";
    /***/
    public final static String EXCEPTION="exception";
    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>id</td><td></td></tr>")
            +("<tr><td>group</td><td></td></tr>")
            +("<tr><td>name</td><td></td></tr>")
            +("<tr><td>start_time</td><td></td></tr>")
            +("<tr><td>end_time</td><td></td></tr>")
            +("<tr><td>res</td><td></td></tr>")
            +("<tr><td>message</td><td></td></tr>")
            +("<tr><td>exception</td><td></td></tr>")
            +"</table>";

	/**
	 * 
	 */
	public JobLog setId(Integer id) {
		set("id", id);
		return this;
	}

	/**
	 *
	 */
	public Integer getId() {
		return getInt("id");
	}

	/**
	 *
	 */
	public JobLog setGroup(String group) {
		set("group", group);
		return this;
	}

	/**
	 *
	 */
	public String getGroup() {
		return getStr("group");
	}

	/**
	 *
	 */
	public JobLog setName(String name) {
		set("name", name);
		return this;
	}

	/**
	 *
	 */
	public String getName() {
		return getStr("name");
	}

	/**
	 *
	 */
	public JobLog setStartTime(java.util.Date startTime) {
		set("start_time", startTime);
		return this;
	}

	/**
	 *
	 */
	public java.util.Date getStartTime() {
		return get("start_time");
	}

	/**
	 *
	 */
	public JobLog setEndTime(java.util.Date endTime) {
		set("end_time", endTime);
		return this;
	}

	/**
	 *
	 */
	public java.util.Date getEndTime() {
		return get("end_time");
	}

	/**
	 *
	 */
	public JobLog setRes(Integer res) {
		set("res", res);
		return this;
	}

	/**
	 *
	 */
	public Integer getRes() {
		return getInt("res");
	}

	/**
	 *
	 */
	public JobLog setMessage(String message) {
		set("message", message);
		return this;
	}

	/**
	 *
	 */
	public String getMessage() {
		return getStr("message");
	}

	/**
	 *
	 */
	public JobLog setException(String exception) {
		set("exception", exception);
		return this;
	}

	/**
	 *
	 */
	public String getException() {
		return getStr("exception");
	}


}
