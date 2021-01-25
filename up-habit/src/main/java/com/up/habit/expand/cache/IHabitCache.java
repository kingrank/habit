package com.up.habit.expand.cache;

import com.jfinal.plugin.activerecord.cache.ICache;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.util.List;

/**
 * TODO:
 * <p>
 *
 * @author 王剑洪 on 2019/11/15 11:01
 */
public interface IHabitCache extends ICache {
    /**
     * 获取
     *
     * @param cacheName
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String cacheName, Object key);

    /**
     * 设置
     *
     * @param cacheName
     * @param key
     * @param value
     */
    public void put(String cacheName, Object key, Object value);

    /**
     * 设置,带过期时间
     *
     * @param cacheName
     * @param key
     * @param value
     * @param liveSeconds
     */
    public void put(String cacheName, Object key, Object value, int liveSeconds);

    /**
     * 获取全部
     * @param cacheName
     * @return
     */
    public List getKeys(String cacheName);

    public void remove(String cacheName, Object key);

    public void removeAll(String cacheName);

    public <T> T get(String cacheName, Object key, IDataLoader dataLoader);

    public <T> T get(String cacheName, Object key, IDataLoader dataLoader, int liveSeconds);

    public Integer getTtl(String cacheName, Object key);

    public void setTtl(String cacheName, Object key, int seconds);
}
