package com.zkkc.track.moudle.pic.callback;

import com.zkkc.track.entity.PicGreenDaoBean;

import java.util.List;

/**
 * Created by ShiJunRan on 2019/2/20
 */
public interface IQueryPic {
    void queryOk(List<PicGreenDaoBean> picDaoBeans);

    void queryErr();
}
