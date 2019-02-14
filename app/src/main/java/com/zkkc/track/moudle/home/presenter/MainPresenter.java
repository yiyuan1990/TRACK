package com.zkkc.track.moudle.home.presenter;

import android.content.Context;
import android.view.View;

import com.luoxudong.app.threadpool.ThreadPoolHelp;
import com.zkkc.track.moudle.home.contract.MainContract;
import com.zkkc.track.moudle.home.model.MainModel;

import java.util.concurrent.ExecutorService;

/**
 * Created by ShiJunRan on 2019/1/23
 */
public class MainPresenter extends MainContract.Presenter {

    private Context mContext;
    private MainModel model;
//    private ExecutorService etService;
    public MainPresenter(Context context) {
        this.model = new MainModel();
        this.mContext = context;
//        etService = ThreadPoolHelp.Builder
//                .cached()
//                .builder();
    }


//    @Override
//    public void pictureShot(Context context, android.view.View mView) {
//        model.pictureShot(mContext, mView,etService, new IResult() {
//            @Override
//            public void Succeed() {
//                getView().pictureShotSucceed();
//            }
//
//            @Override
//            public void Failure(String eStr) {
//                getView().pictureShotErr(eStr);
//            }
//        });
//
//    }
}
