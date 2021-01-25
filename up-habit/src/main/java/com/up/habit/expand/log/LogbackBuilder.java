package com.up.habit.expand.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.OptionHelper;
import com.jfinal.core.ActionReporter;
import com.jfinal.ext.kit.DateKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO:
 * <p>
 * @author 王剑洪 on 2019/10/22 16:49
 */
public class LogbackBuilder {
    private static final Map<Object, Logger> container = new HashMap<>();
    static String logPath = "logback";
    static int rootLevel;
    static Logger rootLog;

    static {
        logPath = PropKit.get("log.folder", "log/");
        rootLog = (Logger) LoggerFactory.getILoggerFactory().getLogger(Logger.ROOT_LOGGER_NAME);
        rootLevel = getLevel("log.root.level", true);
        Level level = null;
        if (rootLevel >= Level.ERROR_INT) {
            level = Level.ERROR;
        } else if (rootLevel >= Level.WARN_INT) {
            level = Level.WARN;
        } else if (rootLevel >= Level.INFO_INT) {
            level = Level.INFO;
        } else {
            level = Level.DEBUG;
        }
        rootLog.setLevel(level);
    }


    public Logger getLogger(Object object) {
        Logger logger = container.get(object);
        if (logger != null) {
            return logger;
        }
        synchronized (LogbackBuilder.class) {
            logger = container.get(object);
            if (logger != null) {
                return logger;
            }
            logger = build(object);
            container.put(object, logger);
        }
        return logger;
    }

    private Logger build(Object object) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = null;
        String name = "";
        if (object instanceof String) {
            name = object + "";
            logger = context.getLogger(name);
            logger = addAppenderLog(context, logger, name, false);
        } else {
            Class clz = (Class) object;
            name = clz.getName();
            logger = context.getLogger(clz);

        }
        logger = addAppenderLog(context, logger, name, true);

        logger.setAdditive(false);
        return logger;
    }

    /**
     * @param name
     * @return 40000-ERROR,30000-WARN,20000-INFO,10000-DEBUG
     */
    private Logger addAppenderLog(LoggerContext context, Logger logger, String name, boolean isConsole) {
        int level = getLevel("log.default.level", true);
        Integer nameLevel = getLevel("log." + name + ".level", false);
        if (nameLevel != null) {
            level = nameLevel.intValue();
        }
        level = level > rootLevel ? level : rootLevel;
        boolean isLevelFile = isLevelFile();
        if (level <= Level.ERROR_INT) {
            if (isConsole) {
                logger.addAppender(consoleAppender(name, context, Level.ERROR));
            } else {
                if (isLevelFile) {
                    logger.addAppender(fileAppender(name, context, Level.ERROR));
                }
            }

        }
        if (level <= Level.WARN_INT) {
            if (isConsole) {
                logger.addAppender(consoleAppender(name, context, Level.WARN));
            } else {
                if (isLevelFile) {
                    logger.addAppender(fileAppender(name, context, Level.WARN));
                }
            }
        }
        if (level <= Level.INFO_INT) {
            if (isConsole) {
                logger.addAppender(consoleAppender(name, context, Level.INFO));
            } else {
                if (isLevelFile) {
                    logger.addAppender(fileAppender(name, context, Level.INFO));
                }
            }
        }
        if (level <= Level.DEBUG_INT) {
            if (isConsole) {
                logger.addAppender(consoleAppender(name, context, Level.DEBUG));
            } else {
                if (isLevelFile) {
                    logger.addAppender(fileAppender(name, context, Level.DEBUG));
                }
            }
        }
        if (!isLevelFile && !isConsole) {
            logger.addAppender(fileAppender(name, context));
        }

        return logger;
    }


    public RollingFileAppender fileAppender(String name, LoggerContext context, Level level) {
        String date = DateKit.toStr(new Date());
        RollingFileAppender appender = new RollingFileAppender();
        appender.setName(name);
        appender.setContext(context);
        //设置级别过滤器
        appender.addFilter(getLevelFilter(level));
        //设置文件名
        String fileName = logPath + date + "/" + name + "." + level.levelStr.toLowerCase() + ".log";
        appender.setFile(OptionHelper.substVars(fileName, context));
        appender.setAppend(true);
        appender.setPrudent(false);
        //设置文件创建时间及大小的类
        SizeAndTimeBasedRollingPolicy policy = new SizeAndTimeBasedRollingPolicy();
        policy.setContext(context);
        //文件名格式
        String fp = OptionHelper.substVars(logPath + "%d{yyyy-MM-dd}/" + name + "." + level.levelStr.toLowerCase() + ".%i.log", context);

        //最大日志文件大小
        policy.setMaxFileSize(FileSize.valueOf("1024KB"));
        //设置文件名模式
        policy.setFileNamePattern(fp);
        //设置最大历史记录为15条
        policy.setMaxHistory(15);
        //总大小限制
        policy.setTotalSizeCap(FileSize.valueOf("32GB"));
        //设置父节点是appender
        policy.setParent(appender);
        //设置上下文，每个logger都关联到logger上下文，默认上下文名称为default。
        // 但可以使用<contextName>设置成其他名字，用于区分不同应用程序的记录。一旦设置，不能修改。
        policy.start();
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        //%d %p (%file:%line\\)- %m%n
        if (name.equals("action")) {
            encoder.setPattern("%msg%n");
        } else {
            encoder.setPattern("%date{yyyy-MM-dd HH:mm:ss.SSS} %logger{40} (%file:%line\\) %n%msg%n");
        }
        encoder.start();
        //加入下面两个节点
        appender.setRollingPolicy(policy);
        appender.setEncoder(encoder);
        appender.start();
        return appender;
    }

    public RollingFileAppender fileAppender(String name, LoggerContext context) {
        String date = DateKit.toStr(new Date());
        RollingFileAppender appender = new RollingFileAppender();
        appender.setName(name);
        appender.setContext(context);
        //设置文件名
        String fileName = logPath + date + "/" + name + ".log";
        appender.setFile(OptionHelper.substVars(fileName, context));
        appender.setAppend(true);
        appender.setPrudent(false);
        //设置文件创建时间及大小的类
        SizeAndTimeBasedRollingPolicy policy = new SizeAndTimeBasedRollingPolicy();
        policy.setContext(context);
        //文件名格式
        String fp = OptionHelper.substVars(logPath + "%d{yyyy-MM-dd}/" + name + ".%i.log", context);

        //最大日志文件大小
        policy.setMaxFileSize(FileSize.valueOf("1024KB"));
        //设置文件名模式
        policy.setFileNamePattern(fp);
        //设置最大历史记录为15条
        policy.setMaxHistory(15);
        //总大小限制
        policy.setTotalSizeCap(FileSize.valueOf("32GB"));
        //设置父节点是appender
        policy.setParent(appender);
        //设置上下文，每个logger都关联到logger上下文，默认上下文名称为default。
        // 但可以使用<contextName>设置成其他名字，用于区分不同应用程序的记录。一旦设置，不能修改。
        policy.start();
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        //%d %p (%file:%line\\)- %m%n
        if (name.equals("action")) {
            encoder.setPattern("%msg%n");
        } else if (name.contains("sql")) {
            encoder.setPattern("%date{yyyy-MM-dd HH:mm:ss.SSS} %msg%n");
        } else {
            encoder.setPattern("%date{yyyy-MM-dd HH:mm:ss.SSS} %logger{40} %-5level (%file:%line\\) %n%msg%n");
        }
        encoder.start();
        //加入下面两个节点
        appender.setRollingPolicy(policy);
        appender.setEncoder(encoder);
        appender.start();
        return appender;
    }

    public ConsoleAppender consoleAppender(String name, LoggerContext context, Level level) {
        ConsoleAppender console = new ConsoleAppender();
        console.setContext(context);
        console.setName("CONSOLE");
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        if (name.equals("action")) {
            encoder.setPattern("%msg");
        } else {
            if (name.contains("sql")) {
                encoder.setPattern("%date{yyyy-MM-dd HH:mm:ss.SSS} %boldGreen(%msg%n)");
            } else {
                encoder.setPattern("%date{yyyy-MM-dd HH:mm:ss.SSS} %boldYellow([%thread]) %boldGreen(%logger{40}) %highlight(%-5level) (%file:%line\\) %n%msg%n");
            }
        }

        encoder.start();
        console.setEncoder(encoder);
        console.addFilter(getLevelFilter(level));
        console.start();
        return console;
    }

    public LevelFilter getLevelFilter(Level level) {
        LevelFilter levelFilter = new LevelFilter();
        levelFilter.setLevel(level);
        levelFilter.setOnMatch(FilterReply.ACCEPT);
        levelFilter.setOnMismatch(FilterReply.DENY);
        levelFilter.start();
        return levelFilter;
    }

    public static Integer getLevel(String name, boolean hasDef) {
        String str = PropKit.get(name);
        if ("error".equals(str) || "ERROR".equals(str)) {
            return 40000;
        } else if ("warn".equals(str) || "WARN".equals(str)) {
            return 30000;
        } else if ("info".equals(str) || "INFO".equals(str)) {
            return 20000;
        } else if ("debug".equals(str) || "DEBUG".equals(str)) {
            return 10000;
        } else {
            try {
                return Integer.parseInt(str) * 10000;
            } catch (Exception e) {
                return hasDef ? 0 : null;
            }

        }

    }

    private boolean isLevelFile() {
        String str = PropKit.get("log.level.file");
        if (StrKit.isBlank(str)) {
            return false;
        } else {
            boolean is = Boolean.parseBoolean(str);
            return is;
        }
    }

    public static void actionReporter() {
        Log log = Log.getLog("action");
        ActionReporter.setWriter(new Writer() {
            @Override
            public void write(String str) throws IOException {
                log.info(str);
            }

            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {

            }

            @Override
            public void flush() throws IOException {

            }

            @Override
            public void close() throws IOException {

            }
        });

    }

}
