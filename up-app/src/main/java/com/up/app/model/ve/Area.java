package com.up.app.model.ve;

import com.up.habit.expand.db.model.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 场地管理 实体类
 * @author 王剑洪 on 2021-01-13 17:22:04
 */
@SuppressWarnings("serial")
public class Area extends DBModel<Area> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="ve_area";

    /**主键*/
    public final static String ID="id";
	/**所属机构*/
	public final static String ORGID="orgid";
    /**场地名称*/
    public final static String NAME="name";
    /**场地类型*/
    public final static String TYPE="type";
    /**状态*/
    public final static String STATUS="status";
    /**是否公开*/
    public final static String ISSHOW="isshow";
    /**收费规则*/
    public final static String FEEID="feeid";
    /**封面图片*/
    public final static String PIC="pic";
    /**场地描述*/
    public final static String DES="des";
    /**营业时间*/
    public final static String BUSINESSTIME="businesstime";
    /**主键*/
    public final static String TABLE_PKS="id";

    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>id</td><td></td></tr>")
			+("<tr><td>orgid</td><td></td></tr>")
            +("<tr><td>name</td><td></td></tr>")
            +("<tr><td>type</td><td></td></tr>")
            +("<tr><td>status</td><td></td></tr>")
            +("<tr><td>isshow</td><td></td></tr>")
            +("<tr><td>feeid</td><td></td></tr>")
            +("<tr><td>pic</td><td></td></tr>")
            +("<tr><td>des</td><td></td></tr>")
            +("<tr><td>businesstime</td><td></td></tr>")
            +"</table>";

	/**设置主键*/
	public Area setId(Integer id) {
		set("id", id);
		return this;
	}

	/**获取主键*/
	public Integer getId() {
		return getInt("id");
	}


	/**设置所属机构*/
	public Area setOrgid(String orgid) {
		set("orgid", orgid);
		return this;
	}
	/**获取所属机构*/
	public String getOrgid() {
		return getStr("orgid");
	}
	/**设置场地名称*/
	public Area setName(String name) {
		set("name", name);
		return this;
	}

	/**获取场地名称*/
	public String getName() {
		return getStr("name");
	}

	/**设置场地类型*/
	public Area setType(Integer type) {
		set("type", type);
		return this;
	}

	/**获取场地类型*/
	public Integer getType() {
		return getInt("type");
	}

	/**设置状态*/
	public Area setStatus(String status) {
		set("status", status);
		return this;
	}

	/**获取状态*/
	public String getStatus() {
		return getStr("status");
	}

	/**设置是否公开*/
	public Area setIsshow(String isshow) {
		set("isshow", isshow);
		return this;
	}

	/**获取是否公开*/
	public String getIsshow() {
		return getStr("isshow");
	}

	/**设置收费规则*/
	public Area setFeeid(Integer feeid) {
		set("feeid", feeid);
		return this;
	}

	/**获取收费规则*/
	public Integer getFeeid() {
		return getInt("feeid");
	}

	/**设置封面图片*/
	public Area setPic(String pic) {
		set("pic", pic);
		return this;
	}

	/**获取封面图片*/
	public String getPic() {
		return getStr("pic");
	}

	/**设置场地描述*/
	public Area setDes(String des) {
		set("des", des);
		return this;
	}

	/**获取场地描述*/
	public String getDes() {
		return getStr("des");
	}

	/**设置营业时间*/
	public Area setBusinesstime(String businesstime) {
		set("businesstime", businesstime);
		return this;
	}

	/**获取营业时间*/
	public String getBusinesstime() {
		return getStr("businesstime");
	}


}
