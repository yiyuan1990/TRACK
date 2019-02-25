package com.zkkc.track.moudle.home.entity;

import com.xuhao.didi.core.iocore.interfaces.ISendable;

/**
 * Created by ShiJunRan on 2019/2/18
 */
public class SendData implements ISendable {
    private byte[] msg;

    public SendData(byte[] msg) {
        this.msg = msg;
    }

    @Override
    public byte[] parse() {

        return msg;

    }
}