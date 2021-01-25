package com.up.habit.expand.cache.redis;

import com.jfinal.plugin.ehcache.IDataLoader;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.up.habit.expand.cache.IHabitCache;
import com.up.habit.kit.StrKit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * TODO:
 * <p>
 *
 * @author 王剑洪 on 2019/11/15 14:38
 */
public class HabitRedisCache implements IHabitCache {

    private String cacheName;
    private Cache redis;

    public HabitRedisCache() {
    }


    public HabitRedisCache(String cacheName) {
        this.cacheName = cacheName;
    }

    public Cache getRedis() {
        if (redis != null) {
            return redis;
        }
        if (StrKit.isBlank(cacheName)) {
            this.redis = Redis.use();
        } else {
            this.redis = Redis.use(cacheName);
        }
        return redis;
    }


    private String buildKey(String cacheName, Object key) {
        if (key instanceof Number) {
            return String.format("%s:I:%s", cacheName, key);
        } else {
            Class keyClass = key.getClass();
            if (String.class.equals(keyClass) ||
                    StringBuffer.class.equals(keyClass) ||
                    StringBuilder.class.equals(keyClass)) {
                return String.format("%s:S:%s", cacheName, key);
            }
        }
        return String.format("%s:O:%s", cacheName, key);
    }

    @Override
    public <T> T get(String cacheName, Object key) {
        return getRedis().get(buildKey(cacheName, key));
    }

    @Override
    public void put(String cacheName, Object key, Object value) {
        if (value == null) {
            // if value is null : java.lang.NullPointerException: null at redis.clients.jedis.Protocol.sendCommand(Protocol.java:99)
            return;
        }
        getRedis().set(buildKey(cacheName, key), value);
    }

    @Override
    public void put(String cacheName, Object key, Object value, int liveSeconds) {
        if (value == null) {
            // if value is null : java.lang.NullPointerException: null at redis.clients.jedis.Protocol.sendCommand(Protocol.java:99)
            return;
        }
        if (liveSeconds <= 0) {
            put(cacheName, key, value);
            return;
        }

        getRedis().setex(buildKey(cacheName, key), liveSeconds, value);
    }

    @Override
    public List getKeys(String cacheName) {
        Set<String> keyset = getRedis().keys(cacheName + ":*");
        if (keyset == null || keyset.size() == 0) {
            return null;
        }
        List<String> keys = new ArrayList<>(keyset);
        for (int i = 0; i < keys.size(); i++) {
            keys.set(i, keys.get(i).substring(cacheName.length() + 3));
        }
        return keys;
    }

    @Override
    public void remove(String cacheName, Object key) {
        getRedis().del(buildKey(cacheName, key));
    }

    @Override
    public void removeAll(String cacheName) {
        String[] keys = new String[]{};
        keys = getRedis().keys(cacheName + ":*").toArray(keys);
        if (keys != null && keys.length > 0) {
            getRedis().del(keys);
        }
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
        Long ttl = getRedis().ttl(buildKey(cacheName, key));
        return ttl != null ? ttl.intValue() : null;
    }

    @Override
    public void setTtl(String cacheName, Object key, int seconds) {
        getRedis().expire(buildKey(cacheName, key), seconds);
    }
}
