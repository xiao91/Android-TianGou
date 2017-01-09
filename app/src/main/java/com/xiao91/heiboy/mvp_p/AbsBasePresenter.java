package com.xiao91.heiboy.mvp_p;

import java.lang.ref.WeakReference;

/**
 * 抽象类Presenter添加要实现view的泛型类
 * Created by XL on 2016.8.1
 */
public abstract class AbsBasePresenter<T> {
    // 弱引用
    protected WeakReference<T> mViewRef;

    /**
     * 与目标view建立关联
     * @param view
     */
    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    /**
     * 获取当前弱引用对象
     * @return
     */
    protected T getView() {
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
