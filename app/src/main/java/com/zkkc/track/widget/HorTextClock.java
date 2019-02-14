package com.zkkc.track.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextClock;

/**
 * Created by ShiJunRan on 2019/1/22
 */
public class HorTextClock extends TextClock {
    public HorTextClock(Context context) {
        super(context);
    }

    public HorTextClock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorTextClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HorTextClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
