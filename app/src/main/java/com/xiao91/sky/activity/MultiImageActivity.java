package com.xiao91.sky.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xiao91.sky.R;
import com.xiao91.sky.adapter.MultiViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用于显示九宫图美女图片和漫画图片
 *
 * 2017-01-12
 *
 */
public class MultiImageActivity extends AppCompatActivity {

    @BindView(R.id.multi_viewPager)
    ViewPager multiViewPager;
    @BindView(R.id.multi_tv_count)
    TextView multiTvCount;

    private int position = 0;
    private ArrayList<String> imgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_image);

        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("position");
            imgList = bundle.getStringArrayList("imgList");
        }

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

}
