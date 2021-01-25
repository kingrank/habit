package com.up.habit.expand.cache;

import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.plugin.redis.serializer.ISerializer;
import redis.clients.util.SafeEncoder;

/**
 * TODO:缓存插件
 * <p>
 *
 * @author 王剑洪 on 2019/11/15 10:27
 */
public class CachePlugin implements IPlugin {

    private EhCachePlugin ehCachePlugin = null;
    private RedisPlugin redisPlugin = null;

    public CachePlugin() {
        if (CacheManager.TYPE_REDIS.equals(CacheManager.me().getType())) {
            redisPlugin = getRedisPlugin();
            redisPlugin.setSerializer(new ISerializer() {
                @Override
                public byte[] keyToBytes(String key) {
                    return SafeEncoder.encode(key);
                }

                @Override
                public String keyFromBytes(byte[] bytes) {
                    return SafeEncoder.encode(bytes);
                }

                @Override
                public byte[] fieldToBytes(Object field) {
                    return valueToBytes(field);
                }

                @Override
                public Object fieldFromBytes(byte[] bytes) {
                    return valueFromBytes(bytes);
                }

                @Override
                public byte[] valueToBytes(Object value) {
                    return SafeEncoder.encode(value.toString());
                }

                @Override
                public Object valueFromBytes(byte[] bytes) {
                    if (bytes == null || bytes.length == 0)
                        return null;
                    return SafeEncoder.encode(bytes);
                }
            });
        } else {
            ehCachePlugin = new EhCachePlugin();
        }
    }

    public CachePlugin(ISerializer serializer) {
        String cacheType = PropKit.get("cache.type");
        if ("redis".equals(cacheType)) {
            redisPlugin = getRedisPlugin();
            redisPlugin.setSerializer(serializer);
        } else {
            ehCachePlugin = new EhCachePlugin();
        }
    }

    @Override
    public boolean start() {
        if (ehCachePlugin != null) {
            return ehCachePlugin.start();
        }
        if (redisPlugin != null) {
            return redisPlugin.start();
        }
        ehCachePlugin = new EhCachePlugin();
        return ehCachePlugin.start();
    }

    @Override
    public boolean stop() {
        if (ehCachePlugin != null) {
            return ehCachePlugin.stop();
        }
        if (redisPlugin != null) {
            return redisPlugin.stop();
        }
        return false;
    }


    public RedisPlugin getRedisPlugin() {
        String cacheName = PropKit.get("cache.name");
        String host = PropKit.get("cache.host");
        Integer port = PropKit.getInt("cache.port", null);
        Integer timeout = PropKit.getInt("cache.timeout", null);
        String password = PropKit.get("cache.password");
        Integer database = PropKit.getInt("cache.database", null);
        String clientName = PropKit.get("cache.clientName");
        if (StrKit.isBlank(cacheName))
            throw new RuntimeException("cacheName can not be blank.");
        if (StrKit.isBlank(host))
            throw new RuntimeException("host can not be blank.");
        RedisPlugin redisPlugin = null;
        if (StrKit.isBlank(clientName)) {
            redisPlugin = new RedisPlugin(cacheName, host, port, timeout, password, database);
        } else {
            redisPlugin = new RedisPlugin(cacheName, host, port, timeout, password, database, clientName);
        }
        return redisPlugin;
    }
}
