package com.up.app.model.ve;

import com.up.app.model.dt.Member;
import com.up.habit.expand.db.model.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 收费规则 实体类
 * @author 王剑洪 on 2021-01-14 15:38:51
 */
@SuppressWarnings("serial")
public class Fee extends DBModel<Fee> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="ve_fee";

    /**ID*/
    public final static String ID="id";
	/**所属机构*/
	public final static String ORGID="orgid";
    /**费率名称*/
    public final static String NAME="name";
    /**状态*/
    public final static String STATUS="status";
    /**主键*/
    public final static String TABLE_PKS="id";

    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>id</td><td></td></tr>")
			+("<tr><td>orgid</td><td></td></tr>")
            +("<tr><td>name</td><td></td></tr>")
            +("<tr><td>status</td><td></td></tr>")
            +"</table>";

	/**设置ID*/
	public Fee setId(Integer id) {
		set("id", id);
		return this;
	}

	/**获取ID*/
	public Integer getId() {
		return getInt("id");
	}

	/**设置所属机构*/
	public Fee setOrgid(String orgid) {
		set("orgid", orgid);
		return this;
	}
	/**获取所属机构*/
	public String getOrgid() {
		return getStr("orgid");
	}


	/**设置费率名称*/
	public Fee setName(String name) {
		set("name", name);
		return this;
	}

	/**获取费率名称*/
	public String getName() {
		return getStr("name");
	}

	/**设置状态*/
	public Fee setStatus(Integer status) {
		set("status", status);
		return this;
	}

	/**获取状态*/
	public Integer getStatus() {
		return getInt("status");
	}


}
