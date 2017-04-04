package com.xiao91.sky.mvp.presenter;

import java.lang.ref.WeakReference;

/**
 * 抽象类Presenter添加要实现view的泛型类
 * Created by XL on 2016.8.1
 */
public abstract class MVPBasePresenter<V> {
    // 弱引用
    protected WeakReference<V> mViewRef;

    /**
     * 与目标view建立关联
     * @param view
     */
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    /**
     * 获取当前弱引用对象
     * @return
     */
    protected V getView() {
        return mViewRef.get();
    }

    /**
     * 判断当前view是否关联
     * @return
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 解除关联
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

}
