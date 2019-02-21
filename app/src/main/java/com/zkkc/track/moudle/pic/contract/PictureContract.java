package com.zkkc.track.moudle.pic.contract;


import com.zkkc.track.base.BasePresenter;
import com.zkkc.track.base.BaseView;
import com.zkkc.track.entity.PicGreenDaoBean;
import com.zkkc.track.moudle.pic.entity.PicBean;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by ShiJunRan on 2019/1/23
 */
public interface PictureContract {
    interface View extends BaseView {
        void getPicSucceed(List<File> files);

        void getPicFailure(String eStr);

        void deletePicOk();

        void queryPicSucceed(List<PicGreenDaoBean> picDaoBeans);

        void queryPicFailure();
    }

    abstract class Presenter extends BasePresenter<View> {
        //        public abstract void pictureShot(Context context, android.view.View mView);
        public abstract void getPicData(ExecutorService threadPool);

        public abstract void deletePic(ExecutorService threadPool, List<PicBean> removeList);

        public abstract void queryPic(ExecutorService threadPool, String picName);
    }
}
