package com.zkkc.track;

import android.app.Application;
import android.content.Context;

import com.zkkc.track.common.GreenDaoManager;

/**
 * Created by ShiJunRan on 2019/2/14
 * Application类
 */
public class TrackApp extends Application {
    private static Context mContext;//全局上下文对象

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //greenDao全局配置,只希望有一个数据库操作对象
        GreenDaoManager.getInstance();
    }
    public static Context getContext() {
        return mContext;
    }
}
