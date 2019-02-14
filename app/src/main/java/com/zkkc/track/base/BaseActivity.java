package com.zkkc.track.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;


import butterknife.ButterKnife;

/**
 * 父类->基类->动态指定类型->泛型设计（通过泛型指定动态类型->由子类指定，父类只需要规定范围即可）
 *
 * @param <V>
 * @param <P>
 */
public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity {
    public String TAG = getClass().getSimpleName();
    //引用V层和P层
    private P presenter;
    private V view;

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (view == null) {
            view = createView();
        }
        if (presenter != null && view != null) {
            presenter.attachView(view);
        }
        init();

    }


    //由子类指定具体类型
    public abstract int getLayoutId();

    public abstract P createPresenter();

    public abstract V createView();

    public abstract void init();



    public void fullscreen(boolean enable) {

        if (enable) { //显示状态栏

            WindowManager.LayoutParams lp = getWindow().getAttributes();

            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;

            getWindow().setAttributes(lp);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        } else { //隐藏状态栏

            WindowManager.LayoutParams lp = getWindow().getAttributes();

            lp.flags = (WindowManager.LayoutParams.FLAG_FULLSCREEN);

            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
