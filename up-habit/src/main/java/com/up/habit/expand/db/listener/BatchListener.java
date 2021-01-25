package com.up.habit.expand.db.listener;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/7 10:47
 */
public interface BatchListener {
    /**
     * 批量执行sql前
     *
     * @return
     */
    public boolean batchBefore();

    /**
     * 批量执行sql后
     *
     * @return
     */
    public boolean batchAfter();
}
