package com.up.habit.expand.quartz;

import com.jfinal.log.Log;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/9 14:28
 */
public abstract class HabitJob implements Job {

    protected Log log = Log.getLog(this.getClass());

    protected JobDataMap map = null;

    public static String KEY_SQL = "SQL";
    public static String KEY_PARA = "PARA";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        this.map = jobExecutionContext.getMergedJobDataMap();
        log.debug("Start " + this.getClass().getName() + " in " + System.currentTimeMillis());
        exe();
        log.debug("End " + this.getClass().getName() + " in " + System.currentTimeMillis());
    }

    /**
     * 执行任务内容
     */
    public abstract void exe();

    public String getSql() {
        if (map != null && map.containsKey(KEY_SQL)) {
            return (String) map.get(KEY_SQL);
        }
        return null;
    }

    public Map getPara() {
        if (map != null && map.containsKey(KEY_PARA)) {
            return (Map) map.get(KEY_PARA);
        }
        return null;
    }

}
