package com.zkkc.track.moudle.home.contract;

import android.content.Context;

import com.zkkc.track.base.BasePresenter;
import com.zkkc.track.base.BaseView;
import com.zkkc.track.entity.HostDaoBean;
import com.zkkc.track.widget.EmptyControlVideo;

import java.util.concurrent.ExecutorService;


/**
 * Created by ShiJunRan on 2019/1/23
 */
public interface MainContract {
    interface View extends BaseView {
        void photoGraphSucceed();
        void photoGraphErr(String err);
        void switchoverHostOk(HostDaoBean bean);
        void switchoverHostErr();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void photoGraph(Context context, EmptyControlVideo detailPlayer, ExecutorService threadPool);//抓拍

        public abstract void switchoverHost(ExecutorService threadPool);

    }
}
