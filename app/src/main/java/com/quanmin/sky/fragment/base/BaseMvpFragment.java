package com.quanmin.sky.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.quanmin.sky.R;
import com.quanmin.sky.mvp.base.BaseMvpPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVP模式所有fragment基类
 *
 * @author xiao 2017.03.10
 */
public abstract class BaseMvpFragment<V, T extends BaseMvpPresenter<V>> extends BaseFragment {

    // Presenter对象
    protected T mPresenter;

    protected View mBaseView;
    protected FrameLayout mBaseFrameLayout;
    // ButterKnife
    private Unbinder mUnbinder;
    private LinearLayout mBaseLoading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 创建Presenter
        mPresenter = createPresenter();

        // View与Presenter建立关联
        mPresenter.attachView((V) this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, null, false);
        mBaseFrameLayout = (FrameLayout) view.findViewById(R.id.base_frame);
        // 加载view
        mBaseLoading = (LinearLayout) view.findViewById(R.id.rl_loading);
        // 子类实现添加布局
        mBaseFrameLayout.addView(addChildView(setLayoutResId()));
        initData();
        initView(mBaseView);
        return view;
    }

    private View addChildView(int layoutResId) {
        mBaseView = LayoutInflater.from(getActivity()).inflate(layoutResId, mBaseFrameLayout, false);
        // 初始化ButterKnife
        mUnbinder = ButterKnife.bind(this, mBaseView);
        return mBaseView;
    }

    /**
     * 子类实现设置布局layout
     */
    protected abstract int setLayoutResId();

    /**
     * 子类实现初始化视图控件View
     */
    protected abstract void initView(View baseView);

    /**
     * 子类实现初始化数据
     */
    protected abstract void initData();

    /**
     * 隐藏加载进度
     */
    protected void setHideProgressbar() {
        if (mBaseLoading.getVisibility() != View.GONE) {
            mBaseLoading.setVisibility(View.GONE);
        }
    }

    /**
     * 显示加载进度
     */
    protected void setShowProgressbar() {
        if (mBaseLoading.getVisibility() != View.VISIBLE) {
            mBaseLoading.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示toast网络请求错误
     * @param code 错误码
     */
    protected void showToastError(int code) {
        String format = String.format(getResources().getString(R.string.toast_error_code), code);
        Toast.makeText(getActivity(), format, Toast.LENGTH_SHORT).show();
    }

    /**
     * 创建泛型的Presenter
     *
     * @return T
     */
    protected abstract T createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

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
