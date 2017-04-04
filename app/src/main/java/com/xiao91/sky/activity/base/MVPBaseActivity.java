package com.xiao91.sky.activity.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiao91.sky.R;
import com.xiao91.sky.mvp.presenter.MVPBasePresenter;

import butterknife.ButterKnife;

/**
 * MVP模式抽象类Activity
 * @author xiao 2017.03.11
 *
 */
public abstract class MVPBaseActivity<V, T extends MVPBasePresenter<V>> extends AppCompatActivity {

    // Presenter对象
    protected T mPresenter;

    // 可伸缩的CoordinatorLayout
    protected CoordinatorLayout mBaseCoordinatorLayout;
    // toolbar的父view
    protected AppBarLayout mAppBarLayout;
    // 声明toolbar：标题居左
    private Toolbar mBaseToolbar;
    // 标题居中显示
    private TextView mBaseTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 创建Presenter
        mPresenter = createPresenter();
        // View与Presenter建立关联
        mPresenter.attachView((V) this);

        initAll();

    }

    private void initAll() {
        View mBaseView = LayoutInflater.from(this).inflate(R.layout.activity_base, null, false);
        initChildView(mBaseView);
        setContentView(mBaseView);
        initData();
        initView();
    }

    private void initChildView(View mBaseView) {
        // 其他初始化...
        mBaseCoordinatorLayout = (CoordinatorLayout) mBaseView.findViewById(R.id.base_cl);
        mAppBarLayout = (AppBarLayout) mBaseView.findViewById(R.id.base_appbar);

        mBaseToolbar = (Toolbar) mBaseView.findViewById(R.id.base_toolbar);
        mBaseToolbar.setTitle("");
        mBaseToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mBaseToolbar);

        RelativeLayout mBaseRelativeLayout = (RelativeLayout) mBaseView.findViewById(R.id.base_rl);
        mBaseRelativeLayout.addView(setLayoutResId());

        // 中间标题
        mBaseTitle = (TextView) mBaseView.findViewById(R.id.tv_base_title);
    }

    /**
     * 创建泛型的Presenter
     * @return
     */
    protected abstract T createPresenter();

    /**
     * 子类实现设置布局layout
     * @return int
     */
    protected abstract View setLayoutResId();

    protected abstract void initData();

    protected abstract void initView();

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
     * 移除toolbar，即没有toolbar，使用自定义的标题栏
     *
     */
    protected void removeToolbar() {
        mBaseCoordinatorLayout.removeView(mAppBarLayout);
    }

    /**
     * toolbar是否导航
     * @param isNavigation
     */
    protected void setToolbarNavigation(boolean isNavigation) {
        if (isNavigation) {
            // 显示导航，默认采用系统导航图表
//            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
