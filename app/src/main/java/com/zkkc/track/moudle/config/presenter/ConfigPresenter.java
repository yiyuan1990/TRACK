package com.zkkc.track.moudle.config.presenter;

import android.content.Context;

import com.zkkc.track.entity.HostDaoBean;
import com.zkkc.track.moudle.config.callback.IAddHost;
import com.zkkc.track.moudle.config.callback.IDelHost;
import com.zkkc.track.moudle.config.callback.IQueryHost;
import com.zkkc.track.moudle.config.callback.ISwitchoverHost;
import com.zkkc.track.moudle.config.contract.ConfigContract;
import com.zkkc.track.moudle.config.model.ConfigModel;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by ShiJunRan on 2019/2/21
 */
public class ConfigPresenter extends ConfigContract.Presenter {
    private Context mContext;
    private ConfigModel model;

    public ConfigPresenter(Context mContext) {
        this.mContext = mContext;
        this.model = new ConfigModel();
    }


    @Override
    public void addHost(ExecutorService threadPool, String hName, String hostIp,
                        String hostPort, String uName, String pW, String sxtIp, String sxtPort,String mState) {

        model.addHost(threadPool, hName, hostIp, hostPort, uName, pW, sxtIp, sxtPort,mState, new IAddHost() {
            @Override
            public void addOk(HostDaoBean hostDaoBean) {
                getView().addSucceed(hostDaoBean);
            }
        });

    }

    @Override
    public void queryHost(ExecutorService threadPool) {
        model.queryHost(threadPool, new IQueryHost() {
            @Override
            public void queryOk(List<HostDaoBean> hostDaoBeans) {
                getView().querySucceed(hostDaoBeans);
            }

            @Override
            public void queryErr() {
                getView().queryErr();
            }
        });
    }

    @Override
    public void delHost(ExecutorService threadPool, HostDaoBean bean) {
        model.delHost(threadPool, bean, new IDelHost() {
            @Override
            public void delOk() {
                getView().delOk();
            }
        });

    }

    @Override
    public void switchoverHost(ExecutorService threadPool, HostDaoBean bean) {
        model.switchoverHost(threadPool, bean, new ISwitchoverHost() {
            @Override
            public void switchoverHostOk(HostDaoBean hostDaoBean) {
                getView().switchoverHostSucceed(hostDaoBean);
            }
        });

    }
}
