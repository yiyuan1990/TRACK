package com.zkkc.track.moudle.config.model;

import com.zgl.greentest.gen.HostDaoBeanDao;
import com.zgl.greentest.gen.PicGreenDaoBeanDao;
import com.zkkc.track.base.BaseModel;
import com.zkkc.track.common.GreenDaoManager;
import com.zkkc.track.entity.HostDaoBean;
import com.zkkc.track.moudle.config.callback.IAddHost;
import com.zkkc.track.moudle.config.callback.IDelHost;
import com.zkkc.track.moudle.config.callback.IQueryHost;
import com.zkkc.track.moudle.config.callback.ISwitchoverHost;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by ShiJunRan on 2019/2/21
 */
public class ConfigModel<T> extends BaseModel {


    public void addHost(ExecutorService threadPool, final String hName, final String hostIp,
                        final String hostPort, final String uName, final String pW, final String sxtIp, final String sxtPort, final String mState, final IAddHost iAddHost) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                HostDaoBean hostDaoBean = new HostDaoBean();
                hostDaoBean.setName(hName);
                hostDaoBean.setHIp(hostIp);
                hostDaoBean.setHPort(hostPort);
                if (uName.equals("")) {
                    hostDaoBean.setHName("admin");
                } else {
                    hostDaoBean.setHName(uName);
                }
                hostDaoBean.setHPw(pW);
                hostDaoBean.setSIp(sxtIp);
                hostDaoBean.setSPort(sxtPort);
                hostDaoBean.setMState(mState);
                getHostDao().insert(hostDaoBean);
                iAddHost.addOk(hostDaoBean);
            }
        });

    }

    public void queryHost(ExecutorService threadPool, final IQueryHost iQueryHost) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<HostDaoBean> hostDaoBeans = getHostDao().loadAll();
                if (hostDaoBeans != null) {
                    iQueryHost.queryOk(hostDaoBeans);
                } else {
                    iQueryHost.queryErr();
                }
            }
        });

    }

    public void delHost(ExecutorService threadPool, final HostDaoBean bean, final IDelHost iDelHost) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                getHostDao().delete(bean);
                iDelHost.delOk();
            }
        });

    }

    public void switchoverHost(ExecutorService threadPool, final HostDaoBean bean, final ISwitchoverHost iSwitchoverHost) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {

                List<HostDaoBean> hostDaoBeans = getHostDao().loadAll();
                for (HostDaoBean b : hostDaoBeans) {
                    if ("Y".equals(b.getMState())) {
                        b.setMState("N");
                        getHostDao().update(b);
                    }
                }

                Query<HostDaoBean> hostDaoBeanQuerys = getHostDao().queryBuilder()
                        .where(HostDaoBeanDao.Properties.Id.eq(bean.getId()))
                        .build();
                List<HostDaoBean> list = hostDaoBeanQuerys.list();
                if (list.size() > 0) {
                    HostDaoBean hostDaoBean = list.get(0);
                    hostDaoBean.setMState("Y");
                    getHostDao().update(hostDaoBean);
                    iSwitchoverHost.switchoverHostOk(hostDaoBean);
                }

//                for (HostDaoBean b : hostDaoBeans) {
//                    if (bean == b) {
//                        b.setMState("Y");
//                        getHostDao().update(b);
//                    }
//                }


            }
        });

    }

    private HostDaoBeanDao getHostDao() {
        return GreenDaoManager.getInstance().getSession().getHostDaoBeanDao();
    }
}
