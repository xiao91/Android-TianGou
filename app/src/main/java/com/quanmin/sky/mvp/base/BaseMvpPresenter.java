package com.quanmin.sky.mvp.base;

import java.lang.ref.WeakReference;

/**
 * 抽象类Presenter添加要实现view的泛型类
 *
 * @author xiao 2017-05-24
 */
public abstract class BaseMvpPresenter<V> {

    // 弱引用
    protected WeakReference<V> mViewRef;

    /**
     * 与目标view建立关联
     *
     * @param view 绑定的View
     */
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    /**
     * 获取当前弱引用对象
     *
     * @return V
     */
    protected V getView() {
        return mViewRef.get();
    }

    /**
     * 判断当前view是否关联
     *
     * @return boolean
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
