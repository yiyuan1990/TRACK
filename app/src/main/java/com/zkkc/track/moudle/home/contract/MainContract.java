package com.zkkc.track.moudle.home.contract;

import android.content.Context;

import com.zkkc.track.base.BasePresenter;
import com.zkkc.track.base.BaseView;


/**
 * Created by ShiJunRan on 2019/1/23
 */
public interface MainContract {
    interface View extends BaseView {
//        void pictureShotSucceed();
//        void pictureShotErr(String err);
    }

    abstract class Presenter extends BasePresenter<View> {
//        public abstract void pictureShot(Context context, android.view.View mView);

    }
}
