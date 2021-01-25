package com.up.app.model.ve;

import com.up.habit.expand.db.model.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 计时费率 实体类
 * @author 王剑洪 on 2021-01-14 15:46:51
 */
@SuppressWarnings("serial")
public class Feeinterval extends DBModel<Feeinterval> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="ve_feeinterval";

    /**ID*/
    public final static String ID="id";
	/**所属收费规则*/
	public final static String FEEIID="feeid";
    /**费率类型*/
    public final static String TYPE="type";
    /**开始日期*/
    public final static String STARTDATE="startdate";
    /**结束日期*/
    public final static String ENDDATE="enddate";
    /**开始时间*/
    public final static String STARTTIIME="starttiime";
    /**结束时间*/
    public final static String ENDTIME="endtime";
    /**免费时间*/
    public final static String FREETIME="freetime";
    /**收费界限*/
    public final static String FEECUT="feecut";
    /**收费单位*/
    public final static String UNIT="unit";
    /**单价*/
    public final static String UNITPRICE="unitprice";
    /**会员价*/
    public final static String VIPUNITPRICE="vipunitprice";
    /**积分类型*/
    public final static String POINTSTYPE="pointstype";
    /**积分值*/
    public final static String POINTSVAL="pointsval";
    /**积分上限*/
    public final static String POINTSTOP="pointstop";
    /**抵扣类型*/
    public final static String POINTDEDUCTION="pointdeduction";
    /**抵扣值*/
    public final static String POINTDEDUCTIONVAL="pointdeductionval";
    /**抵扣上限*/
    public final static String POINTDEDUCTIONTOP="pointdeductiontop";
    /**状态*/
    public final static String STATUS="status";
    /**主键*/
    public final static String TABLE_PKS="id";

    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>id</td><td></td></tr>")
			+("<tr><td>feeid</td><td></td></tr>")
            +("<tr><td>type</td><td></td></tr>")
            +("<tr><td>startdate</td><td></td></tr>")
            +("<tr><td>enddate</td><td></td></tr>")
            +("<tr><td>starttiime</td><td></td></tr>")
            +("<tr><td>endtime</td><td></td></tr>")
            +("<tr><td>freetime</td><td></td></tr>")
            +("<tr><td>feecut</td><td></td></tr>")
            +("<tr><td>unit</td><td></td></tr>")
            +("<tr><td>unitprice</td><td></td></tr>")
            +("<tr><td>vipunitprice</td><td></td></tr>")
            +("<tr><td>pointstype</td><td></td></tr>")
            +("<tr><td>pointsval</td><td></td></tr>")
            +("<tr><td>pointstop</td><td></td></tr>")
            +("<tr><td>pointdeduction</td><td></td></tr>")
            +("<tr><td>pointdeductionval</td><td></td></tr>")
            +("<tr><td>pointdeductiontop</td><td></td></tr>")
            +("<tr><td>status</td><td></td></tr>")
            +"</table>";

	/**设置ID*/
	public Feeinterval setId(Integer id) {
		set("id", id);
		return this;
	}

	/**获取ID*/
	public Integer getId() {
		return getInt("id");
	}

	/**设置所属收费规则*/
	public Feeinterval setFeeid(Integer feeid) {
		set("feeid", feeid);
		return this;
	}

	/**获取所属收费规则*/
	public Integer getFeeid() {
		return getInt("feeid");
	}

	/**设置费率类型*/
	public Feeinterval setType(Integer type) {
		set("type", type);
		return this;
	}

	/**获取费率类型*/
	public Integer getType() {
		return getInt("type");
	}

	/**设置开始日期*/
	public Feeinterval setStartdate(java.util.Date startdate) {
		set("startdate", startdate);
		return this;
	}

	/**获取开始日期*/
	public java.util.Date getStartdate() {
		return get("startdate");
	}

	/**设置结束日期*/
	public Feeinterval setEnddate(java.util.Date enddate) {
		set("enddate", enddate);
		return this;
	}

	/**获取结束日期*/
	public java.util.Date getEnddate() {
		return get("enddate");
	}

	/**设置开始时间*/
	public Feeinterval setStarttiime(Integer starttiime) {
		set("starttiime", starttiime);
		return this;
	}

	/**获取开始时间*/
	public Integer getStarttiime() {
		return getInt("starttiime");
	}

	/**设置结束时间*/
	public Feeinterval setEndtime(Integer endtime) {
		set("endtime", endtime);
		return this;
	}

	/**获取结束时间*/
	public Integer getEndtime() {
		return getInt("endtime");
	}

	/**设置免费时间*/
	public Feeinterval setFreetime(Integer freetime) {
		set("freetime", freetime);
		return this;
	}

	/**获取免费时间*/
	public Integer getFreetime() {
		return getInt("freetime");
	}

	/**设置收费界限*/
	public Feeinterval setFeecut(Integer feecut) {
		set("feecut", feecut);
		return this;
	}

	/**获取收费界限*/
	public Integer getFeecut() {
		return getInt("feecut");
	}

	/**设置收费单位*/
	public Feeinterval setUnit(Integer unit) {
		set("unit", unit);
		return this;
	}

	/**获取收费单位*/
	public Integer getUnit() {
		return getInt("unit");
	}

	/**设置单价*/
	public Feeinterval setUnitprice(Integer unitprice) {
		set("unitprice", unitprice);
		return this;
	}

	/**获取单价*/
	public Integer getUnitprice() {
		return getInt("unitprice");
	}

	/**设置会员价*/
	public Feeinterval setVipunitprice(Integer vipunitprice) {
		set("vipunitprice", vipunitprice);
		return this;
	}

	/**获取会员价*/
	public Integer getVipunitprice() {
		return getInt("vipunitprice");
	}

	/**设置积分类型*/
	public Feeinterval setPointstype(Integer pointstype) {
		set("pointstype", pointstype);
		return this;
	}

	/**获取积分类型*/
	public Integer getPointstype() {
		return getInt("pointstype");
	}

	/**设置积分值*/
	public Feeinterval setPointsval(Integer pointsval) {
		set("pointsval", pointsval);
		return this;
	}

	/**获取积分值*/
	public Integer getPointsval() {
		return getInt("pointsval");
	}

	/**设置积分上限*/
	public Feeinterval setPointstop(Integer pointstop) {
		set("pointstop", pointstop);
		return this;
	}

	/**获取积分上限*/
	public Integer getPointstop() {
		return getInt("pointstop");
	}

	/**设置抵扣类型*/
	public Feeinterval setPointdeduction(Integer pointdeduction) {
		set("pointdeduction", pointdeduction);
		return this;
	}

	/**获取抵扣类型*/
	public Integer getPointdeduction() {
		return getInt("pointdeduction");
	}

	/**设置抵扣值*/
	public Feeinterval setPointdeductionval(Integer pointdeductionval) {
		set("pointdeductionval", pointdeductionval);
		return this;
	}

	/**获取抵扣值*/
	public Integer getPointdeductionval() {
		return getInt("pointdeductionval");
	}

	/**设置抵扣上限*/
	public Feeinterval setPointdeductiontop(Integer pointdeductiontop) {
		set("pointdeductiontop", pointdeductiontop);
		return this;
	}

	/**获取抵扣上限*/
	public Integer getPointdeductiontop() {
		return getInt("pointdeductiontop");
	}

	/**设置状态*/
	public Feeinterval setStatus(Integer status) {
		set("status", status);
		return this;
	}

	/**获取状态*/
	public Integer getStatus() {
		return getInt("status");
	}


}
