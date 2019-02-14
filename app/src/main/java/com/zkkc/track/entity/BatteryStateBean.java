package com.zkkc.track.entity;

/**
 * Created by ShiJunRan on 2019/1/21
 */
public class BatteryStateBean {
   public int powStr;
   public String batteryType;

    public BatteryStateBean() {
    }

    public BatteryStateBean(int powStr, String batteryType) {
        this.powStr = powStr;
        this.batteryType = batteryType;
    }
}
