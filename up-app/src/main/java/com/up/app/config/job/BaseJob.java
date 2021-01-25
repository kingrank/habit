package com.up.app.config.job;

import com.google.gson.Gson;
import com.jfinal.log.Log;
import com.up.app.model.cm.JobLog;
import com.up.app.service.monitor.JobService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;

import java.util.Date;
import java.util.Map;

/**
 * TODO:定时任务基础类
 *
 * @author 王剑洪 on 2020/1/9 18:43
 */
public abstract class BaseJob implements Job {

    protected Log log = Log.getLog(this.getClass());

    protected JobExecutionContext jobExecutionContext = null;
    protected JobDataMap map = null;

    protected String message = "执行成功!";

    public static String KEY_SQL = "SQL";
    public static String KEY_PARA = "PARA";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        this.jobExecutionContext = jobExecutionContext;
        this.map = jobExecutionContext.getMergedJobDataMap();

        boolean res = true;
        Exception exception = null;
        long startTime = System.currentTimeMillis();
        try {
            res = execute();
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
            res = false;
        }
        long endTime = System.currentTimeMillis();
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        JobService.me.refreshLastTime(key.getName(), key.getGroup());
        new JobLog()
                .setGroup(key.getGroup())
                .setName(key.getName())
                .setStartTime(new Date(startTime))
                .setEndTime(new Date(endTime))
                .setRes(res ? 0 : -1)
                .setException(new Gson().toJson(exception))
                .setMessage(message)
                .save();


    }

    /**
     * 执行
     *
     * @return
     */
    public abstract boolean execute();

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
