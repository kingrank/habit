package com.up.habit.expand.db.model;

import com.jfinal.plugin.activerecord.*;
import com.jfinal.plugin.activerecord.cache.ICache;
import com.up.habit.expand.db.listener.Batch;
import com.up.habit.kit.StrKit;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

/**
 * TODO:
 * <p>
 *
 * @author 王剑洪 on 2019/11/26 11:54
 */
public class DaoTemplate<M extends DBModel> extends com.jfinal.plugin.activerecord.DaoTemplate<M> {


    public DaoTemplate(DBModel dao, String key, Map data) {
        super(dao, key, data);
        this.dao = dao;
    }

    public DaoTemplate(DBModel dao, String key, Object... paras) {
        super(dao, key, paras);
    }

    public DaoTemplate(boolean byString, DBModel dao, String content, Map data) {
        super(byString, dao, content, data);
        this.dao = dao;
    }

    public DaoTemplate(boolean byString, DBModel dao, String content, Object... paras) {
        super(byString, dao, content, paras);
        this.dao = dao;
    }

    public String getCacheName() {
        return ((DBModel) dao).getCacheName();
    }

    public String getCacheKey() {
        return ((DBModel) dao).getCacheKey(this.sqlPara.getSql(), this.sqlPara.getPara());
    }

    public List<M> findByCache() {
        return super.findByCache(getCacheName(), getCacheKey());
    }

    public M findFirstByCache() {
        return super.findFirstByCache(getCacheName(), getCacheKey());
    }

    public Page<M> paginateByCache(int pageNumber, int pageSize) {
        return super.paginateByCache(getCacheName(), getCacheKey(), pageNumber, pageSize);
    }

    public Page<M> paginateByCache(int pageNumber, int pageSize, boolean isGroupBySql) {
        return super.paginateByCache(getCacheName(), getCacheKey(), pageNumber, pageSize, isGroupBySql);
    }


    public DbPro db() {
        return Db.use();
    }

    public boolean update() {
        ((DBModel) dao).removeAllCache();
        int count = db().update(sqlPara);
        return count >= 1;
    }

    public boolean batch() {
        return batch(new Batch());
    }

    public boolean batchNonTransaction() {
        String[] sqlArray = sqlPara.getSql().split(";");
        int index = 0;
        for (int i = 0; i < sqlArray.length; i++) {
            String sql = sqlArray[i];
            sql = sql.replaceAll("\n", "").replaceAll("\t", " ").replaceAll(" +", " ");
            if (StrKit.isBlank(sql)) {
                continue;
            }
            String tmp = sql.replace("?", "??");
            int paraCount = tmp.length() - sql.length();
            Object[] paras = Arrays.copyOfRange(sqlPara.getPara(), index, paraCount + index);

            int count = db().update(sqlArray[i], paras);
            index += paraCount;
            if (count < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean batch(Batch batch) {
        boolean res = db().tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                boolean goon = batch.batchBefore();
                if (!goon) {
                    return false;
                }
                goon = batchNonTransaction();
                if (!goon) {
                    return false;
                }
                goon = batch.batchAfter();
                return goon;
            }
        });
        return res;
    }

    public String queryStr() {
        return db().queryStr(sqlPara.getSql(), sqlPara.getPara());
    }

    public long queryLong() {
        Long tmp = db().queryLong(sqlPara.getSql(), sqlPara.getPara());
        if (tmp == null) {
            return 0;
        }
        return tmp;
    }

    public int queryInt() {
        Integer tmp = db().queryInt(sqlPara.getSql(), sqlPara.getPara());
        if (tmp == null) {
            return 0;
        }
        return tmp;
    }

    public BigDecimal queryBigDecimal() {
        return db().queryBigDecimal(sqlPara.getSql(), sqlPara.getPara());
    }

    public Date queryDate() {
        return db().queryDate(sqlPara.getSql(), sqlPara.getPara());
    }

    public <T> List<T> queryList() {
        return (List<T>) db().query(sqlPara.getSql(), sqlPara.getPara());
    }

    public <T> T getCache() {
        DBModel model = (DBModel) dao;
        return model.getCache().get(getCacheName(), getCacheKey());
    }

    public <T> List<T> queryListByCache() {
        ICache cache = ((DBModel) dao).getCache();
        List<T> result = cache.get(getCacheName(), getCacheKey());
        if (result == null) {
            result = db().query(sqlPara.getSql(), sqlPara.getPara());
            cache.put(getCacheName(), getCacheKey(), result);
        }
        return result;
    }

    public List<Integer> queryIntList() {
        List list = db().query(sqlPara.getSql(), sqlPara.getPara());
        List<Integer> returnList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            returnList.add(Integer.parseInt(String.valueOf(list.get(i))));
        }
        return returnList;
    }

    public List<Integer> queryIntListByCache() {
        ICache cache = ((DBModel) dao).getCache();
        List<Integer> result = cache.get(getCacheName(), getCacheKey());
        if (result == null) {
            result = queryIntList();
            cache.put(getCacheName(), getCacheKey(), result);
        }
        return result;
    }

    public SqlPara getSql() {
        return this.sqlPara;
    }

}
