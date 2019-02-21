package com.zkkc.track.moudle.pic.presenter;

import android.content.Context;
import android.graphics.Color;

import com.cazaea.sweetalert.ProgressHelper;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.zkkc.track.R;
import com.zkkc.track.entity.PicGreenDaoBean;
import com.zkkc.track.moudle.pic.callback.IDeletePic;
import com.zkkc.track.moudle.pic.callback.IPicResult;
import com.zkkc.track.moudle.pic.callback.IQueryPic;
import com.zkkc.track.moudle.pic.contract.PictureContract;
import com.zkkc.track.moudle.pic.entity.PicBean;
import com.zkkc.track.moudle.pic.model.PictureModel;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;


/**
 * Created by ShiJunRan on 2019/1/23
 */
public class PicturePresenter extends PictureContract.Presenter {
    private Context mContext;
    private PictureModel model;

    public PicturePresenter(Context mContext) {
        this.mContext = mContext;
        this.model = new PictureModel();
    }

    @Override
    public void getPicData(ExecutorService threadPool) {
        showLoadDialog("正在获取图片...");
        model.getPicData(threadPool, new IPicResult() {
            @Override
            public void Succeed(List<File> files) {
                hideLoadDialog();
                getView().getPicSucceed(files);
            }

            @Override
            public void Failure(String eStr) {
                hideLoadDialog();
                getView().getPicFailure(eStr);
            }
        });
    }

    @Override
    public void deletePic(ExecutorService threadPool, List<PicBean> removeList) {
        showLoadDialog("删除中...");
        model.deletePic(threadPool, removeList, new IDeletePic() {
            @Override
            public void DeleteOk() {
                hideLoadDialog();
                getView().deletePicOk();
            }
        });

    }

    @Override
    public void queryPic(ExecutorService threadPool, String picName) {
        model.queryPic(threadPool, picName, new IQueryPic() {
            @Override
            public void queryOk(List<PicGreenDaoBean> picDaoBeans) {
                getView().queryPicSucceed(picDaoBeans);
            }

            @Override
            public void queryErr() {
                getView().queryPicFailure();
            }
        });
    }

    SweetAlertDialog pDialog;

    private void showLoadDialog(String str) {
        if (pDialog == null) {
            pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.yellow));
            pDialog.setTitleText(str);
            pDialog.setCancelable(true);
            pDialog.show();
        } else {
            pDialog.dismiss();
            pDialog = null;
            pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.yellow));
            pDialog.setTitleText(str);
            pDialog.setCancelable(true);
            pDialog.show();
        }

    }

    private void hideLoadDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }

    }
}
