package com.zkkc.track.moudle.config.callback;

import com.zkkc.track.entity.HostDaoBean;

import java.util.List;

/**
 * Created by ShiJunRan on 2019/2/21
 */
public interface IQueryHost {
    void queryOk(List<HostDaoBean> hostDaoBeans);
    void queryErr();
}
