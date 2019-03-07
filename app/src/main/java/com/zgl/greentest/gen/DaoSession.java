package com.zgl.greentest.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.zkkc.track.entity.HostDaoBean;
import com.zkkc.track.entity.PicGreenDaoBean;

import com.zgl.greentest.gen.HostDaoBeanDao;
import com.zgl.greentest.gen.PicGreenDaoBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig hostDaoBeanDaoConfig;
    private final DaoConfig picGreenDaoBeanDaoConfig;

    private final HostDaoBeanDao hostDaoBeanDao;
    private final PicGreenDaoBeanDao picGreenDaoBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        hostDaoBeanDaoConfig = daoConfigMap.get(HostDaoBeanDao.class).clone();
        hostDaoBeanDaoConfig.initIdentityScope(type);

        picGreenDaoBeanDaoConfig = daoConfigMap.get(PicGreenDaoBeanDao.class).clone();
        picGreenDaoBeanDaoConfig.initIdentityScope(type);

        hostDaoBeanDao = new HostDaoBeanDao(hostDaoBeanDaoConfig, this);
        picGreenDaoBeanDao = new PicGreenDaoBeanDao(picGreenDaoBeanDaoConfig, this);

        registerDao(HostDaoBean.class, hostDaoBeanDao);
        registerDao(PicGreenDaoBean.class, picGreenDaoBeanDao);
    }
    
    public void clear() {
        hostDaoBeanDaoConfig.clearIdentityScope();
        picGreenDaoBeanDaoConfig.clearIdentityScope();
    }

    public HostDaoBeanDao getHostDaoBeanDao() {
        return hostDaoBeanDao;
    }

    public PicGreenDaoBeanDao getPicGreenDaoBeanDao() {
        return picGreenDaoBeanDao;
    }

}
