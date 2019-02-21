package com.zkkc.track.moudle.config.contract;

import com.zkkc.track.base.BasePresenter;
import com.zkkc.track.base.BaseView;
import com.zkkc.track.entity.HostDaoBean;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by ShiJunRan on 2019/2/21
 */
public interface ConfigContract {
    interface View extends BaseView {
        void addSucceed(HostDaoBean hostDaoBean);

        void querySucceed(List<HostDaoBean> hostDaoBeans);

        void queryErr();

        void delOk();

        void switchoverHostSucceed(HostDaoBean hostDaoBean);

    }

    abstract class Presenter extends BasePresenter<ConfigContract.View> {
        public abstract void addHost(ExecutorService threadPool, String hName, String hostIp,
                                     String hostPort, String uName, String pW, String sxtIp, String sxtPort,String mState);

        public abstract void queryHost(ExecutorService threadPool);

        public abstract void delHost(ExecutorService threadPool,HostDaoBean bean);

        public abstract void switchoverHost(ExecutorService threadPool,HostDaoBean bean);
    }
}
