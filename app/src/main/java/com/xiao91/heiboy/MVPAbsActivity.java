package com.xiao91.heiboy;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiao91.heiboy.mvp_p.AbsBasePresenter;


/**
 * MVP模式抽象类Activity
 * Created by Administrator on 2016.7.1.
 */
public abstract class MVPAbsActivity<V, T extends AbsBasePresenter<V>> extends BaseActivity {

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
