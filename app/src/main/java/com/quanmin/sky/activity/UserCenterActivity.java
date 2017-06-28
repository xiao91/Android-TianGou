package com.quanmin.sky.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.quanmin.sky.R;
import com.quanmin.sky.activity.base.BaseActivity;
import com.quanmin.sky.adapter.TabLayoutAdapter;
import com.quanmin.sky.fragment.MyChildFragment;
import com.quanmin.sky.glide.XBlurTransformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 用户中心
 * <p>
 *
 * @author xiao 2017-02-18
 */

public class UserCenterActivity extends BaseActivity {

    @BindView(R.id.uc_iv_bg)
    ImageView uc_iv_bg;
    @BindView(R.id.uc_iv_head)
    ImageView uc_iv_head;
    @BindView(R.id.uc_rl_title)
    RelativeLayout uc_rl_title;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    // 当前查看内容的对应用户id
    private String uid;

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void iniView(View baseView) {
        fab.setVisibility(View.GONE);
        uc_rl_title.setVisibility(View.GONE);

        setToolbarNavigation(true);
        setToolbarTitle(true, "用户中心");

        // 绑定viewpager
        List<Fragment> mFragments = new ArrayList<>();
        String[] mTitles = getResources().getStringArray(R.array.arr_user_tab);
        for (String mTitle : mTitles) {
            MyChildFragment listFragment = MyChildFragment.newInstance(mTitle, "");
            mFragments.add(listFragment);
        }
        TabLayoutAdapter adapter =
                new TabLayoutAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewpager.setAdapter(adapter);
        tabs.setupWithViewPager(viewpager);

        // 背景图片
        RequestOptions options = new RequestOptions();
        options.transform(new XBlurTransformation(this));
        Glide.with(this)
                .load(R.mipmap.dog)
                .apply(options)
                .into(uc_iv_bg);

        // 用户头像
        RequestOptions options2 = new RequestOptions();
        options2.circleCrop();
        Glide.with(this)
                .load(R.mipmap.dog)
                .apply(options2)
                .into(uc_iv_head);
    }

    @Override
    protected void initData() {
        uid = getIntent().getStringExtra("uid");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_uc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_uc_home) {
            Toast.makeText(this, "进入主页", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_uc_relay) {
            Toast.makeText(this, "转发", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
