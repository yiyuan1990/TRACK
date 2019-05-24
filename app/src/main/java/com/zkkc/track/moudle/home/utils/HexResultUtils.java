package com.zkkc.track.moudle.home.utils;


import android.app.Activity;
import android.os.Build;
import android.view.View;

import com.zkkc.track.entity.HostDaoBean;

/**
 * Created by ShiJunRan on 2019/2/22
 * 输出16进制
 */
public class HexResultUtils {
    /**
     * 获取视频url
     *
     * @return
     */
    public static String toPlayUrl(HostDaoBean hostDaobean, boolean isDebug) {
        String sIp = hostDaobean.getSIp();
        String sPort = hostDaobean.getSPort();
        String hName = hostDaobean.getHName();
        String hPw = hostDaobean.getHPw();
        int mPort = Integer.parseInt(sPort);
        String url = "rtsp://" + sIp + ":" + mPort + "/user=" + hName + "&password=" + hPw + "&channel=1&stream=0.sdp";
        if (isDebug) {
            String url2 = "rtsp://" + sIp + ":" + mPort + "/steam1";
            return url2;
        }
        return url;
    }


    /**
     * 前行
     *
     * @return
     */
    public static byte[] sendZJUp() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x21;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0xE7;
        msg[7] = (byte) 0x7C;
//        int i = CRC16Util.calcCrc16(msg);
//        CRC16Util.setParamCRC(msg)
        return msg;
    }

    /**
     * 前行右转
     *
     * @return
     */
    public static byte[] sendZJUpAndRight() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x20;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0x77;
        msg[7] = (byte) 0x7D;
        return msg;
    }

    /**
     * 右转
     *
     * @return
     */
    public static byte[] sendZJRight() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x27;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0x47;
        msg[7] = (byte) 0x7F;
        return msg;
    }

    /**
     * 后退右转
     *
     * @return
     */
    public static byte[] sendZJDownAndRight() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x26;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0xD7;
        msg[7] = (byte) 0x7E;
        return msg;
    }

    /**
     * 后退
     *
     * @return
     */
    public static byte[] sendZJDown() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x25;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0x27;
        msg[7] = (byte) 0x7E;
        return msg;
    }

    /**
     * 后退左转
     *
     * @return
     */
    public static byte[] sendZJDownAndLeft() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x24;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0xB7;
        msg[7] = (byte) 0x7F;
        return msg;
    }

    /**
     * 左转
     *
     * @return
     */
    public static byte[] sendZJLeft() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x23;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0x87;
        msg[7] = (byte) 0x7D;
        return msg;
    }

    /**
     * 前行左转
     *
     * @return
     */
    public static byte[] sendZJUpAndLeft() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x22;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0x17;
        msg[7] = (byte) 0x7C;
        return msg;
    }

    /**
     * 停止移动
     *
     * @return
     */
    public static byte[] sendZJStopMove() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x28;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0xB7;
        msg[7] = (byte) 0x7A;
        return msg;
    }

    /**
     * 摆臂反转
     *
     * @return
     */
    public static byte[] sendBBDown() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x29;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0x27;
        msg[7] = (byte) 0x7B;
        return msg;
    }

    /**
     * 摆臂正转
     *
     * @return
     */
    public static byte[] sendBBUp() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x2A;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0xD7;
        msg[7] = (byte) 0x7B;
        return msg;
    }

    /**
     * 摆臂停止
     *
     * @return
     */
    public static byte[] sendBBStop() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x2B;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0x47;
        msg[7] = (byte) 0x7A;
        return msg;
    }

    /**
     * 前行速度档位查询
     *
     * @return
     */
    public static byte[] sendGoQuery() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x00;

        msg[4] = (byte) 0x0A;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0xD7;
        msg[7] = (byte) 0x33;
        return msg;
    }

    /**
     * 前行速度档位
     *
     * @return
     */
    public static byte[] sendGoSpeed(int speed) {
        byte[] msg = new byte[9];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x2C;
        msg[5] = (byte) 0x01;
        if (speed == 1) {
            msg[6] = (byte) 0x01;
            msg[7] = (byte) 0x72;
            msg[8] = (byte) 0xB7;
        } else if (speed == 2) {
            msg[6] = (byte) 0x02;
            msg[7] = (byte) 0x73;
            msg[8] = (byte) 0xF7;
        } else {
            msg[6] = (byte) 0x03;
            msg[7] = (byte) 0xB3;
            msg[8] = (byte) 0x36;
        }

        return msg;
    }

    /**
     * 查询摆臂速度
     */
    public static byte[] queryBBSpeed() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x00;

        msg[4] = (byte) 0x0D;
        msg[5] = (byte) 0x00;
        msg[6] = (byte) 0xE7;
        msg[7] = (byte) 0x31;
        return msg;
    }

    /**
     * 摆臂速度档位设置+，-
     *
     * @return
     */
    public static byte[] sendBBSpeed(int num) {
        byte[] msg = new byte[9];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x2D;
        msg[5] = (byte) 0x01;
        if (num == 1) {//增加档
            msg[6] = (byte) 0x01;
            msg[7] = (byte) 0xB2;
            msg[8] = (byte) 0xE6;
        } else if (num == 2) {//减少档
            msg[6] = (byte) 0x02;
            msg[7] = (byte) 0xB3;
            msg[8] = (byte) 0xA6;
        }

        return msg;
    }

    /**
     * 摄像头控制上下方向
     * num  1 上   2 下
     *
     * @return
     */
    public static byte[] sendSSTUpDown(int num) {
        byte[] msg = new byte[9];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x30;
        msg[5] = (byte) 0x01;
        if (num == 1) {//上
            msg[6] = (byte) 0x01;
            msg[7] = (byte) 0xB4;
            msg[8] = (byte) 0x76;
        } else {//下
            msg[6] = (byte) 0x02;
            msg[7] = (byte) 0xB5;
            msg[8] = (byte) 0x36;
        }

        return msg;
    }

    /**
     * 摄像头控制左右方向
     * num  1 左   2 右
     *
     * @return
     */
    public static byte[] sendSSTLeftRight(int num) {
        byte[] msg = new byte[9];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x31;
        msg[5] = (byte) 0x01;
        if (num == 1) {//左
            msg[6] = (byte) 0x01;
            msg[7] = (byte) 0x74;
            msg[8] = (byte) 0x27;
        } else {//右
            msg[6] = (byte) 0x02;
            msg[7] = (byte) 0x75;
            msg[8] = (byte) 0x67;
        }

        return msg;
    }

    /**
     * 摄像头控制停止
     * num  1  上下    2 左右
     *
     * @return
     */
    public static byte[] sendSSTStop(int num) {
        byte[] msg = new byte[9];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;
        if (num == 1) {
            msg[4] = (byte) 0x30;
            msg[5] = (byte) 0x01;

            msg[6] = (byte) 0x00;
            msg[7] = (byte) 0x74;
            msg[8] = (byte) 0xB7;
        } else {
            msg[4] = (byte) 0x31;
            msg[5] = (byte) 0x01;

            msg[6] = (byte) 0x00;
            msg[7] = (byte) 0xB4;
            msg[8] = (byte) 0xB6;
        }


        return msg;
    }

    /**
     * 摄像头调焦
     * num  1  +    2  -   3  stop
     */
    public static byte[] sendSSTTJ(int num) {
        byte[] msg = new byte[9];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x34;
        msg[5] = (byte) 0x01;
        if (num == 1) {
            msg[6] = (byte) 0x01;
            msg[7] = (byte) 0x75;
            msg[8] = (byte) 0x37;
        } else if (num == 2) {
            msg[6] = (byte) 0x02;
            msg[7] = (byte) 0x74;
            msg[8] = (byte) 0x77;
        } else {
            msg[6] = (byte) 0x00;
            msg[7] = (byte) 0xB5;
            msg[8] = (byte) 0xF6;
        }


        return msg;
    }

    /**
     * 自动聚焦
     */
    public static byte[] sendSSTZDJJ() {
        byte[] msg = new byte[9];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x01;

        msg[4] = (byte) 0x35;
        msg[5] = (byte) 0x01;

        msg[6] = (byte) 0x00;
        msg[7] = (byte) 0x75;
        msg[8] = (byte) 0xA7;

        return msg;
    }

    /**
     * 查询LED灯光亮度
     */
    public static byte[] queryLEDLD() {
        byte[] msg = new byte[8];
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x00;

        msg[4] = (byte) 0x0E;
        msg[5] = (byte) 0x00;

        msg[6] = (byte) 0x17;
        msg[7] = (byte) 0x31;

        return msg;
    }
}
