package com.zkkc.track.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zkkc.track.welcome.WelcomeAct;


/**
 * Created by ShiJunRan on 2019/1/17
 * 开机广播监听
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ACTION)) {
            Log.e("TAG", "onReceive: 开机启动");
            //开机启动
            Intent mainIntent = new Intent(context, WelcomeAct.class);
            //在BroadcastReceiver中显示Activity，必须要设置FLAG_ACTIVITY_NEW_TASK标志
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainIntent);
        }
    }
}
