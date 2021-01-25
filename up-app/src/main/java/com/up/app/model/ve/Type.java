package com.up.app.model.ve;

import com.up.habit.expand.db.model.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 场地类型 实体类
 * @author 王剑洪 on 2021-01-12 09:23:49
 */
@SuppressWarnings("serial")
public class Type extends DBModel<Type> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="ve_type";

    /**编号*/
    public final static String ID="id";
    /**场馆名称*/
    public final static String VENAME="vename";
    /**所属组织机构*/
    public final static String ORGID="orgid";
    /**封面图片*/
    public final static String PIC="pic";
    /**状态*/
    public final static String STATUS="status";
    /**描述*/
    public final static String DESC="desc";
    /**创建时间*/
    public final static String LOGDATE="logdate";
    /**主键*/
    public final static String TABLE_PKS="id";

    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>id</td><td></td></tr>")
            +("<tr><td>vename</td><td></td></tr>")
            +("<tr><td>orgid</td><td></td></tr>")
            +("<tr><td>pic</td><td></td></tr>")
            +("<tr><td>status</td><td></td></tr>")
            +("<tr><td>desc</td><td></td></tr>")
            +("<tr><td>logdate</td><td></td></tr>")
            +"</table>";

	/**设置编号*/
	public Type setId(Integer id) {
		set("id", id);
		return this;
	}

	/**获取编号*/
	public Integer getId() {
		return getInt("id");
	}

	/**设置场馆名称*/
	public Type setVename(String vename) {
		set("vename", vename);
		return this;
	}

	/**获取场馆名称*/
	public String getVename() {
		return getStr("vename");
	}

	/**设置所属组织机构*/
	public Type setOrgid(String orgid) {
		set("orgid", orgid);
		return this;
	}

	/**获取所属组织机构*/
	public String getOrgid() {
		return getStr("orgid");
	}

	/**设置封面图片*/
	public Type setPic(String pic) {
		set("pic", pic);
		return this;
	}

	/**获取封面图片*/
	public String getPic() {
		return getStr("pic");
	}

	/**设置状态*/
	public Type setStatus(Integer status) {
		set("status", status);
		return this;
	}

	/**获取状态*/
	public Integer getStatus() {
		return getInt("status");
	}

	/**设置描述*/
	public Type setDesc(String desc) {
		set("desc", desc);
		return this;
	}

	/**获取描述*/
	public String getDesc() {
		return getStr("desc");
	}

	/**设置创建时间*/
	public Type setLogdate(java.util.Date logdate) {
		set("logdate", logdate);
		return this;
	}

	/**获取创建时间*/
	public java.util.Date getLogdate() {
		return get("logdate");
	}


}
