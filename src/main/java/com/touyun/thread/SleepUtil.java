package com.touyun.thread;

/**
 * Created by wenfeng on 2018/4/12.
 */
public class SleepUtil {
    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
