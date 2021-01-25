package com.up.app.model.cm;

import com.jfinal.plugin.activerecord.IBean;
import com.up.habit.expand.db.model.DBModel;

/**
 * 菜单权限表
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class Menu extends DBModel<Menu> implements IBean {
	/**表名*/
    public final static String TABLE_NAME="cm_menu";
    /**主键*/
    public final static String TABLE_PKS="id";
    /**菜单ID*/
    public final static String ID="id";
    /**父菜单ID*/
    public final static String P_ID="p_id";
    /**菜单图标*/
    public final static String ICON="icon";
    /**菜单名称*/
    public final static String NAME="name";
    /**菜单类型@1:目录,2:菜单,3:按钮*/
    public final static String TYPE="type";
    /**是否链接@0:否,1:是*/
    public final static String IS_LINK="is_link";
    /**路由地址*/
    public final static String PATH="path";
    /**组件路径*/
    public final static String COMPONENT="component";
    /**菜单状态@0:显示,1:隐藏*/
    public final static String VISIBLE="visible";
    /**控制器*/
    public final static String CONTROLLER="controller";
    /**权限标识*/
    public final static String ACTION="action";
    /**顺序*/
    public final static String SORT="sort";
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
            +("<tr><td>id</td><td>菜单ID</td></tr>")
            +("<tr><td>p_id</td><td>父菜单ID</td></tr>")
            +("<tr><td>icon</td><td>菜单图标</td></tr>")
            +("<tr><td>name</td><td>菜单名称</td></tr>")
            +("<tr><td>type</td><td>菜单类型@1:目录,2:菜单,3:按钮</td></tr>")
            +("<tr><td>is_link</td><td>是否链接@0:否,1:是</td></tr>")
            +("<tr><td>path</td><td>路由地址</td></tr>")
            +("<tr><td>component</td><td>组件路径</td></tr>")
            +("<tr><td>visible</td><td>菜单状态@0:显示,1:隐藏</td></tr>")
            +("<tr><td>controller</td><td>控制器</td></tr>")
            +("<tr><td>action</td><td>权限标识</td></tr>")
            +("<tr><td>sort</td><td>顺序</td></tr>")
            +("<tr><td>remark</td><td>备注</td></tr>")
            +("<tr><td>create_by</td><td>创建者</td></tr>")
            +("<tr><td>create_time</td><td>创建时间</td></tr>")
            +("<tr><td>modify_by</td><td>更新者</td></tr>")
            +("<tr><td>modify_time</td><td>更新时间</td></tr>")
            +"</table>";

	/**
	 * 菜单ID
	 */
	public Menu setId(Integer id) {
		set("id", id);
		return this;
	}

	/**
	 * 菜单ID
	 */
	public Integer getId() {
		return getInt("id");
	}

	/**
	 * 父菜单ID
	 */
	public Menu setPId(Integer pId) {
		set("p_id", pId);
		return this;
	}

	/**
	 * 父菜单ID
	 */
	public Integer getPId() {
		return getInt("p_id");
	}

	/**
	 * 菜单图标
	 */
	public Menu setIcon(String icon) {
		set("icon", icon);
		return this;
	}

	/**
	 * 菜单图标
	 */
	public String getIcon() {
		return getStr("icon");
	}

	/**
	 * 菜单名称
	 */
	public Menu setName(String name) {
		set("name", name);
		return this;
	}

	/**
	 * 菜单名称
	 */
	public String getName() {
		return getStr("name");
	}

	/**
	 * 菜单类型@1:目录,2:菜单,3:按钮
	 */
	public Menu setType(Integer type) {
		set("type", type);
		return this;
	}

	/**
	 * 菜单类型@1:目录,2:菜单,3:按钮
	 */
	public Integer getType() {
		return getInt("type");
	}

	/**
	 * 是否链接@0:否,1:是
	 */
	public Menu setIsLink(Integer isLink) {
		set("is_link", isLink);
		return this;
	}

	/**
	 * 是否链接@0:否,1:是
	 */
	public Integer getIsLink() {
		return getInt("is_link");
	}

	/**
	 * 路由地址
	 */
	public Menu setPath(String path) {
		set("path", path);
		return this;
	}

	/**
	 * 路由地址
	 */
	public String getPath() {
		return getStr("path");
	}

	/**
	 * 组件路径
	 */
	public Menu setComponent(String component) {
		set("component", component);
		return this;
	}

	/**
	 * 组件路径
	 */
	public String getComponent() {
		return getStr("component");
	}

	/**
	 * 菜单状态@0:显示,1:隐藏
	 */
	public Menu setVisible(Integer visible) {
		set("visible", visible);
		return this;
	}

	/**
	 * 菜单状态@0:显示,1:隐藏
	 */
	public Integer getVisible() {
		return getInt("visible");
	}

	/**
	 * 控制器
	 */
	public Menu setController(String controller) {
		set("controller", controller);
		return this;
	}

	/**
	 * 控制器
	 */
	public String getController() {
		return getStr("controller");
	}

	/**
	 * 权限标识
	 */
	public Menu setAction(String action) {
		set("action", action);
		return this;
	}

	/**
	 * 权限标识
	 */
	public String getAction() {
		return getStr("action");
	}

	/**
	 * 顺序
	 */
	public Menu setSort(Integer sort) {
		set("sort", sort);
		return this;
	}

	/**
	 * 顺序
	 */
	public Integer getSort() {
		return getInt("sort");
	}

	/**
	 * 备注
	 */
	public Menu setRemark(String remark) {
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
	public Menu setCreateBy(Integer createBy) {
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
	public Menu setCreateTime(java.util.Date createTime) {
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
	public Menu setModifyBy(Integer modifyBy) {
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
	public Menu setModifyTime(java.util.Date modifyTime) {
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
