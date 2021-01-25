package com.up.habit.expand.db;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.druid.DruidPlugin;
import com.up.habit.Habit;
import com.up.habit.expand.cache.CacheManager;
import com.up.habit.expand.db.kit.ActiveRecordKit;
import com.up.habit.expand.template.ColumnDirective;
import com.up.habit.kit.StrKit;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:
 * <p>
 * @author 王剑洪 on 2019/11/14 19:54
 */
public class DBPlugin implements IPlugin {

    private volatile boolean isStarted = false;
    private List<Ret> pluginsList = new ArrayList<>();


    public DBPlugin() {
        String array = PropKit.get("db.array").trim();
        if (StrKit.isBlank(array)) {
            return;
        }
        String[] dbArray = (StrKit.isBlank(array) ? "main" : array).split(",");
        for (String configName : dbArray) {
            if (StrKit.notBlank(configName)) {
                String url = getStr(configName, "url");
                if (!url.startsWith("jdbc:mysql://")) {
                    url = "jdbc:mysql://" + url + "?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=Asia/Shanghai";
                }
                String user = getStr(configName, "user");
                String password = getStr(configName, "password");
                int initialSize = getInt(configName, "initialSize", 1);
                int minIdle = getInt(configName, "minIdle", 10);
                int maxActive = getInt(configName, "maxActive", 32);
                int maxWait = getInt(configName, "maxWait", DruidDataSource.DEFAULT_MAX_WAIT);
                long timeBetweenEvictionRunsMillis = getLong(configName, "timeBetweenEvictionRunsMillis", DruidDataSource.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
                long minEvictableIdleTimeMillis = getLong(configName, "minEvictableIdleTimeMillis", DruidDataSource.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
                long timeBetweenConnectErrorMillis = getLong(configName, "timeBetweenConnectErrorMillis", DruidDataSource.DEFAULT_TIME_BETWEEN_CONNECT_ERROR_MILLIS);
                boolean logAbandoned = getBoolean(configName, "logAbandoned", false);
                boolean removeAbandoned = getBoolean(configName, "removeAbandoned", false);
                long removeAbandonedTimeoutMillis = getLong(configName, "removeAbandonedTimeoutMillis", 300 * 1000L);

                String map = getStr(configName, "map");
                String sql = getStr(configName, "sql");
                DruidPlugin druid = new DruidPlugin(url, user, password)
                        .setInitialSize(initialSize)//初始连接池大小
                        .setMinIdle(minIdle)//最小空闲连接数
                        .setMaxActive(maxActive)//最大活跃连接数
                        .setMaxWait(maxWait)//配置获取连接等待超时的时间
                        .setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis)//配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
                        .setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis)//配置连接在池中最小生存的时间
                        .setTimeBetweenConnectErrorMillis(timeBetweenConnectErrorMillis)//配置发生错误时多久重连
                        .setRemoveAbandoned(removeAbandoned)//是否打开连接泄露自动检测
                        .setRemoveAbandonedTimeoutMillis(removeAbandonedTimeoutMillis)//连接长时间没有使用，被认为发生泄露时长
                        .setLogAbandoned(logAbandoned);//发生泄露时是否需要输出 log，建议在开启连接泄露检测时开启，方便排错
                //是否打印SQL
                if (Habit.showSql()) {
                    SqlFilter sqlFilter = new SqlFilter();
                    sqlFilter.setStatementSqlPrettyFormat(true);
                    sqlFilter.setStatementSqlFormatOption(new SQLUtils.FormatOption());
                    druid.addFilter(sqlFilter);
                }
                // 配置防火墙加强数据库安全
                WallFilter wallFilter = new WallFilter();
                wallFilter.setDbType(JdbcConstants.MYSQL);
                druid.addFilter(wallFilter);
                //配置监控
                StatFilter statFilter = new StatFilter();
                statFilter.setMergeSql(true);
                statFilter.setLogSlowSql(true);
                statFilter.setSlowSqlMillis(1500);

                druid.addFilter(statFilter);


                ActiveRecordPlugin arp = null;
                if (StrKit.isBlank(configName) || DbKit.MAIN_CONFIG_NAME.equals(configName)) {
                    arp = new ActiveRecordPlugin(druid);
                } else {
                    arp = new ActiveRecordPlugin(configName, druid);
                }
                arp.setDevMode(Habit.dev());
                arp.setTransactionLevel(getInt(configName, "transactionLevel", 2));
                ActiveRecordKit.addMapping(arp, map);
                arp.getEngine().setToClassPathSourceFactory();//sql模板
                arp.setShowSql(false);
                arp.getSqlKit().getEngine().addSharedMethod(new StrKit()).addDirective("column", ColumnDirective.class);
                ActiveRecordKit.addSqlTemplate(arp, sql);
                arp.setCache(CacheManager.me().getCache());
                pluginsList.add(Ret.create("druid", druid).set("arp", arp));
            }

        }

    }

    @Override
    public boolean start() {
        if (isStarted) {
            return true;
        }
        for (Ret ret : pluginsList) {
            DruidPlugin druidPlugin = (DruidPlugin) ret.get("druid");
            ActiveRecordPlugin arp = (ActiveRecordPlugin) ret.get("arp");
            druidPlugin.start();
            arp.start();

        }
        isStarted = true;
        return true;
    }

    @Override
    public boolean stop() {
        for (Ret ret : pluginsList) {
            DruidPlugin druidPlugin = (DruidPlugin) ret.get("druid");
            ActiveRecordPlugin arp = (ActiveRecordPlugin) ret.get("arp");
            druidPlugin.stop();
            arp.stop();

        }
        isStarted = false;
        return true;
    }


    public String getStr(String configName, String key) {
        return PropKit.get("db." + configName + "." + key);
    }

    public int getInt(String configName, String key, Integer defaultValue) {
        String str = PropKit.get("db." + configName + "." + key);
        if (StrKit.isBlank(str)) {
            return defaultValue;
        } else {
            return Integer.parseInt(str);
        }
    }

    public long getLong(String configName, String key, Long defaultValue) {
        String str = PropKit.get("db." + configName + "." + key);
        if (StrKit.isBlank(str)) {
            return defaultValue;
        } else {
            return Long.parseLong(str);
        }

    }

    public boolean getBoolean(String configName, String key, boolean defaultValue) {
        String str = PropKit.get("db." + configName + "." + key);
        if (StrKit.isBlank(str)) {
            return defaultValue;
        } else {
            return Boolean.parseBoolean(str);
        }
    }

}
