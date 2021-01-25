package com.up.app.model.dt;

import com.up.habit.expand.db.model.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 会员信息 实体类
 * @author 王剑洪 on 2021-01-20 09:54:45
 */
@SuppressWarnings("serial")
public class Member extends DBModel<Member> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="dt_member";

    /**会员编号*/
    public final static String ID="id";
	/**所属机构*/
	public final static String ORGID="orgid";
    /**微信标识*/
    public final static String OPENID="openid";
    /**姓名*/
    public final static String NAME="name";
    /**昵称*/
    public final static String NICKNAME="nickname";
    /**手机号码*/
    public final static String TEL="tel";
    /**身份证号*/
    public final static String CARDID="cardid";
    /**性别*/
    public final static String GENDER="gender";
    /**级别*/
    public final static String LEVEL="level";
    /**头像*/
    public final static String AVATAR="avatar";
    /**生日*/
    public final static String BIRTH="birth";
    /**剩余积分*/
    public final static String POINTS="points";
    /**余额*/
    public final static String BALANCE="balance";
    /**状态*/
    public final static String STATUS="status";
    /**开户时间*/
    public final static String LOGDATE="logdate";
    /**主键*/
    public final static String TABLE_PKS="id";

    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>id</td><td></td></tr>")
			+("<tr><td>orgid</td><td></td></tr>")
            +("<tr><td>openid</td><td></td></tr>")
            +("<tr><td>name</td><td></td></tr>")
            +("<tr><td>nickname</td><td></td></tr>")
            +("<tr><td>tel</td><td></td></tr>")
            +("<tr><td>cardid</td><td></td></tr>")
            +("<tr><td>gender</td><td></td></tr>")
            +("<tr><td>level</td><td></td></tr>")
            +("<tr><td>avatar</td><td></td></tr>")
            +("<tr><td>birth</td><td></td></tr>")
            +("<tr><td>points</td><td></td></tr>")
            +("<tr><td>balance</td><td></td></tr>")
            +("<tr><td>status</td><td></td></tr>")
            +("<tr><td>logdate</td><td></td></tr>")
            +"</table>";

	/**设置会员编号*/
	public Member setId(String id) {
		set("id", id);
		return this;
	}

	/**获取会员编号*/
	public String getId() {
		return getStr("id");
	}

	/**设置所属机构*/
	public Member setOrgid(String orgid) {
		set("orgid", orgid);
		return this;
	}

	/**获取所属机构*/
	public String getOrgid() {
		return getStr("orgid");
	}

	/**设置微信标识*/
	public Member setOpenid(String openid) {
		set("openid", openid);
		return this;
	}

	/**获取微信标识*/
	public String getOpenid() {
		return getStr("openid");
	}

	/**设置姓名*/
	public Member setName(String name) {
		set("name", name);
		return this;
	}

	/**获取姓名*/
	public String getName() {
		return getStr("name");
	}

	/**设置昵称*/
	public Member setNickname(String nickname) {
		set("nickname", nickname);
		return this;
	}

	/**获取昵称*/
	public String getNickname() {
		return getStr("nickname");
	}

	/**设置手机号码*/
	public Member setTel(String tel) {
		set("tel", tel);
		return this;
	}

	/**获取手机号码*/
	public String getTel() {
		return getStr("tel");
	}

	/**设置身份证号*/
	public Member setCardid(String cardid) {
		set("cardid", cardid);
		return this;
	}

	/**获取身份证号*/
	public String getCardid() {
		return getStr("cardid");
	}

	/**设置性别*/
	public Member setGender(Integer gender) {
		set("gender", gender);
		return this;
	}

	/**获取性别*/
	public Integer getGender() {
		return getInt("gender");
	}

	/**设置级别*/
	public Member setLevel(Integer level) {
		set("level", level);
		return this;
	}

	/**获取级别*/
	public Integer getLevel() {
		return getInt("level");
	}

	/**设置头像*/
	public Member setAvatar(String avatar) {
		set("avatar", avatar);
		return this;
	}

	/**获取头像*/
	public String getAvatar() {
		return getStr("avatar");
	}

	/**设置生日*/
	public Member setBirth(java.util.Date birth) {
		set("birth", birth);
		return this;
	}

	/**获取生日*/
	public java.util.Date getBirth() {
		return get("birth");
	}

	/**设置剩余积分*/
	public Member setPoints(Integer points) {
		set("points", points);
		return this;
	}

	/**获取剩余积分*/
	public Integer getPoints() {
		return getInt("points");
	}

	/**设置余额*/
	public Member setBalance(Integer balance) {
		set("balance", balance);
		return this;
	}

	/**获取余额*/
	public Integer getBalance() {
		return getInt("balance");
	}

	/**设置状态*/
	public Member setStatus(Integer status) {
		set("status", status);
		return this;
	}

	/**获取状态*/
	public Integer getStatus() {
		return getInt("status");
	}

	/**设置开户时间*/
	public Member setLogdate(java.util.Date logdate) {
		set("logdate", logdate);
		return this;
	}

	/**获取开户时间*/
	public java.util.Date getLogdate() {
		return get("logdate");
	}


}
