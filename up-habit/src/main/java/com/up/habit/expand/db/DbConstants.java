package com.up.habit.expand.db;

import com.jfinal.kit.PropKit;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:model常量
 *
 * @author 王剑洪 on 2020/1/19 0:11
 */
public class DbConstants {
    /**
     * 数据库创建时间字段
     */
    public static String column_created = PropKit.get("db.model.created", "create_time").trim();
    /**
     * 数据库创建者字段
     */
    public static String column_createdId = PropKit.get("db.model.createdId", "create_by").trim();
    /**
     * 数据库更新时间字段
     */
    public static String column_modified = PropKit.get("db.model.modified", "modify_time").trim();
    /**
     * 数据库更是者字段
     */
    public static String column_modifyId = PropKit.get("db.model.modifyId", "modify_by").trim();
    /**
     * 数据库排序字段
     */
    public static String column_sort = PropKit.get("db.model.sort", "sort").trim();
    /**
     * 数据库删除标识字段
     */
    public static String column_del = PropKit.get("db.model.del", "is_del").trim();

    /**
     * 需要过滤HTML的字段
     */
    public static String columns_filter_html = PropKit.get("db.model.filterHtml", "").trim();


    public static List<String> DEF_COLUMNS = new ArrayList<String>() {{
        this.add(column_createdId);
        this.add(column_created);
        this.add(column_modifyId);
        this.add(column_modified);
        this.add(column_del);

    }};


}
