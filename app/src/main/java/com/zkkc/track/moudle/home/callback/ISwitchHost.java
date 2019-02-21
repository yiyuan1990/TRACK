package com.zkkc.track.moudle.home.callback;

import com.zkkc.track.entity.HostDaoBean;

/**
 * Created by ShiJunRan on 2019/2/21
 */
public interface ISwitchHost {
    void switchHostOk(HostDaoBean bean);

    void switchHostErr();
}
