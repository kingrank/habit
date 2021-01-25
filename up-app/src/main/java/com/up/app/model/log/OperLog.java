package com.up.app.model.log;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 系统操作日志
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class OperLog extends DBModel<OperLog> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="log_oper_log";
    /**主键*/
    public final static String TABLE_PKS="id";
    /**系统日志ID*/
    public final static String ID="id";
    /**模块*/
    public final static String MODULE="module";
    /**操作名称*/
    public final static String NAME="name";
    /**访问action*/
    public final static String ACTION="action";
    /**控制器*/
    public final static String CONTROLLER="controller";
    /**请求时间*/
    public final static String REQUEST_TIME="request_time";
    /**请求IP*/
    public final static String REQUEST_IP="request_ip";
    /**请求头*/
    public final static String REQUEST_HEADER="request_header";
    /**请求参数*/
    public final static String REQUEST_PARAMETER="request_parameter";
    /**响应时间*/
    public final static String RESPONSE_TIME="response_time";
    /**响应消息*/
    public final static String RESPONSE_BODY="response_body";
    /**响应码@0:成功*/
    public final static String CODE="code";
    /**业务处理时间,毫秒*/
    public final static String DISPOSE="dispose";
    /**操作者*/
    public final static String USER_ID="user_id";
    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>id</td><td>系统日志ID</td></tr>")
            +("<tr><td>module</td><td>模块</td></tr>")
            +("<tr><td>name</td><td>操作名称</td></tr>")
            +("<tr><td>action</td><td>访问action</td></tr>")
            +("<tr><td>controller</td><td>控制器</td></tr>")
            +("<tr><td>request_time</td><td>请求时间</td></tr>")
            +("<tr><td>request_ip</td><td>请求IP</td></tr>")
            +("<tr><td>request_header</td><td>请求头</td></tr>")
            +("<tr><td>request_parameter</td><td>请求参数</td></tr>")
            +("<tr><td>response_time</td><td>响应时间</td></tr>")
            +("<tr><td>response_body</td><td>响应消息</td></tr>")
            +("<tr><td>code</td><td>响应码@0:成功</td></tr>")
            +("<tr><td>dispose</td><td>业务处理时间,毫秒</td></tr>")
            +("<tr><td>user_id</td><td>操作者</td></tr>")
            +"</table>";

	/**
	 * 系统日志ID
	 */
	public OperLog setId(Integer id) {
		set("id", id);
		return this;
	}

	/**
	 * 系统日志ID
	 */
	public Integer getId() {
		return getInt("id");
	}

	/**
	 * 模块
	 */
	public OperLog setModule(String module) {
		set("module", module);
		return this;
	}

	/**
	 * 模块
	 */
	public String getModule() {
		return getStr("module");
	}

	/**
	 * 操作名称
	 */
	public OperLog setName(String name) {
		set("name", name);
		return this;
	}

	/**
	 * 操作名称
	 */
	public String getName() {
		return getStr("name");
	}

	/**
	 * 访问action
	 */
	public OperLog setAction(String action) {
		set("action", action);
		return this;
	}

	/**
	 * 访问action
	 */
	public String getAction() {
		return getStr("action");
	}

	/**
	 * 控制器
	 */
	public OperLog setController(String controller) {
		set("controller", controller);
		return this;
	}

	/**
	 * 控制器
	 */
	public String getController() {
		return getStr("controller");
	}

	/**
	 * 请求时间
	 */
	public OperLog setRequestTime(java.util.Date requestTime) {
		set("request_time", requestTime);
		return this;
	}

	/**
	 * 请求时间
	 */
	public java.util.Date getRequestTime() {
		return get("request_time");
	}

	/**
	 * 请求IP
	 */
	public OperLog setRequestIp(String requestIp) {
		set("request_ip", requestIp);
		return this;
	}

	/**
	 * 请求IP
	 */
	public String getRequestIp() {
		return getStr("request_ip");
	}

	/**
	 * 请求头
	 */
	public OperLog setRequestHeader(String requestHeader) {
		set("request_header", requestHeader);
		return this;
	}

	/**
	 * 请求头
	 */
	public String getRequestHeader() {
		return getStr("request_header");
	}

	/**
	 * 请求参数
	 */
	public OperLog setRequestParameter(String requestParameter) {
		set("request_parameter", requestParameter);
		return this;
	}

	/**
	 * 请求参数
	 */
	public String getRequestParameter() {
		return getStr("request_parameter");
	}

	/**
	 * 响应时间
	 */
	public OperLog setResponseTime(java.util.Date responseTime) {
		set("response_time", responseTime);
		return this;
	}

	/**
	 * 响应时间
	 */
	public java.util.Date getResponseTime() {
		return get("response_time");
	}

	/**
	 * 响应消息
	 */
	public OperLog setResponseBody(String responseBody) {
		set("response_body", responseBody);
		return this;
	}

	/**
	 * 响应消息
	 */
	public String getResponseBody() {
		return getStr("response_body");
	}

	/**
	 * 响应码@0:成功
	 */
	public OperLog setCode(Integer code) {
		set("code", code);
		return this;
	}

	/**
	 * 响应码@0:成功
	 */
	public Integer getCode() {
		return getInt("code");
	}

	/**
	 * 业务处理时间,毫秒
	 */
	public OperLog setDispose(Integer dispose) {
		set("dispose", dispose);
		return this;
	}

	/**
	 * 业务处理时间,毫秒
	 */
	public Integer getDispose() {
		return getInt("dispose");
	}

	/**
	 * 操作者
	 */
	public OperLog setUserId(Integer userId) {
		set("user_id", userId);
		return this;
	}

	/**
	 * 操作者
	 */
	public Integer getUserId() {
		return getInt("user_id");
	}


}
