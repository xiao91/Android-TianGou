package com.xiao91.heiboy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.xiao91.heiboy.mvp_p.AbsBasePresenter;

/**
 * MVP模式所有fragment基类
 * Created by xiao91 on 2016/8/23.
 */
public abstract class MVPAbsFragment<V, T extends AbsBasePresenter<V>> extends Fragment {

    // Presenter对象
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 创建Presenter
        mPresenter = createPresenter();

        // View与Presenter建立关联
        mPresenter.attachView((V) this);

    }

    /**
     * 创建泛型的Presenter
     * @return
     */
    protected abstract T createPresenter();

    /**
     * 解除关联
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPresenter.isViewAttached()) {
            mPresenter.detachView();
        }
    }
}
