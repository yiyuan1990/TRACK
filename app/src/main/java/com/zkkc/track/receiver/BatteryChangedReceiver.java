package com.zkkc.track.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.zkkc.track.entity.BatteryStateBean;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by ShiJunRan on 2019/1/21
 * 电池状态变化广播
 */
public class BatteryChangedReceiver extends BroadcastReceiver {
    private BatteryStateBean stateBean;
    @Override
    public void onReceive(Context context, Intent intent) {
        stateBean = new BatteryStateBean();
        // TODO Auto-generated method stub
        final String action = intent.getAction();
        if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_CHANGED)) {
            System.out.println("BatteryChangedReceiver BATTERY_CHANGED_ACTION---");
            // 当前电池的电压
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,
                    -1);
            // 电池的健康状态
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
            switch (health) {
                case BatteryManager.BATTERY_HEALTH_COLD:
                    System.out.println("BATTERY_HEALTH_COLD");
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    System.out.println("BATTERY_HEALTH_DEAD ");
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    System.out.println("BATTERY_HEALTH_GOOD");
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    System.out.println("BATTERY_HEALTH_OVERHEAT");
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    System.out.println("BATTERY_HEALTH_COLD");
                    break;
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    System.out.println("BATTERY_HEALTH_UNKNOWN");
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    System.out.println("BATTERY_HEALTH_UNSPECIFIED_FAILURE");
                    break;
                default:
                    break;
            }
            // 电池当前的电量, 它介于0和 EXTRA_SCALE之间
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            // 电池电量的最大值
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int percent = level * 100 / scale;
            stateBean.powStr = percent;

            // 当前手机使用的是哪里的电源
            int pluged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            switch (pluged) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    // 电源是AC charger.[应该是指充电器]
                    System.out.println("BATTERY_PLUGGED_AC");
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    // 电源是USB port
                    System.out.println("BATTERY_PLUGGED_USB ");
                    break;
                default:
                    break;
            }
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    // 正在充电
                    System.out.println("BATTERY_STATUS_CHARGING ");
                    stateBean.batteryType = "BATTERY_STATUS_CHARGING";
                    EventBus.getDefault().post(stateBean);
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    System.out.println("BATTERY_STATUS_DISCHARGING  ");
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    // 充满
                    System.out.println("BATTERY_STATUS_FULL ");
                    stateBean.batteryType = "BATTERY_STATUS_FULL";
                    EventBus.getDefault().post(stateBean);
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    // 没有充电
                    System.out.println("BATTERY_STATUS_NOT_CHARGING ");
                    stateBean.batteryType = "BATTERY_STATUS_NOT_CHARGING";
                    EventBus.getDefault().post(stateBean);
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    // 未知状态
                    System.out.println("BATTERY_STATUS_UNKNOWN ");
                    break;
                default:
                    break;
            }
            // 电池使用的技术。比如，对于锂电池是Li-ion
            String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            // 当前电池的温度
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            System.out.println("voltage = " + voltage + " technology = " + technology +
                    " temperature = " + temperature + " level = " + level + " scale = " + scale);
        } else if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_LOW)) {
            // 表示当前电池电量低
            System.out.println("BatteryChangedReceiver ACTION_BATTERY_LOW---");
        } else if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_OKAY)) {
            // 表示当前电池已经从电量低恢复为正常
            System.out.println("BatteryChangedReceiver ACTION_BATTERY_OKAY---");
        }
    }
}
