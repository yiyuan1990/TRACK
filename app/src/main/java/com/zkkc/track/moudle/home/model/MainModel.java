package com.zkkc.track.moudle.home.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;


import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuyu.gsyvideoplayer.listener.GSYVideoShotListener;
import com.shuyu.gsyvideoplayer.listener.GSYVideoShotSaveListener;
import com.zgl.greentest.gen.HostDaoBeanDao;
import com.zgl.greentest.gen.PicGreenDaoBeanDao;
import com.zkkc.track.base.BaseModel;
import com.zkkc.track.common.GreenDaoManager;
import com.zkkc.track.entity.HostDaoBean;
import com.zkkc.track.entity.PicGreenDaoBean;
import com.zkkc.track.moudle.config.callback.ISwitchoverHost;
import com.zkkc.track.moudle.home.callback.IResult;
import com.zkkc.track.moudle.home.callback.ISwitchHost;
import com.zkkc.track.widget.EmptyControlVideo;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by ShiJunRan on 2019/1/23
 */
public class MainModel<T> extends BaseModel {
    public void photoGraph(Context context, final EmptyControlVideo detailPlayer, ExecutorService threadPool, final IResult iResult) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                detailPlayer.saveFrame(bitmapToFile(), true, new GSYVideoShotSaveListener() {
                    @Override
                    public void result(boolean success, File file) {
                        if (success) {
                            String fileName = FileUtils.getFileNameNoExtension(file);
                            PicGreenDaoBean bean = new PicGreenDaoBean();
                            bean.setName(fileName);
                            getPicDao().insert(bean);

                            List<PicGreenDaoBean> picGreenDaoBeans = getPicDao().loadAll();
                            for (PicGreenDaoBean bean2 : picGreenDaoBeans) {
                                LogUtils.vTag("SJR", bean2.getName());
                            }
                            iResult.Succeed();
                        } else {
                            iResult.Failure("抓拍失败");
                        }

                    }
                });
//                detailPlayer.taskShotPic(new GSYVideoShotListener() {
//                    @Override
//                    public void getBitmap(Bitmap bitmap) {
//
//                    }
//                }, true);
            }
        });
    }

    public void switchoverHost(ExecutorService threadPool, final ISwitchHost iSwitchHost) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                Query<HostDaoBean> build = getHostDao().queryBuilder()
                        .where(HostDaoBeanDao.Properties.MState.eq("Y"))
                        .build();
                List<HostDaoBean> list = build.list();
                if (list != null) {
                    if (list.size() > 0) {
                        iSwitchHost.switchHostOk(list.get(0));
                    } else {
                        iSwitchHost.switchHostErr();
                    }
                } else {
                    iSwitchHost.switchHostErr();
                }

            }
        });


    }

    private HostDaoBeanDao getHostDao() {
        return GreenDaoManager.getInstance().getSession().getHostDaoBeanDao();
    }

    private PicGreenDaoBeanDao getPicDao() {
        return GreenDaoManager.getInstance().getSession().getPicGreenDaoBeanDao();
    }

    private File bitmapToFile() {
        String nowDate = getNowDate();
        File filesDir = Environment.getExternalStorageDirectory();
        File appDir = new File(filesDir, "a_track");
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = nowDate + ".png";
        File file = new File(appDir, fileName);
        return file;
    }

    /**
     * 获取系统当前时间
     */
    private String getNowDate() {
        String format = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        return format;
    }

}
