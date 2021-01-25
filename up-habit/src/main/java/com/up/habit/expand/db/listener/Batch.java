package com.up.habit.expand.db.listener;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/7 10:48
 */
public class Batch implements BatchListener {
    @Override
    public boolean batchBefore() {
        return true;
    }

    @Override
    public boolean batchAfter() {
        return true;
    }
}
