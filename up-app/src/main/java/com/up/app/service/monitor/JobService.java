package com.up.app.service.monitor;

import com.jfinal.kit.Kv;
import com.up.app.config.job.BaseJob;
import com.up.app.model.cm.Job;
import com.up.habit.controller.render.To;
import com.up.habit.expand.quartz.HabitQuartzManager;
import com.up.habit.kit.ClassKit;
import com.up.habit.kit.StrKit;
import com.up.habit.service.HabitService;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.util.Date;
import java.util.List;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/9 16:44
 */
public class JobService extends HabitService<Job> {

    public static JobService me = new JobService();

    public To add(Job job) {
        if (StrKit.isBlank(job.getName())) {
            return To.fail("任务名称不能为空!");
        }
        if (StrKit.isBlank(job.getGroup())) {
            job.setGroup("DEFAULT");
        }
        Job old = dao.template("queryByNameAndGroup", job.getName(), job.getGroup()).findFirst();
        if (old != null) {
            return To.fail("任务组对应的任务已存在!");
        }
        if (!isCorn(job.getCorn())) {
            return To.fail("时间表达式不正确!");
        }
        if (!isJobClass(job.getClazz())) {
            return To.fail("执行类不正确!");
        }
        job.setState(0);
        return To.ok(job.save());
    }

    public To delete(Integer[] ids) {
        int count = dao.template("queryRunCountByIds", Kv.by("ids", ids)).queryInt();
        if (count > 0) {
            return To.fail("请先停止任务!");
        }
        return super.delete(ids);
    }

    public To edit(Job job) {
        if (job.getId() == null) {
            return To.fail("任务不存在!");
        }
        Job old = dao.findById(job.getId());
        if (old == null) {
            return To.fail("任务不存在!");
        }
        old.setName(StrKit.isBlank(job.getName()) ? old.getName() : job.getName());
        old.setGroup(StrKit.isBlank(job.getGroup()) ? old.getGroup() : job.getGroup());
        old.setSql(StrKit.isBlank(job.getSql()) ? old.getSql() : job.getSql());
        if (StrKit.notBlank(job.getName(), job.getGroup())) {
            Job tmp = dao.template("queryByNameAndGroup", old.getName(), old.getGroup()).findFirst();
            if (tmp != null && tmp.getId().intValue() != old.getId().intValue()) {
                return To.fail("任务组对应的任务已存在!");
            }
        }
        old.setCorn(isCorn(job.getCorn()) ? job.getCorn() : old.getCorn());
        old.setClazz(isJobClass(job.getClazz()) ? job.getClazz() : old.getClazz());
        return To.ok(old.update());
    }

    public To startOrStop(int id) {
        Job job = dao.findById(id);
        if (job == null) {
            return To.fail("任务不存在!");
        }
        boolean res = false;
        int state = job.getState().intValue();
        if (job.getState().intValue() == 0) {
            res = HabitQuartzManager.me().add(job.getName(), job.getGroup(), ClassKit.loadClass(job.getClazz()), job.getCorn(), Kv.by(BaseJob.KEY_SQL, job.getSql()));
            state = 1;
        } else {
            res = HabitQuartzManager.me().remove(job.getName(), job.getGroup());
            state = 0;
        }
        if (res) {
            return To.to(job.setState(state).update());
        }
        return To.fail(job.getState().intValue() == 0 ? "启动失败!" : "停止失败!");
    }


    public void start() {
        List<Job> jobs = dao.template("queryRunJob").find();
        for (Job job : jobs) {
            HabitQuartzManager.me().add(job.getName(), job.getGroup(), ClassKit.loadClass(job.getClazz()), job.getCorn(), Kv.by(BaseJob.KEY_SQL, job.getSql()));
        }
    }

    public void refreshLastTime(String name, String group) {
        dao.template("refreshLastTime", name, group).update();
    }


    public boolean isCorn(String corn) {
        if (StrKit.isBlank(corn)) {
            return false;
        }
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            trigger.setCronExpression(corn);
            Date date = trigger.computeFirstFireTime(null);
            return date != null;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isJobClass(String className) {
        if (StrKit.isBlank(className)) {
            return false;
        }
        return ClassKit.loadClass(className) == null ? false : true;
    }

    public static void main(String[] args) {
        System.out.println(JobService.me.isCorn("0 0 0 1 0 * *"));
    }
}
