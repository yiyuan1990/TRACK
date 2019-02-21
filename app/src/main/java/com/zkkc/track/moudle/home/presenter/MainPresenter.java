package com.zkkc.track.moudle.home.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.cazaea.sweetalert.ProgressHelper;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.luoxudong.app.threadpool.ThreadPoolHelp;
import com.shuyu.gsyvideoplayer.listener.GSYVideoShotListener;
import com.zkkc.track.R;
import com.zkkc.track.entity.HostDaoBean;
import com.zkkc.track.moudle.home.callback.IResult;
import com.zkkc.track.moudle.home.callback.ISwitchHost;
import com.zkkc.track.moudle.home.contract.MainContract;
import com.zkkc.track.moudle.home.model.MainModel;
import com.zkkc.track.widget.EmptyControlVideo;

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
    }

    @Override
    public void photoGraph(Context context, final EmptyControlVideo detailPlayer, ExecutorService threadPool) {
        showLoadDialog();
        model.photoGraph(context, detailPlayer, threadPool, new IResult() {
            @Override
            public void Succeed() {
                getView().photoGraphSucceed();
                hideLoadDialog();
            }

            @Override
            public void Failure(String eStr) {
                hideLoadDialog();
                getView().photoGraphErr(eStr);
            }
        });


    }

    @Override
    public void switchoverHost(ExecutorService threadPool) {
        model.switchoverHost(threadPool, new ISwitchHost() {
            @Override
            public void switchHostOk(HostDaoBean bean) {
                getView().switchoverHostOk(bean);
            }

            @Override
            public void switchHostErr() {
                getView().switchoverHostErr();
            }
        });

    }

    SweetAlertDialog pDialog;

    private void showLoadDialog() {
        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.yellow));
        pDialog.setTitleText("努力抓拍中...");
        pDialog.setCancelable(true);
        pDialog.show();
    }

    private void hideLoadDialog() {
        pDialog.dismiss();
    }
}
