package com.up.app.model.dt;

import com.up.habit.expand.db.model.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 订单明细 实体类
 * @author 王剑洪 on 2021-01-21 16:33:21
 */
@SuppressWarnings("serial")
public class Orderdetail extends DBModel<Orderdetail> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="dt_orderdetail";

    /**清单编号*/
    public final static String ID="id";
    /**订单编号*/
    public final static String ORDERID="orderid";
    /**商品编号*/
    public final static String PRODUCTID="productid";
    /**商品名称*/
    public final static String PRODUCTNAME="productname";
    /**单价*/
    public final static String PRICE="price";
    /**数量*/
    public final static String NUM="num";
    /**应收*/
    public final static String PAYMENT="payment";
    /**实收*/
    public final static String PAIDIN="paidin";
    /**优惠*/
    public final static String DISCOUNT="discount";
    /**优惠说明*/
    public final static String DISCOUNTDESC="discountdesc";
    /**积分抵扣*/
    public final static String POINTPAYMENT="pointpayment";
    /**积分增加*/
    public final static String POINTADD="pointadd";
    /**订单状态*/
    public final static String STATUS="status";
    /**支付状态*/
    public final static String PAYSTATE="paystate";
    /**完成时间*/
    public final static String OVERTIME="overtime";
    /**创建时间*/
    public final static String LOGDATE="logdate";
    /**备注*/
    public final static String DESC="desc";
    /**主键*/
    public final static String TABLE_PKS="id";

    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>id</td><td></td></tr>")
            +("<tr><td>orderid</td><td></td></tr>")
            +("<tr><td>productid</td><td></td></tr>")
            +("<tr><td>productname</td><td></td></tr>")
            +("<tr><td>price</td><td></td></tr>")
            +("<tr><td>num</td><td></td></tr>")
            +("<tr><td>payment</td><td></td></tr>")
            +("<tr><td>paidin</td><td></td></tr>")
            +("<tr><td>discount</td><td></td></tr>")
            +("<tr><td>discountdesc</td><td></td></tr>")
            +("<tr><td>pointpayment</td><td></td></tr>")
            +("<tr><td>pointadd</td><td></td></tr>")
            +("<tr><td>status</td><td></td></tr>")
            +("<tr><td>paystate</td><td></td></tr>")
            +("<tr><td>overtime</td><td></td></tr>")
            +("<tr><td>logdate</td><td></td></tr>")
            +("<tr><td>desc</td><td></td></tr>")
            +"</table>";

	/**设置清单编号*/
	public Orderdetail setId(String id) {
		set("id", id);
		return this;
	}

	/**获取清单编号*/
	public String getId() {
		return getStr("id");
	}

	/**设置订单编号*/
	public Orderdetail setOrderid(String orderid) {
		set("orderid", orderid);
		return this;
	}

	/**获取订单编号*/
	public String getOrderid() {
		return getStr("orderid");
	}

	/**设置商品编号*/
	public Orderdetail setProductid(Integer productid) {
		set("productid", productid);
		return this;
	}

	/**获取商品编号*/
	public Integer getProductid() {
		return getInt("productid");
	}

	/**设置商品名称*/
	public Orderdetail setProductname(String productname) {
		set("productname", productname);
		return this;
	}

	/**获取商品名称*/
	public String getProductname() {
		return getStr("productname");
	}

	/**设置单价*/
	public Orderdetail setPrice(Integer price) {
		set("price", price);
		return this;
	}

	/**获取单价*/
	public Integer getPrice() {
		return getInt("price");
	}

	/**设置数量*/
	public Orderdetail setNum(Integer num) {
		set("num", num);
		return this;
	}

	/**获取数量*/
	public Integer getNum() {
		return getInt("num");
	}

	/**设置应收*/
	public Orderdetail setPayment(Integer payment) {
		set("payment", payment);
		return this;
	}

	/**获取应收*/
	public Integer getPayment() {
		return getInt("payment");
	}

	/**设置实收*/
	public Orderdetail setPaidin(Integer paidin) {
		set("paidin", paidin);
		return this;
	}

	/**获取实收*/
	public Integer getPaidin() {
		return getInt("paidin");
	}

	/**设置优惠*/
	public Orderdetail setDiscount(Integer discount) {
		set("discount", discount);
		return this;
	}

	/**获取优惠*/
	public Integer getDiscount() {
		return getInt("discount");
	}

	/**设置优惠说明*/
	public Orderdetail setDiscountdesc(Integer discountdesc) {
		set("discountdesc", discountdesc);
		return this;
	}

	/**获取优惠说明*/
	public Integer getDiscountdesc() {
		return getInt("discountdesc");
	}

	/**设置积分抵扣*/
	public Orderdetail setPointpayment(Integer pointpayment) {
		set("pointpayment", pointpayment);
		return this;
	}

	/**获取积分抵扣*/
	public Integer getPointpayment() {
		return getInt("pointpayment");
	}

	/**设置积分增加*/
	public Orderdetail setPointadd(Integer pointadd) {
		set("pointadd", pointadd);
		return this;
	}

	/**获取积分增加*/
	public Integer getPointadd() {
		return getInt("pointadd");
	}

	/**设置订单状态*/
	public Orderdetail setStatus(Integer status) {
		set("status", status);
		return this;
	}

	/**获取订单状态*/
	public Integer getStatus() {
		return getInt("status");
	}

	/**设置支付状态*/
	public Orderdetail setPaystate(Integer paystate) {
		set("paystate", paystate);
		return this;
	}

	/**获取支付状态*/
	public Integer getPaystate() {
		return getInt("paystate");
	}

	/**设置完成时间*/
	public Orderdetail setOvertime(java.util.Date overtime) {
		set("overtime", overtime);
		return this;
	}

	/**获取完成时间*/
	public java.util.Date getOvertime() {
		return get("overtime");
	}

	/**设置创建时间*/
	public Orderdetail setLogdate(java.util.Date logdate) {
		set("logdate", logdate);
		return this;
	}

	/**获取创建时间*/
	public java.util.Date getLogdate() {
		return get("logdate");
	}

	/**设置备注*/
	public Orderdetail setDesc(String desc) {
		set("desc", desc);
		return this;
	}

	/**获取备注*/
	public String getDesc() {
		return getStr("desc");
	}


}
