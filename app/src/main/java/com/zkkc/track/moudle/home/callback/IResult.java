package com.zkkc.track.moudle.home.callback;


import com.zkkc.track.base.IBaseCallBack;

/**
 * Created by ShiJunRan on 2019/1/23
 * 照相
 */
public interface IResult extends IBaseCallBack {
    void Succeed();
    void Failure(String eStr);
}
