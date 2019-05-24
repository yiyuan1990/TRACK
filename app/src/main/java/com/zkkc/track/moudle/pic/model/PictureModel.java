package com.zkkc.track.moudle.pic.model;


import android.os.Environment;

import com.blankj.utilcode.util.FileUtils;
import com.zgl.greentest.gen.PicGreenDaoBeanDao;
import com.zkkc.track.base.BaseModel;
import com.zkkc.track.common.GreenDaoManager;
import com.zkkc.track.entity.PicGreenDaoBean;
import com.zkkc.track.moudle.pic.callback.IDeletePic;
import com.zkkc.track.moudle.pic.callback.IPicResult;
import com.zkkc.track.moudle.pic.callback.IQueryPic;
import com.zkkc.track.moudle.pic.entity.PicBean;

import org.greenrobot.greendao.query.Query;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by ShiJunRan on 2019/1/23
 */
public class PictureModel<T> extends BaseModel {
    public void getPicData(ExecutorService threadPool, final IPicResult iPicResult) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                File filesDir = Environment.getExternalStorageDirectory();
                File appDir = new File(filesDir, "cip_track");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                List<File> files = FileUtils.listFilesInDir(appDir);
                if (files != null) {
                    iPicResult.Succeed(files);
                } else {
                    iPicResult.Failure("获取抓拍图片失败！");
                }


            }
        });

    }

    private PicGreenDaoBean picDao;

    public void deletePic(ExecutorService threadPool, final List<PicBean> removeList, final IDeletePic iDeletePic) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (PicBean bean : removeList) {
                    boolean delete = FileUtils.delete(bean.getmFile());//删除图片文件
                    if (delete) {
                        greenDaoDel(bean.getName());
                    }
                }
                iDeletePic.DeleteOk();

            }
        });


    }

    public void queryPic(ExecutorService threadPool, final String picName, final IQueryPic iQueryPic) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<PicGreenDaoBean> picDaoBeans = queryDao(picName);
                if (picDaoBeans!=null){
                    iQueryPic.queryOk(picDaoBeans);
                }else {
                    iQueryPic.queryErr();
                }

            }
        });

    }

    private List<PicGreenDaoBean> queryDao(String picName) {
        Query<PicGreenDaoBean> picDaos = getPicDao().queryBuilder()
                .where(PicGreenDaoBeanDao.Properties.Name.ge(picName))
                .build();
        return picDaos.list();
    }

    /**
     * 删除数据库内的数据
     *
     * @param fileName
     */
    private void greenDaoDel(String fileName) {
        Query<PicGreenDaoBean> picDaos = getPicDao().queryBuilder()
                .where(PicGreenDaoBeanDao.Properties.Name.eq(fileName))
                .build();
        List<PicGreenDaoBean> list = picDaos.list();
        for (PicGreenDaoBean daoBean : list) {
            getPicDao().delete(daoBean);
        }

    }

    private PicGreenDaoBeanDao getPicDao() {
        return GreenDaoManager.getInstance().getSession().getPicGreenDaoBeanDao();
    }
}
