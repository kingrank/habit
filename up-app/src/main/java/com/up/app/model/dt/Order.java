package com.up.app.model.dt;

import com.up.habit.expand.db.model.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 订单总览 实体类
 * @author 王剑洪 on 2021-01-21 16:31:00
 */
@SuppressWarnings("serial")
public class Order extends DBModel<Order> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="dt_order";

    /**订单编号*/
    public final static String ID="id";
    /**所属机构*/
    public final static String ORGID="orgid";
    /**会员编号*/
    public final static String MEMBERID="memberid";
    /**会员姓名*/
    public final static String MEMBERNAME="membername";
    /**联系方式*/
    public final static String MEMBERTEL="membertel";
    /**消费类型*/
    public final static String PRODUCTTYPE="producttype";
    /**应收*/
    public final static String PAYMENT="payment";
    /**实收*/
    public final static String PAIDIN="paidin";
    /**优惠*/
    public final static String DISCOUNT="discount";
    /**订单状态*/
    public final static String STATUS="status";
    /**支付状态*/
    public final static String PAYSTATE="paystate";
    /**生成时间*/
    public final static String LOGDATE="logdate";
    /**完成时间*/
    public final static String OVERDATE="overdate";
    /**主键*/
    public final static String TABLE_PKS="id";

    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>id</td><td></td></tr>")
            +("<tr><td>orgid</td><td></td></tr>")
            +("<tr><td>memberid</td><td></td></tr>")
            +("<tr><td>membername</td><td></td></tr>")
            +("<tr><td>membertel</td><td></td></tr>")
            +("<tr><td>producttype</td><td></td></tr>")
            +("<tr><td>payment</td><td></td></tr>")
            +("<tr><td>paidin</td><td></td></tr>")
            +("<tr><td>discount</td><td></td></tr>")
            +("<tr><td>status</td><td></td></tr>")
            +("<tr><td>paystate</td><td></td></tr>")
            +("<tr><td>logdate</td><td></td></tr>")
            +("<tr><td>overdate</td><td></td></tr>")
            +"</table>";

	/**设置订单编号*/
	public Order setId(String id) {
		set("id", id);
		return this;
	}

	/**获取订单编号*/
	public String getId() {
		return getStr("id");
	}

	/**设置所属机构*/
	public Order setOrgid(Integer orgid) {
		set("orgid", orgid);
		return this;
	}

	/**获取所属机构*/
	public Integer getOrgid() {
		return getInt("orgid");
	}

	/**设置会员编号*/
	public Order setMemberid(String memberid) {
		set("memberid", memberid);
		return this;
	}

	/**获取会员编号*/
	public String getMemberid() {
		return getStr("memberid");
	}

	/**设置会员姓名*/
	public Order setMembername(String membername) {
		set("membername", membername);
		return this;
	}

	/**获取会员姓名*/
	public String getMembername() {
		return getStr("membername");
	}

	/**设置联系方式*/
	public Order setMembertel(String membertel) {
		set("membertel", membertel);
		return this;
	}

	/**获取联系方式*/
	public String getMembertel() {
		return getStr("membertel");
	}

	/**设置消费类型*/
	public Order setProducttype(Integer producttype) {
		set("producttype", producttype);
		return this;
	}

	/**获取消费类型*/
	public Integer getProducttype() {
		return getInt("producttype");
	}

	/**设置应收*/
	public Order setPayment(Integer payment) {
		set("payment", payment);
		return this;
	}

	/**获取应收*/
	public Integer getPayment() {
		return getInt("payment");
	}

	/**设置实收*/
	public Order setPaidin(Integer paidin) {
		set("paidin", paidin);
		return this;
	}

	/**获取实收*/
	public Integer getPaidin() {
		return getInt("paidin");
	}

	/**设置优惠*/
	public Order setDiscount(Integer discount) {
		set("discount", discount);
		return this;
	}

	/**获取优惠*/
	public Integer getDiscount() {
		return getInt("discount");
	}

	/**设置订单状态*/
	public Order setStatus(Integer status) {
		set("status", status);
		return this;
	}

	/**获取订单状态*/
	public Integer getStatus() {
		return getInt("status");
	}

	/**设置支付状态*/
	public Order setPaystate(Integer paystate) {
		set("paystate", paystate);
		return this;
	}

	/**获取支付状态*/
	public Integer getPaystate() {
		return getInt("paystate");
	}

	/**设置生成时间*/
	public Order setLogdate(java.util.Date logdate) {
		set("logdate", logdate);
		return this;
	}

	/**获取生成时间*/
	public java.util.Date getLogdate() {
		return get("logdate");
	}

	/**设置完成时间*/
	public Order setOverdate(java.util.Date overdate) {
		set("overdate", overdate);
		return this;
	}

	/**获取完成时间*/
	public java.util.Date getOverdate() {
		return get("overdate");
	}


}
