package com.zkkc.track.moudle.pic.contract;


import com.zkkc.track.base.BasePresenter;
import com.zkkc.track.base.BaseView;

/**
 * Created by ShiJunRan on 2019/1/23
 */
public interface PictureContract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<View> {
//        public abstract void pictureShot(Context context, android.view.View mView);

    }
}
