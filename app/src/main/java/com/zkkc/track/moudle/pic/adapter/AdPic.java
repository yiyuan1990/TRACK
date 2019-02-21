package com.zkkc.track.moudle.pic.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uniquestudio.library.CircleCheckBox;
import com.zkkc.track.R;
import com.zkkc.track.moudle.pic.entity.PicBean;

import java.util.List;

/**
 * Created by ShiJunRan on 2019/2/19
 * 图片recycler适配器
 */
public class AdPic extends BaseQuickAdapter<PicBean, BaseViewHolder> {

    public AdPic(int layoutResId, @Nullable List<PicBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final PicBean item) {
        helper.setText(R.id.tvShowName, item.getName());
        CircleCheckBox ccb = helper.getView(R.id.ccb);
        if (item.isShow()) {
            ccb.setVisibility(View.VISIBLE);
        } else {
            ccb.setVisibility(View.GONE);
        }

        if (item.isEd()) {
            ccb.setChecked(true);
        } else {
            ccb.setChecked(false);
        }
        ccb.setListener(new CircleCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                item.setEd(isChecked);
            }
        });

    }
}
