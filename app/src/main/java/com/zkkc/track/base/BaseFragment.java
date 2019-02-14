package com.zkkc.track.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>> extends Fragment {
    //引用V层和P层
    private P presenter;
    private V view;
    public Context mContext;
    private Unbinder unbinder;

    public P getPresenter() {
        return presenter;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (this.view == null) {
            this.view = createView();
        }
        if (presenter != null && view != null) {
            presenter.attachView(this.view);
        }
        init();
        return view;
    }

    //由子类指定具体类型
    public abstract int getLayoutId();

    public abstract P createPresenter();

    public abstract V createView();

    public abstract void init();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
