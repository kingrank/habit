package com.up.habit.expand.cache.ehcache;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.up.habit.expand.cache.IHabitCache;
import com.up.habit.kit.StrKit;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

import java.util.List;

/**
 * TODO:
 * <p>
 *
 * @author 王剑洪 on 2019/11/15 14:30
 */
public class HabitEhCache implements IHabitCache {

    private static final Log LOG = Log.getLog(HabitEhCache.class);

    private CacheManager cacheManager;
    private static Object locker = new Object();
    private CacheEventListener cacheEventListener;

    public HabitEhCache() {
        String configFileName = PropKit.get("cache.ehcache.configFileName", "");
        if (StrKit.isBlank(configFileName)) {
            cacheManager = CacheManager.create();
        } else {
            if (!configFileName.startsWith("/")) {
                configFileName = PathKit.getRootClassPath() + "/" + configFileName;
            }
            cacheManager = CacheManager.create(configFileName);
        }
    }

    public HabitEhCache(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public CacheEventListener getCacheEventListener() {
        return cacheEventListener;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public Cache getOrAddCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            synchronized (locker) {
                cache = cacheManager.getCache(cacheName);
                if (cache == null) {
                    LOG.debug("Could not find cache config [" + cacheName + "], using default.");
                    cacheManager.addCacheIfAbsent(cacheName);
                    cache = cacheManager.getCache(cacheName);
                    if (cacheEventListener != null) {
                        cache.getCacheEventNotificationService().registerListener(cacheEventListener);
                    }
                }
            }
        }
        return cache;
    }

    @Override
    public <T> T get(String cacheName, Object key) {
        Element element = getOrAddCache(cacheName).get(key);
        return element != null ? (T) element.getObjectValue() : null;
    }

    @Override
    public void put(String cacheName, Object key, Object value) {
        getOrAddCache(cacheName).put(new Element(key, value));
    }

    @Override
    public void put(String cacheName, Object key, Object value, int liveSeconds) {
        if (liveSeconds <= 0) {
            put(cacheName, key, value);
            return;
        }
        Element element = new Element(key, value);
        element.setTimeToLive(liveSeconds);
        getOrAddCache(cacheName).put(element);
    }

    @Override
    public List getKeys(String cacheName) {
        return getOrAddCache(cacheName).getKeys();
    }

    @Override
    public void remove(String cacheName, Object key) {
        getOrAddCache(cacheName).remove(key);
    }

    @Override
    public void removeAll(String cacheName) {
        getOrAddCache(cacheName).removeAll();
    }

    @Override
    public <T> T get(String cacheName, Object key, IDataLoader dataLoader) {
        Object data = get(cacheName, key);
        if (data == null) {
            data = dataLoader.load();
            put(cacheName, key, data);
        }
        return (T) data;
    }

    @Override
    public <T> T get(String cacheName, Object key, IDataLoader dataLoader, int liveSeconds) {
        if (liveSeconds <= 0) {
            return get(cacheName, key, dataLoader);
        }
        Object data = get(cacheName, key);
        if (data == null) {
            data = dataLoader.load();
            put(cacheName, key, data, liveSeconds);
        }
        return (T) data;
    }

    @Override
    public Integer getTtl(String cacheName, Object key) {
        Element element = getOrAddCache(cacheName).get(key);
        return element != null ? element.getTimeToLive() : null;
    }

    @Override
    public void setTtl(String cacheName, Object key, int seconds) {
        Element element = getOrAddCache(cacheName).get(key);
        if (element == null) {
            return;
        }

        element.setTimeToLive(seconds);
        getOrAddCache(cacheName).put(element);
    }
}
