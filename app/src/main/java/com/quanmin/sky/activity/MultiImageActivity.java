package com.quanmin.sky.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.quanmin.sky.R;
import com.quanmin.sky.activity.base.BaseActivity;
import com.quanmin.sky.adapter.MultiViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 用于显示九宫图美女图片和漫画图片
 * <p>
 * @author xiao 2017-01-12
 */
public class MultiImageActivity extends BaseActivity {

    @BindView(R.id.multi_viewPager)
    ViewPager multiViewPager;
    @BindView(R.id.multi_tv_count)
    TextView multiTvCount;

    private int position = 0;
    private ArrayList<String> imgList = new ArrayList<>();
    private String title;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_multi_image;
    }

    @Override
    protected void iniView(View baseView) {
        setToolbarTitle(true, title);
        setToolbarNavigation(true);

        // 添加个数提示
        multiTvCount.setText((position + 1) + "/" + imgList.size());
        // 显示
        multiTvCount.setVisibility(View.VISIBLE);

        multiTvCount.postDelayed(new Runnable() {
            @Override
            public void run() {
                multiTvCount.setVisibility(View.GONE);
            }
        }, 2000);

        MultiViewPagerAdapter adapter = new MultiViewPagerAdapter(this, imgList);
        multiViewPager.setAdapter(adapter);

        // 指定显示第几个
        multiViewPager.setCurrentItem(position);

        multiViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 添加个数提示
                multiTvCount.setText((position + 1) + "/" + imgList.size());
                // 显示
                multiTvCount.setVisibility(View.VISIBLE);

                multiTvCount.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        multiTvCount.setVisibility(View.GONE);
                    }
                }, 2000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("position");
            imgList = bundle.getStringArrayList("imgList");
            title = bundle.getString("title");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (multiTvCount != null) {
            multiTvCount.removeCallbacks(null);
        }
    }
}
