package com.zkkc.track.utils;


/**
 * Time Counter.
 * 计时器（可获取用户使用app的时间）
 *
 */
public class TimeCounter {

    private static final String TAG = TimeCounter.class.getSimpleName();
    private long t;

    public TimeCounter() {
        start();
    }

    /**
     * Count start.
     */
    public long start() {
        t = System.currentTimeMillis();
        return t;
    }

    /**
     * Get duration and restart.
     */
    public long durationRestart() {
        long now = System.currentTimeMillis();
        long d = now - t;
        t = now;
        return d;
    }

    /**
     * Get duration.
     */
    public long duration() {
        return System.currentTimeMillis() - t;
    }
}