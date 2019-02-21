package com.zgl.greentest.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.zkkc.track.entity.PicGreenDaoBean;
import com.zkkc.track.entity.HostDaoBean;

import com.zgl.greentest.gen.PicGreenDaoBeanDao;
import com.zgl.greentest.gen.HostDaoBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig picGreenDaoBeanDaoConfig;
    private final DaoConfig hostDaoBeanDaoConfig;

    private final PicGreenDaoBeanDao picGreenDaoBeanDao;
    private final HostDaoBeanDao hostDaoBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        picGreenDaoBeanDaoConfig = daoConfigMap.get(PicGreenDaoBeanDao.class).clone();
        picGreenDaoBeanDaoConfig.initIdentityScope(type);

        hostDaoBeanDaoConfig = daoConfigMap.get(HostDaoBeanDao.class).clone();
        hostDaoBeanDaoConfig.initIdentityScope(type);

        picGreenDaoBeanDao = new PicGreenDaoBeanDao(picGreenDaoBeanDaoConfig, this);
        hostDaoBeanDao = new HostDaoBeanDao(hostDaoBeanDaoConfig, this);

        registerDao(PicGreenDaoBean.class, picGreenDaoBeanDao);
        registerDao(HostDaoBean.class, hostDaoBeanDao);
    }
    
    public void clear() {
        picGreenDaoBeanDaoConfig.clearIdentityScope();
        hostDaoBeanDaoConfig.clearIdentityScope();
    }

    public PicGreenDaoBeanDao getPicGreenDaoBeanDao() {
        return picGreenDaoBeanDao;
    }

    public HostDaoBeanDao getHostDaoBeanDao() {
        return hostDaoBeanDao;
    }

}
