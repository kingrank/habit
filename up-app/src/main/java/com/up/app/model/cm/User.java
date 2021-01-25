package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 用户信息表
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class User extends DBModel<User> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_user";
    /**主键*/
    public final static String TABLE_PKS="id";
    /**用户ID*/
    public final static String ID="id";
    /**所属机构*/
    public final static String ORG_ID="org_id";
    /**用户账号*/
    public final static String ACCOUNT="account";
    /**用户昵称*/
    public final static String NICKNAME="nickname";
    /**用户邮箱*/
    public final static String EMAIL="email";
    /**手机号码*/
    public final static String PHONE="phone";
    /**性别@0:未设置,1:男,2:女*/
    public final static String SEX="sex";
    /**头像文件ID*/
    public final static String AVATAR="avatar";
    /**密码安全码*/
    public final static String SALT="salt";
    /**密码*/
    public final static String PASSWORD="password";
    /**帐号状态@0:正常,1:停用*/
    public final static String STATE="state";
    /**删除标志@0:存在,2:删除*/
    public final static String IS_DEL="is_del";
    /**最后登陆IP*/
    public final static String LOGIN_IP="login_ip";
    /**最后登陆时间*/
    public final static String LOGIN_TIME="login_time";
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
            +("<tr><td>id</td><td>用户ID</td></tr>")
            +("<tr><td>org_id</td><td>所属机构</td></tr>")
            +("<tr><td>account</td><td>用户账号</td></tr>")
            +("<tr><td>nickname</td><td>用户昵称</td></tr>")
            +("<tr><td>email</td><td>用户邮箱</td></tr>")
            +("<tr><td>phone</td><td>手机号码</td></tr>")
            +("<tr><td>sex</td><td>性别@0:未设置,1:男,2:女</td></tr>")
            +("<tr><td>avatar</td><td>头像文件ID</td></tr>")
            +("<tr><td>salt</td><td>密码安全码</td></tr>")
            +("<tr><td>password</td><td>密码</td></tr>")
            +("<tr><td>state</td><td>帐号状态@0:正常,1:停用</td></tr>")
            +("<tr><td>is_del</td><td>删除标志@0:存在,2:删除</td></tr>")
            +("<tr><td>login_ip</td><td>最后登陆IP</td></tr>")
            +("<tr><td>login_time</td><td>最后登陆时间</td></tr>")
            +("<tr><td>remark</td><td>备注</td></tr>")
            +("<tr><td>create_by</td><td>创建者</td></tr>")
            +("<tr><td>create_time</td><td>创建时间</td></tr>")
            +("<tr><td>modify_by</td><td>更新者</td></tr>")
            +("<tr><td>modify_time</td><td>更新时间</td></tr>")
            +"</table>";

	/**
	 * 用户ID
	 */
	public User setId(Integer id) {
		set("id", id);
		return this;
	}

	/**
	 * 用户ID
	 */
	public Integer getId() {
		return getInt("id");
	}

	/**
	 * 所属机构
	 */
	public User setOrgId(Integer orgId) {
		set("org_id", orgId);
		return this;
	}

	/**
	 * 所属机构
	 */
	public Integer getOrgId() {
		return getInt("org_id");
	}

	/**
	 * 用户账号
	 */
	public User setAccount(String account) {
		set("account", account);
		return this;
	}

	/**
	 * 用户账号
	 */
	public String getAccount() {
		return getStr("account");
	}

	/**
	 * 用户昵称
	 */
	public User setNickname(String nickname) {
		set("nickname", nickname);
		return this;
	}

	/**
	 * 用户昵称
	 */
	public String getNickname() {
		return getStr("nickname");
	}

	/**
	 * 用户邮箱
	 */
	public User setEmail(String email) {
		set("email", email);
		return this;
	}

	/**
	 * 用户邮箱
	 */
	public String getEmail() {
		return getStr("email");
	}

	/**
	 * 手机号码
	 */
	public User setPhone(String phone) {
		set("phone", phone);
		return this;
	}

	/**
	 * 手机号码
	 */
	public String getPhone() {
		return getStr("phone");
	}

	/**
	 * 性别@0:未设置,1:男,2:女
	 */
	public User setSex(Integer sex) {
		set("sex", sex);
		return this;
	}

	/**
	 * 性别@0:未设置,1:男,2:女
	 */
	public Integer getSex() {
		return getInt("sex");
	}

	/**
	 * 头像文件ID
	 */
	public User setAvatar(Integer avatar) {
		set("avatar", avatar);
		return this;
	}

	/**
	 * 头像文件ID
	 */
	public Integer getAvatar() {
		return getInt("avatar");
	}

	/**
	 * 密码安全码
	 */
	public User setSalt(String salt) {
		set("salt", salt);
		return this;
	}

	/**
	 * 密码安全码
	 */
	public String getSalt() {
		return getStr("salt");
	}

	/**
	 * 密码
	 */
	public User setPassword(String password) {
		set("password", password);
		return this;
	}

	/**
	 * 密码
	 */
	public String getPassword() {
		return getStr("password");
	}

	/**
	 * 帐号状态@0:正常,1:停用
	 */
	public User setState(Integer state) {
		set("state", state);
		return this;
	}

	/**
	 * 帐号状态@0:正常,1:停用
	 */
	public Integer getState() {
		return getInt("state");
	}

	/**
	 * 删除标志@0:存在,2:删除
	 */
	public User setIsDel(Integer isDel) {
		set("is_del", isDel);
		return this;
	}

	/**
	 * 删除标志@0:存在,2:删除
	 */
	public Integer getIsDel() {
		return getInt("is_del");
	}

	/**
	 * 最后登陆IP
	 */
	public User setLoginIp(String loginIp) {
		set("login_ip", loginIp);
		return this;
	}

	/**
	 * 最后登陆IP
	 */
	public String getLoginIp() {
		return getStr("login_ip");
	}

	/**
	 * 最后登陆时间
	 */
	public User setLoginTime(java.util.Date loginTime) {
		set("login_time", loginTime);
		return this;
	}

	/**
	 * 最后登陆时间
	 */
	public java.util.Date getLoginTime() {
		return get("login_time");
	}

	/**
	 * 备注
	 */
	public User setRemark(String remark) {
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
	public User setCreateBy(Integer createBy) {
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
	public User setCreateTime(java.util.Date createTime) {
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
	public User setModifyBy(Integer modifyBy) {
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
	public User setModifyTime(java.util.Date modifyTime) {
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
