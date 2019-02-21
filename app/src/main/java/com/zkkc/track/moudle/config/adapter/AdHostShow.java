package com.zkkc.track.moudle.config.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkkc.track.R;
import com.zkkc.track.entity.HostDaoBean;

import java.util.List;

/**
 * Created by ShiJunRan on 2019/2/21
 */
public class AdHostShow extends BaseQuickAdapter<HostDaoBean, BaseViewHolder> {
    public AdHostShow(int layoutResId, @Nullable List<HostDaoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HostDaoBean item) {
        helper.setText(R.id.tvHostName, item.getName());
        helper.setText(R.id.tvIp, item.getHIp());
        helper.setText(R.id.tvPort, item.getHPort());
        helper.setText(R.id.tvSXTIp, item.getSIp());
        helper.setText(R.id.tvSXTPort, item.getSPort());
        Button btnConnectIn = helper.getView(R.id.btnConnectIn);
        if (item.getMState().equals("Y")) {
            btnConnectIn.setText("已配置");
            btnConnectIn.setTextColor(Color.parseColor("#D81B60"));
        } else {
            btnConnectIn.setTextColor(Color.parseColor("#FFCC33"));
            btnConnectIn.setText("接入");
        }
        helper.addOnClickListener(R.id.btnConnectIn);

    }
}
