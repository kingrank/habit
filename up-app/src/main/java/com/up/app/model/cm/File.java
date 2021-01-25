package com.up.app.model.cm;

import com.up.habit.expand.db.model.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class File extends DBModel<File> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_file";
    /**主键*/
    public final static String TABLE_PKS="id";
    /**文件ID*/
    public final static String ID="id";
    /**文件类型,img:图片*/
    public final static String TYPE="type";
    /**文件路径*/
    public final static String PATH="path";
    /**1-使用中*/
    public final static String USED="used";
    /***/
    public final static String CREATE_BY="create_by";
    /***/
    public final static String CREATE_TIME="create_time";
    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            +("<tr><td>id</td><td>文件ID</td></tr>")
            +("<tr><td>type</td><td>文件类型,img:图片</td></tr>")
            +("<tr><td>path</td><td>文件路径</td></tr>")
            +("<tr><td>used</td><td>1-使用中</td></tr>")
            +("<tr><td>create_by</td><td></td></tr>")
            +("<tr><td>create_time</td><td></td></tr>")
            +"</table>";

	/**
	 * 文件ID
	 */
	public File setId(java.lang.Long id) {
		set("id", id);
		return this;
	}

	/**
	 * 文件ID
	 */
	public java.lang.Long getId() {
		return getLong("id");
	}

	/**
	 * 文件类型,img:图片
	 */
	public File setType(java.lang.String type) {
		set("type", type);
		return this;
	}

	/**
	 * 文件类型,img:图片
	 */
	public java.lang.String getType() {
		return getStr("type");
	}

	/**
	 * 文件路径
	 */
	public File setPath(java.lang.String path) {
		set("path", path);
		return this;
	}

	/**
	 * 文件路径
	 */
	public java.lang.String getPath() {
		return getStr("path");
	}

	/**
	 * 1-使用中
	 */
	public File setUsed(java.lang.Integer used) {
		set("used", used);
		return this;
	}

	/**
	 * 1-使用中
	 */
	public java.lang.Integer getUsed() {
		return getInt("used");
	}

	/**
	 * 
	 */
	public File setCreateBy(java.lang.Integer createBy) {
		set("create_by", createBy);
		return this;
	}

	/**
	 * 
	 */
	public java.lang.Integer getCreateBy() {
		return getInt("create_by");
	}

	/**
	 * 
	 */
	public File setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return this;
	}

	/**
	 * 
	 */
	public java.util.Date getCreateTime() {
		return get("create_time");
	}


}
