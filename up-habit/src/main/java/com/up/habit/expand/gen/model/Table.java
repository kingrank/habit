package com.up.habit.expand.gen.model;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 代码生成业务表
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class Table extends DBModel<Table> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="gen_table";
    /**主键*/
    public final static String TABLE_PKS="id";
    /**编号*/
    public final static String ID="id";
    /**表名称*/
    public final static String NAME="name";
    /**表描述*/
    public final static String COMMENT="comment";
    /**业务名称*/
    public final static String BUSINESS="business";
    /**所属目录*/
    public final static String MODULE="module";
    /**目录名称*/
    public final static String MODULE_NAME="module_name";
    /**目录图标*/
    public final static String MODULE_ICON="module_icon";
    /**主键*/
    public final static String PKS="pks";
    /**实体类包名*/
    public final static String MODEL_PKG="model_pkg";
    /**实体类名*/
    public final static String MODEL_NAME="model_name";
    /**service类包名*/
    public final static String SRV_PKG="srv_pkg";
    /**service类m名*/
    public final static String SRV_NAME="srv_name";
    /**控制器包名*/
    public final static String CTR_PKG="ctr_pkg";
    /**控制器包名*/
    public final static String CTR_NAME="ctr_name";
    /**控制器路径*/
    public final static String CTR_KEY="ctr_key";
    /**js文件生成路径*/
    public final static String JS_PATH="js_path";
    /**vue文件生成路径*/
    public final static String VUE_PATH="vue_path";
    /**作者*/
    public final static String AUTHOR="author";
    /**其它生成选项*/
    public final static String OPTIONS="options";
    /**使用的模板（crud单表操作 tree树表操作）*/
    public final static String TPL="tpl";
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
            +("<tr><td>id</td><td>编号</td></tr>")
            +("<tr><td>name</td><td>表名称</td></tr>")
            +("<tr><td>comment</td><td>表描述</td></tr>")
            +("<tr><td>business</td><td>业务名称</td></tr>")
            +("<tr><td>module</td><td>所属目录</td></tr>")
            +("<tr><td>module_name</td><td>目录名称</td></tr>")
            +("<tr><td>module_icon</td><td>目录图标</td></tr>")
            +("<tr><td>pks</td><td>主键</td></tr>")
            +("<tr><td>model_pkg</td><td>实体类包名</td></tr>")
            +("<tr><td>model_name</td><td>实体类名</td></tr>")
            +("<tr><td>srv_pkg</td><td>service类包名</td></tr>")
            +("<tr><td>srv_name</td><td>service类m名</td></tr>")
            +("<tr><td>ctr_pkg</td><td>控制器包名</td></tr>")
            +("<tr><td>ctr_name</td><td>控制器包名</td></tr>")
            +("<tr><td>ctr_key</td><td>控制器路径</td></tr>")
            +("<tr><td>js_path</td><td>js文件生成路径</td></tr>")
            +("<tr><td>vue_path</td><td>vue文件生成路径</td></tr>")
            +("<tr><td>author</td><td>作者</td></tr>")
            +("<tr><td>options</td><td>其它生成选项</td></tr>")
            +("<tr><td>tpl</td><td>使用的模板（crud单表操作 tree树表操作）</td></tr>")
            +("<tr><td>remark</td><td>备注</td></tr>")
            +("<tr><td>create_by</td><td>创建者</td></tr>")
            +("<tr><td>create_time</td><td>创建时间</td></tr>")
            +("<tr><td>modify_by</td><td>更新者</td></tr>")
            +("<tr><td>modify_time</td><td>更新时间</td></tr>")
            +"</table>";

	/**
	 * 编号
	 */
	public Table setId(Integer id) {
		set("id", id);
		return this;
	}

	/**
	 * 编号
	 */
	public Integer getId() {
		return getInt("id");
	}

	/**
	 * 表名称
	 */
	public Table setName(String name) {
		set("name", name);
		return this;
	}

	/**
	 * 表名称
	 */
	public String getName() {
		return getStr("name");
	}

	/**
	 * 表描述
	 */
	public Table setComment(String comment) {
		set("comment", comment);
		return this;
	}

	/**
	 * 表描述
	 */
	public String getComment() {
		return getStr("comment");
	}

	/**
	 * 业务名称
	 */
	public Table setBusiness(String business) {
		set("business", business);
		return this;
	}

	/**
	 * 业务名称
	 */
	public String getBusiness() {
		return getStr("business");
	}

	/**
	 * 所属目录
	 */
	public Table setModule(String module) {
		set("module", module);
		return this;
	}

	/**
	 * 所属目录
	 */
	public String getModule() {
		return getStr("module");
	}

	/**
	 * 目录名称
	 */
	public Table setModuleName(String moduleName) {
		set("module_name", moduleName);
		return this;
	}

	/**
	 * 目录名称
	 */
	public String getModuleName() {
		return getStr("module_name");
	}

	/**
	 * 目录图标
	 */
	public Table setModuleIcon(String moduleIcon) {
		set("module_icon", moduleIcon);
		return this;
	}

	/**
	 * 目录图标
	 */
	public String getModuleIcon() {
		return getStr("module_icon");
	}

	/**
	 * 主键
	 */
	public Table setPks(String pks) {
		set("pks", pks);
		return this;
	}

	/**
	 * 主键
	 */
	public String getPks() {
		return getStr("pks");
	}

	/**
	 * 实体类包名
	 */
	public Table setModelPkg(String modelPkg) {
		set("model_pkg", modelPkg);
		return this;
	}

	/**
	 * 实体类包名
	 */
	public String getModelPkg() {
		return getStr("model_pkg");
	}

	/**
	 * 实体类名
	 */
	public Table setModelName(String modelName) {
		set("model_name", modelName);
		return this;
	}

	/**
	 * 实体类名
	 */
	public String getModelName() {
		return getStr("model_name");
	}

	/**
	 * service类包名
	 */
	public Table setSrvPkg(String srvPkg) {
		set("srv_pkg", srvPkg);
		return this;
	}

	/**
	 * service类包名
	 */
	public String getSrvPkg() {
		return getStr("srv_pkg");
	}

	/**
	 * service类m名
	 */
	public Table setSrvName(String srvName) {
		set("srv_name", srvName);
		return this;
	}

	/**
	 * service类m名
	 */
	public String getSrvName() {
		return getStr("srv_name");
	}

	/**
	 * 控制器包名
	 */
	public Table setCtrPkg(String ctrPkg) {
		set("ctr_pkg", ctrPkg);
		return this;
	}

	/**
	 * 控制器包名
	 */
	public String getCtrPkg() {
		return getStr("ctr_pkg");
	}

	/**
	 * 控制器包名
	 */
	public Table setCtrName(String ctrName) {
		set("ctr_name", ctrName);
		return this;
	}

	/**
	 * 控制器包名
	 */
	public String getCtrName() {
		return getStr("ctr_name");
	}

	/**
	 * 控制器路径
	 */
	public Table setCtrKey(String ctrKey) {
		set("ctr_key", ctrKey);
		return this;
	}

	/**
	 * 控制器路径
	 */
	public String getCtrKey() {
		return getStr("ctr_key");
	}

	/**
	 * js文件生成路径
	 */
	public Table setJsPath(String jsPath) {
		set("js_path", jsPath);
		return this;
	}

	/**
	 * js文件生成路径
	 */
	public String getJsPath() {
		return getStr("js_path");
	}

	/**
	 * vue文件生成路径
	 */
	public Table setVuePath(String vuePath) {
		set("vue_path", vuePath);
		return this;
	}

	/**
	 * vue文件生成路径
	 */
	public String getVuePath() {
		return getStr("vue_path");
	}

	/**
	 * 作者
	 */
	public Table setAuthor(String author) {
		set("author", author);
		return this;
	}

	/**
	 * 作者
	 */
	public String getAuthor() {
		return getStr("author");
	}

	/**
	 * 其它生成选项
	 */
	public Table setOptions(String options) {
		set("options", options);
		return this;
	}

	/**
	 * 其它生成选项
	 */
	public String getOptions() {
		return getStr("options");
	}

	/**
	 * 使用的模板（crud单表操作 tree树表操作）
	 */
	public Table setTpl(String tpl) {
		set("tpl", tpl);
		return this;
	}

	/**
	 * 使用的模板（crud单表操作 tree树表操作）
	 */
	public String getTpl() {
		return getStr("tpl");
	}

	/**
	 * 备注
	 */
	public Table setRemark(String remark) {
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
	public Table setCreateBy(Integer createBy) {
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
	public Table setCreateTime(java.util.Date createTime) {
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
	public Table setModifyBy(Integer modifyBy) {
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
	public Table setModifyTime(java.util.Date modifyTime) {
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
