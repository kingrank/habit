package com.up.habit.expand.cache;

import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.up.habit.expand.cache.ehcache.HabitEhCache;
import com.up.habit.expand.cache.redis.HabitRedisCache;


/**
 * TODO:
 * <p>
 *
 * @author 王剑洪 on 2019/11/15 15:03
 */
public class CacheManager {
    public static final String KEY_TYPE = "cache.type";
    public static final String TYPE_EHCACHE = "ehcache";
    public static final String TYPE_REDIS = "redis";

    private static CacheManager me = new CacheManager();

    public static CacheManager me() {
        return me;
    }

    private CacheManager() {
    }


    public IHabitCache getCache() {
        String cacheType = getType();
        if (TYPE_EHCACHE.equals(cacheType)) {
            return new HabitEhCache();
        } else {
            return new HabitRedisCache();
        }
    }

    public String getType() {
        String cacheType = PropKit.get(KEY_TYPE);
        return StrKit.isBlank(cacheType) ? TYPE_EHCACHE : cacheType;
    }
}
