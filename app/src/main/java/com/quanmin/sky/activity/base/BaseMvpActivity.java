package com.quanmin.sky.activity.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quanmin.sky.R;
import com.quanmin.sky.mvp.base.BaseMvpPresenter;
import com.quanmin.sky.utils.FixFocusedViewLeakUtils;
import com.quanmin.sky.utils.SharedUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVP模式抽象类Activity
 *
 * @author xiao 2017-05-24
 */
public abstract class BaseMvpActivity<V, T extends BaseMvpPresenter<V>> extends AppCompatActivity {

    // 可伸缩的CoordinatorLayout
    private CoordinatorLayout mBaseCoordinatorLayout;
    // toolbar的父view
    private AppBarLayout mAppBarLayout;
    // 声明toolbar：标题居左
    private Toolbar mBaseToolbar;
    // 标题居中显示
    private TextView mBaseTitle;
    private RelativeLayout mBaseRelativeLayout;
    // 子类根View，可以查找控件
    private View mBaseView;
    // 加载view
    private LinearLayout mBaseLoading;

    // Presenter对象
    protected T mPresenter;

    // ButterKnife
    private Unbinder mUnbinder;

    // 登录后的userId
    protected String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 修复输入法的内存泄漏
        FixFocusedViewLeakUtils.fixFocusedViewLeak(getApplication());

        // 创建Presenter
        mPresenter = createPresenter();

        // View与Presenter建立关联
        mPresenter.attachView((V) this);

        initData();
        View view = LayoutInflater.from(this).inflate(R.layout.activity_base, null, false);
        initChildView(view);
        setContentView(view);
        iniView(mBaseView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 取登陆后的值
        userId = SharedUtils.getSharedString(this, SharedUtils.USER_ID, "");
    }

    private void initChildView(View mBaseView) {
        // 其他初始化...
        mBaseCoordinatorLayout = (CoordinatorLayout) mBaseView.findViewById(R.id.base_cl);
        mAppBarLayout = (AppBarLayout) mBaseView.findViewById(R.id.base_appbar);

        mBaseToolbar = (Toolbar) mBaseView.findViewById(R.id.base_toolbar);
        mBaseToolbar.setTitle("");
        mBaseToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mBaseToolbar);

        // 加载view
        mBaseLoading = (LinearLayout) mBaseView.findViewById(R.id.rl_loading);

        mBaseRelativeLayout = (RelativeLayout) mBaseView.findViewById(R.id.base_rl);

        // 中间标题
        mBaseTitle = (TextView) mBaseView.findViewById(R.id.tv_base_title);
        mBaseRelativeLayout.addView(addChildView(setLayoutResId()));
    }

    private View addChildView(int layoutResId) {
        mBaseView = LayoutInflater.from(this).inflate(layoutResId, mBaseRelativeLayout, false);
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
    protected abstract void iniView(View baseView);

    /**
     * 子类实现初始化数据
     */
    protected abstract void initData();

    /**
     * toolbar居左设置标题，并且中间TextView消失
     * @param isCenter
     * @param title
     */
    protected void setToolbarTitle(boolean isCenter, String title) {
        if (isCenter) {
            mBaseTitle.setVisibility(View.VISIBLE);
            mBaseTitle.setText(title);
        }else {
            mBaseTitle.setVisibility(View.GONE);
            mBaseToolbar.setTitle(title);
        }
    }

    /**
     * toolbar是否导航
     * @param isNavigation 是否导航
     */
    protected void setToolbarNavigation(boolean isNavigation) {
        if (isNavigation) {
            // 显示导航，默认采用系统导航图表
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // toolbar点击返回
            mBaseToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    /**
     * toolbar导航图片
     * @param icon
     */
    protected void setToolbarNavigationIcon(int icon) {
        mBaseToolbar.setNavigationIcon(icon);
    }

    /**
     * 设置toolbar中居中的标题隐藏，以便toolbar可以居左设置标题
     *
     * @param isVisible 是否隐藏中间标题
     */
    protected void setTitleVisible(boolean isVisible) {
        int visible = isVisible ? View.GONE : View.VISIBLE;
        mBaseTitle.setVisibility(visible);
    }

    /**
     * 删除toolbar
     */
    protected void removeToolbar() {
        mBaseCoordinatorLayout.removeView(mAppBarLayout);
    }

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
        Toast.makeText(this, format, Toast.LENGTH_SHORT).show();
    }


    /**
     * 创建泛型的Presenter
     *
     * @return t 对应子类的Presenter
     */
    protected abstract T createPresenter();

    /**
     * 解除关联
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter.isViewAttached()) {
            mPresenter.detachView();
        }

        if(mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
