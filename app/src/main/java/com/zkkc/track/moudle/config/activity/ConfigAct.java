package com.zkkc.track.moudle.config.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luoxudong.app.threadpool.ThreadPoolHelp;
import com.zkkc.track.R;
import com.zkkc.track.base.BaseActivity;
import com.zkkc.track.entity.HostDaoBean;
import com.zkkc.track.moudle.config.adapter.AdHostShow;
import com.zkkc.track.moudle.config.contract.ConfigContract;
import com.zkkc.track.moudle.config.presenter.ConfigPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by ShiJunRan on 2019/2/20
 */
public class ConfigAct extends BaseActivity<ConfigContract.View, ConfigContract.Presenter> implements ConfigContract.View {
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etOne)
    EditText etOne;
    @BindView(R.id.etTwo)
    EditText etTwo;
    @BindView(R.id.etThree)
    EditText etThree;
    @BindView(R.id.etFour)
    EditText etFour;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.rvHost)
    RecyclerView rvHost;


    List<HostDaoBean> mList = new ArrayList<HostDaoBean>();
    private ExecutorService threadPool;
    AdHostShow adHostShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.config_act;
    }

    @Override
    public ConfigContract.Presenter createPresenter() {
        return new ConfigPresenter(this);
    }

    @Override
    public ConfigContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        threadPool = ThreadPoolHelp.Builder
                .cached()
                .builder();
        rvHost.setLayoutManager(new LinearLayoutManager(this));
        adHostShow = new AdHostShow(R.layout.item_host_rv, mList);
        rvHost.setAdapter(adHostShow);
        getPresenter().queryHost(threadPool);
        adHostShow.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                showOkDialog(mList.get(position));
                return false;
            }
        });
        adHostShow.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                getPresenter().switchoverHost(threadPool, mList.get(position));
            }
        });
    }

    @OnClick(R.id.btnAdd)
    public void onViewClicked() {
        String hName = etName.getText().toString().trim();
        String hIp = etOne.getText().toString().trim();
        String uName = etTwo.getText().toString().trim();
        String pW = etThree.getText().toString().trim();
        String sIp = etFour.getText().toString().trim();
        boolean isOk = isOk(hIp, sIp);
        if (isOk) {
            String[] split = hIp.split(":");
            String[] split2 = sIp.split(":");
            String hostIp = split[0];
            String hostPort = split[1];
            String sxtIp = split2[0];
            String sxtPort = split2[1];
            String mState = "N";
            getPresenter().addHost(threadPool, hName, hostIp, hostPort, uName, pW, sxtIp, sxtPort, mState);

        }

    }

    private boolean isOk(String hIp, String sIp) {
        if (hIp.equals("") || sIp.equals("")) {
            ToastUtils.showShort("IP或端口号不能为空");
            return false;
        } else {
            String[] split = hIp.split(":");
            String[] split2 = sIp.split(":");
            if (RegexUtils.isIP(split[0]) && RegexUtils.isIP(split2[0])) {
                return true;
            } else {
                ToastUtils.showShort("主机或摄像头IP不符合规范");
                return false;
            }
        }
    }

    @Override
    public void addSucceed(HostDaoBean hostDaoBean) {
        getPresenter().queryHost(threadPool);
    }

    @Override
    public void querySucceed(final List<HostDaoBean> hostDaoBeans) {
        mList = hostDaoBeans;
        Collections.reverse(mList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adHostShow.setNewData(mList);
            }
        });
    }

    @Override
    public void queryErr() {
        ToastUtils.showShort("查询主机错误");
    }

    @Override
    public void delOk() {
        getPresenter().queryHost(threadPool);
    }

    @Override
    public void switchoverHostSucceed(HostDaoBean hostDaoBean) {
        finish();
    }

    SweetAlertDialog dialog;

    private void showOkDialog(final HostDaoBean bean) {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText("确定删除主机")
                .setConfirmText("确定") // 如果不设置确定按钮属性，确定按钮将不显示，便于弹出后自动消失
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        getPresenter().delHost(threadPool, bean);
                        dismisDialog();
                    }
                })
                .show();
    }

    private void dismisDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
