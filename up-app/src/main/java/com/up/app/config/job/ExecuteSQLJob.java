package com.up.app.config.job;

import com.jfinal.plugin.activerecord.Db;
import com.up.habit.kit.ArrayKit;
import com.up.habit.kit.StrKit;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:执行sql定时任务
 *
 * @author 王剑洪 on 2020/1/9 18:58
 */
public class ExecuteSQLJob extends BaseJob {
    @Override
    public boolean execute() {
        String[] sqlArray = ArrayKit.toStrArray(getSql(), ";");
        if (sqlArray.length < 1) {
            message = "sql语句为空!";
            return false;
        }
        List<String> sqlList = new ArrayList<>();
        for (int i = 0; i < sqlArray.length; i++) {
            String sql = StrKit.removeSqlOther(sqlArray[i]);
            if (StrKit.notBlank(sql)) {
                sqlList.add(sql);
            }
        }
        Db.use().batch(sqlList, sqlArray.length);
        return true;
    }
}
