package com.up.habit.expand.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.jfinal.log.Log;

import static org.slf4j.spi.LocationAwareLogger.*;

/**
 * TODO:
 * <p>
 * @author 王剑洪 on 2019/10/22 16:49
 */
public class Logback extends Log {

    private Logger log;
    private static final String FQCN = Logback.class.getName();

    public Logback(Class<?> clazz) {
        log = new LogbackBuilder().getLogger(clazz);
    }

    public Logback(String name) {
        log = new LogbackBuilder().getLogger(name);
    }

    public void info(String message) {
        log.log(null, FQCN, INFO_INT, message, null, null);
    }

    public void info(String message, Throwable t) {
        log.log(null, FQCN, INFO_INT, message, null, t);
    }

    public void debug(String message) {
        log.log(null, FQCN, DEBUG_INT, message, null, null);
    }

    public void debug(String message, Throwable t) {
        log.log(null, FQCN, DEBUG_INT, message, null, t);
    }

    public void warn(String message) {
        log.log(null, FQCN, WARN_INT, message, null, null);
    }

    public void warn(String message, Throwable t) {
        log.log(null, FQCN, WARN_INT, message, null, t);
    }

    public void error(String message) {
        log.log(null, FQCN, ERROR_INT, message, null, null);
    }

    public void error(String message, Throwable t) {
        log.log(null, FQCN, ERROR_INT, message, null, t);
    }

    public void fatal(String message) {
        log.log(null, FQCN, ERROR_INT, message, null, null);
    }

    public void fatal(String message, Throwable t) {
        log.log(null, FQCN, ERROR_INT, message, null, t);
    }

    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return log.isEnabledFor(Level.WARN);
    }

    public boolean isErrorEnabled() {
        return log.isEnabledFor(Level.ERROR);
    }

    public boolean isFatalEnabled() {
        return log.isEnabledFor(Level.ERROR);
    }
}
