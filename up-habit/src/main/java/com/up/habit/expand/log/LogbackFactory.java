package com.up.habit.expand.log;

import com.jfinal.log.ILogFactory;
import com.jfinal.log.Log;

/**
 * TODO:
 * <p>
 * @author 王剑洪 on 2019/10/22 17:11
 */
public class LogbackFactory implements ILogFactory {
    @Override
    public Log getLog(Class<?> clazz) {
        return new Logback(clazz);
    }

    @Override
    public Log getLog(String name) {
        return new Logback(name);
    }

}
