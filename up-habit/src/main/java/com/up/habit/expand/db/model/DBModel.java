package com.up.habit.expand.db.model;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.*;
import com.jfinal.plugin.activerecord.cache.ICache;
import com.up.habit.config.ThreadVariable;
import com.up.habit.expand.cache.CacheManager;
import com.up.habit.expand.db.DbConstants;
import com.up.habit.expand.safe.JsoupFilter;
import com.up.habit.kit.NumberKit;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO:
 * <p>
 * @author 王剑洪 on 2019/11/15 15:31
 */
public class DBModel<M extends DBModel> extends Model<M> {
    public static String TABLE_NAME = "";

    public String getTableName() {
        return _getTable().getName();
    }

    public ICache getCache() {
        return this._getConfig().getCache();
    }

    /**
     * TODO:获取表的缓存空间
     *
     * @param
     * @return
     * @author 王剑洪 on 2019/12/26 13:55
     **/
    public String getCacheName() {
        return _getConfig().getName() + ":" + _getTable().getName();
    }

    /**
     * TODO:获取缓存key
     *
     * @param sql
     * @param paras
     * @return
     * @author 王剑洪 on 2019/12/26 13:55
     **/
    public String getCacheKey(String sql, Object... paras) {
        String key = sql;
        if (paras != null && paras.length > 0) {
            for (int i = 0; i < paras.length; i++) {
                key += ":" + String.valueOf(paras[i]);
            }
        }
        key = key.replaceAll("\n", "")
                .replaceAll("\t", " ")
                .replaceAll(" +", " ");
        return key;
    }

    /************************************/

    public void removeAllCache() {
        try {
            CacheManager.me().getCache().removeAll(getCacheName());
        } catch (Exception e) {
        }

    }


    @Override
    public boolean save() {
        filterHtml();
        if (_getTable().hasColumnLabel(DbConstants.column_created)) {
            set(DbConstants.column_created, new Date());
        }
        if (_getTable().hasColumnLabel(DbConstants.column_createdId)) {
            set(DbConstants.column_createdId, ThreadVariable.me().getAdmin());
        }
        if (_getTable().hasColumnLabel(DbConstants.column_sort)) {
            Integer sort = getInt(DbConstants.column_sort);
            sort = NumberKit.isScope(sort, 1, 99999) ? sort : 1;
            set(DbConstants.column_sort, sort);
        }
        removeAllCache();
        return super.save();
    }

    @Override
    public boolean update() {
        filterHtml();
        if (_getTable().hasColumnLabel(DbConstants.column_modified)) {
            set(DbConstants.column_modified, new Date());
        }
        if (_getTable().hasColumnLabel(DbConstants.column_modifyId)) {
            set(DbConstants.column_modifyId, ThreadVariable.me().getAdmin());
        }
        if (_getTable().hasColumnLabel(DbConstants.column_sort)) {
            Integer sort = getInt(DbConstants.column_sort);
            if (NumberKit.isScope(sort, 1, 99999)) {
                set(DbConstants.column_sort, sort);
            }

        }
        removeAllCache();
        return super.update();
    }

    @Override
    public boolean delete() {
        removeAllCache();
        return super.delete();
    }

    @Override
    public boolean deleteById(Object idValue) {
        removeAllCache();
        return super.deleteById(idValue);
    }

    @Override
    public boolean deleteByIds(Object... idValues) {
        removeAllCache();
        return super.deleteByIds(idValues);
    }

    /**
     * TODO:过滤html标签的字段
     *
     * @param
     * @return
     * @author 王剑洪 on 2019/12/26 13:43
     **/
    private void filterHtml() {
        if (StrKit.notBlank(DbConstants.columns_filter_html)) {
            String[] columnNameArray = DbConstants.columns_filter_html.split(",");
            for (String name : columnNameArray) {
                if (_getTable().hasColumnLabel(name)) {
                    set(name, JsoupFilter.getText(get(name)));
                }
            }
        }
    }

    /*((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((*/
    @Override
    public M findFirst(String sql) {
        return this.findFirst(sql);
    }

    @Override
    public M findFirst(String sql, Object... paras) {
        return super.findFirst(sql, paras);
    }

    @Override
    public M findFirst(SqlPara sqlPara) {
        return super.findFirst(sqlPara);
    }

    public M findByIdByCache(Object idValue) {
        ICache cache = this._getConfig().getCache();
        String key = "id:" + idValue;
        M result = cache.get(getCacheName(), key);
        if (result == null) {
            result = this.findById(idValue);
            cache.put(getCacheName(), key, result);
        }
        return result;
    }

    public M findByIdsByCache(Object... idValues) {
        ICache cache = this._getConfig().getCache();
        String key = getCacheKey("id", idValues);
        M result = cache.get(getCacheName(), key);
        if (result == null) {
            result = this.findByIds(idValues);
            cache.put(getCacheName(), key, result);
        }
        return result;
    }

    public M findFirstByCache(String sql) {
        return super.findFirstByCache(getCacheName(), getCacheKey(sql, new Object[0]), sql);
    }

    public M findFirstByCache(String sql, Object... paras) {
        return super.findFirstByCache(getCacheName(), getCacheKey(sql, paras), sql, paras);
    }

    public M findFirstByCache(SqlPara sqlPara) {
        return super.findFirstByCache(getCacheName(), getCacheKey(sqlPara.getSql(), sqlPara.getPara()), sqlPara.getSql(), sqlPara.getPara());
    }

    public List<M> findByCache(String sql) {
        return super.findByCache(getCacheName(), getCacheKey(sql, new Object[0]), sql);
    }

    public List<M> findByCache(String sql, Object... paras) {
        return super.findByCache(getCacheName(), getCacheKey(sql, paras), sql, paras);
    }

    public List<M> findByCache(SqlPara sqlPara) {
        return super.findByCache(getCacheName(), getCacheKey(sqlPara.getSql(), sqlPara.getPara()), sqlPara.getSql(), sqlPara.getPara());
    }

    public Page<M> paginateByCache(int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        return super.paginateByCache(getCacheName(), getCacheKey(select + sqlExceptSelect + ":page:" + pageNumber + ":" + pageSize, new Object[0]),
                pageNumber, pageSize, select, sqlExceptSelect);
    }

    public Page<M> paginateByCache(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) {
        return super.paginateByCache(getCacheName(), getCacheKey(select + sqlExceptSelect + ":page:" + pageNumber + ":" + pageSize, paras), pageNumber, pageSize, select, sqlExceptSelect, paras);
    }

    public Page<M> paginateByCache(int pageNumber, int pageSize, boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        return super.paginateByCache(getCacheName(), getCacheKey(select + sqlExceptSelect + ":page:" + pageNumber + ":isGroupBySql" + pageSize, paras), pageNumber, pageSize, isGroupBySql, select, sqlExceptSelect, paras);
    }

    /*))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))*/


    @Override
    public DaoTemplate<M> template(String key, Map data) {
        key = key.contains(".") ? key : (_getTable().getName() + "." + key);
        return new DaoTemplate(this, key, data);
    }

    @Override
    public DaoTemplate<M> template(String key, Object... paras) {
        key = key.contains(".") ? key : (_getTable().getName() + "." + key);
        return new DaoTemplate(this, key, paras);
    }

    @Override
    public DaoTemplate<M> template(String key, Model model) {
        key = key.contains(".") ? key : (_getTable().getName() + "." + key);
        if (model == null) {
            return this.template(key);
        }
        return (DaoTemplate<M>) super.template(key, model);
    }

    @Override
    public DaoTemplate<M> templateByString(String content, Map data) {
        if (data == null) {
            return this.templateByString(content);
        }
        return new DaoTemplate(true, this, content, data);
    }

    @Override
    public DaoTemplate<M> templateByString(String content, Object... paras) {
        return new DaoTemplate(true, this, content, paras);
    }

    @Override
    public DaoTemplate<M> templateByString(String content, Model model) {
        if (model == null) {
            return this.templateByString(content);
        }
        return (DaoTemplate<M>) super.templateByString(content, model);
    }


    public String queryStr(String sql, Object... paras) {
        Object s = this.queryColumn(sql, paras);
        return s != null ? s.toString() : null;
    }


    public <T> T queryColumn(String sql, Object... paras) {
        List result = find(sql, paras);
        if (result.size() > 0) {
            T temp = (T) result.get(0);
            if (temp instanceof Object[]) {
                throw new ActiveRecordException("Only ONE COLUMN can be queried.");
            } else {
                return temp;
            }
        } else {
            return null;
        }
    }

    public String findStr(String sql, Object... paras) {
        Object s = this.queryColumn(sql, paras);
        return s != null ? s.toString() : null;
    }

    public Integer queryInt(String sql, Object... paras) {
        Number n = this.queryColumn(sql, paras);
        return n != null ? n.intValue() : null;
    }

    public Long queryLong(String sql, Object... paras) {
        Number n = this.queryColumn(sql, paras);
        return n != null ? n.longValue() : null;
    }

}
