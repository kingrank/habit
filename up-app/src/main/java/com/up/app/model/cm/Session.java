package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 用户session
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class Session extends DBModel<Session> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_session";
    /**主键*/
    public final static String TABLE_PKS="id";
    /**sessionId*/
    public final static String ID="id";
    /**用户ID*/
    public final static String USER_ID="user_id";
    /**session过期时间*/
    public final static String EXPIRE_AT="expire_at";
    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>id</td><td>sessionId</td></tr>")
            +("<tr><td>user_id</td><td>用户ID</td></tr>")
            +("<tr><td>expire_at</td><td>session过期时间</td></tr>")
            +"</table>";

	/**
	 * sessionId
	 */
	public Session setId(String id) {
		set("id", id);
		return this;
	}

	/**
	 * sessionId
	 */
	public String getId() {
		return getStr("id");
	}

	/**
	 * 用户ID
	 */
	public Session setUserId(Integer userId) {
		set("user_id", userId);
		return this;
	}

	/**
	 * 用户ID
	 */
	public Integer getUserId() {
		return getInt("user_id");
	}

	/**
	 * session过期时间
	 */
	public Session setExpireAt(Long expireAt) {
		set("expire_at", expireAt);
		return this;
	}

	/**
	 * session过期时间
	 */
	public Long getExpireAt() {
		return getLong("expire_at");
	}


}
