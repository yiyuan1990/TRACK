package com.zkkc.track.moudle.config.callback;

import com.zkkc.track.entity.HostDaoBean;

/**
 * Created by ShiJunRan on 2019/2/21
 */
public interface ISwitchoverHost {
    void switchoverHostOk(HostDaoBean hostDaoBean);

}
