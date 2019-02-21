package com.zkkc.track.common;

import com.zgl.greentest.gen.DaoMaster;
import com.zgl.greentest.gen.DaoSession;
import com.zkkc.track.TrackApp;

/**
 * Created by ShiJunRan on 2019/2/12
 * GreenDao工具类
 */
public class GreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static GreenDaoManager mInstance; //单例

    private GreenDaoManager(){
        if (mInstance == null) {
            //此处为自己需要处理的表
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(TrackApp.getContext(), "user1-db", null);
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {//保证异步处理安全操作

                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }
    public DaoSession getSession() {
        return mDaoSession;
    }
    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
