package com.zkkc.track.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.zkkc.track.R;
import com.zkkc.track.base.BaseActivity;
import com.zkkc.track.base.BasePresenter;
import com.zkkc.track.base.BaseView;
import com.zkkc.track.moudle.home.activity.MainAct;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ShiJunRan on 2019/2/14
 */
public class WelcomeAct extends BaseActivity {

    @BindView(R.id.ivShow)
    ImageView ivShow;
    @Override
    public int getLayoutId() {
        return R.layout.welcome_act;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    @Override
    public void init() {
        ivShow.setBackgroundResource(R.mipmap.welcome);

        //初始化渐变动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        //设置动画监听器
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(WelcomeAct.this, MainAct.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {


            }
        });
        ivShow.startAnimation(animation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        fullscreen(true);
    }
}
