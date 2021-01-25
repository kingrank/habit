package com.up.habit.expand.gen.model;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class Directory extends DBModel<Directory> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="gen_directory";
    /**主键*/
    public final static String TABLE_PKS="id";
    /***/
    public final static String ID="id";
    /**目录路径*/
    public final static String PATH="path";
    /**目录名称*/
    public final static String NAME="name";
    /**目录图标*/
    public final static String ICON="icon";
    /**数据库表前缀*/
    public final static String PREFIX="prefix";
    /**是否删除*/
    public final static String IS_DEL="is_del";
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
            +("<tr><td>id</td><td></td></tr>")
            +("<tr><td>path</td><td>目录路径</td></tr>")
            +("<tr><td>name</td><td>目录名称</td></tr>")
            +("<tr><td>icon</td><td>目录图标</td></tr>")
            +("<tr><td>prefix</td><td>数据库表前缀</td></tr>")
            +("<tr><td>is_del</td><td>是否删除</td></tr>")
            +("<tr><td>create_by</td><td>创建者</td></tr>")
            +("<tr><td>create_time</td><td>创建时间</td></tr>")
            +("<tr><td>modify_by</td><td>更新者</td></tr>")
            +("<tr><td>modify_time</td><td>更新时间</td></tr>")
            +"</table>";

	/**
	 * 
	 */
	public Directory setId(Integer id) {
		set("id", id);
		return this;
	}

	/**
	 *
	 */
	public Integer getId() {
		return getInt("id");
	}

	/**
	 * 目录路径
	 */
	public Directory setPath(String path) {
		set("path", path);
		return this;
	}

	/**
	 * 目录路径
	 */
	public String getPath() {
		return getStr("path");
	}

	/**
	 * 目录名称
	 */
	public Directory setName(String name) {
		set("name", name);
		return this;
	}

	/**
	 * 目录名称
	 */
	public String getName() {
		return getStr("name");
	}

	/**
	 * 目录图标
	 */
	public Directory setIcon(String icon) {
		set("icon", icon);
		return this;
	}

	/**
	 * 目录图标
	 */
	public String getIcon() {
		return getStr("icon");
	}

	/**
	 * 数据库表前缀
	 */
	public Directory setPrefix(String prefix) {
		set("prefix", prefix);
		return this;
	}

	/**
	 * 数据库表前缀
	 */
	public String getPrefix() {
		return getStr("prefix");
	}

	/**
	 * 是否删除
	 */
	public Directory setIsDel(Integer isDel) {
		set("is_del", isDel);
		return this;
	}

	/**
	 * 是否删除
	 */
	public Integer getIsDel() {
		return getInt("is_del");
	}

	/**
	 * 创建者
	 */
	public Directory setCreateBy(Integer createBy) {
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
	public Directory setCreateTime(java.util.Date createTime) {
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
	public Directory setModifyBy(Integer modifyBy) {
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
	public Directory setModifyTime(java.util.Date modifyTime) {
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
