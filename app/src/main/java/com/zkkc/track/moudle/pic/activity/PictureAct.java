package com.zkkc.track.moudle.pic.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.zkkc.track.R;
import com.zkkc.track.base.BaseActivity;
import com.zkkc.track.moudle.pic.contract.PictureContract;
import com.zkkc.track.moudle.pic.presenter.PicturePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ShiJunRan on 2019/1/23
 * 图片查看页面
 */
public class PictureAct extends BaseActivity<PictureContract.View, PictureContract.Presenter> implements PictureContract.View {

    @BindView(R.id.pvShow)
    PhotoView pvShow;
    @BindView(R.id.llBack)
    LinearLayout llBack;
    @BindView(R.id.tvMemory)
    TextView tvMemory;
    @BindView(R.id.rvPicture)
    RecyclerView rvPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        fullscreen(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.picture_act;
    }

    @Override
    public PictureContract.Presenter createPresenter() {
        return new PicturePresenter(this) ;
    }


    @Override
    public PictureContract.View createView() {
        return this;
    }

    @Override
    public void init() {
//        pvShow.setImageResource(R.mipmap.ic_photo);

    }


    @OnClick(R.id.llBack)
    public void onViewClicked() {
        finish();
    }
}
