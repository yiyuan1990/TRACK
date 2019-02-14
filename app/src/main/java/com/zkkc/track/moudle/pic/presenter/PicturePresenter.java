package com.zkkc.track.moudle.pic.presenter;

import android.content.Context;

import com.zkkc.track.moudle.pic.contract.PictureContract;
import com.zkkc.track.moudle.pic.model.PictureModel;


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

}
