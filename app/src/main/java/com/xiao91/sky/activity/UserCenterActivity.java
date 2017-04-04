package com.xiao91.sky.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xiao91.sky.R;
import com.xiao91.sky.bean.UserCenter;
import com.xiao91.sky.listener.OnAppBarStateChangeListener;
import com.xiao91.sky.domain.UCViewDomain;
import com.xiao91.sky.utils.SharedUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 用户中心
 * <p>
 * 2017-02-18
 */

public class UserCenterActivity extends AppCompatActivity {

    @BindView(R.id.iv_uc_dog)
    ImageView ivUcDog;
    @BindView(R.id.iv_uc_photo)
    ImageView ivUcPhoto;
    @BindView(R.id.tv_uc_addr)
    TextView tvUcAddr;
    @BindView(R.id.toolbar_uc)
    Toolbar toolbarUc;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.ll_uc_activity)
    LinearLayout llUCActivity;

    private boolean isLogin;
    // 自己登陆后的id
    private int userId;
    // 当前查看内容的对应用户id
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_uc);
        toolbar.setTitle("tiangou");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new OnAppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    // 展开状态
                    toolbar.setTitle("");
                } else if (state == State.COLLAPSED) {
                    // 折叠状态
                    toolbar.setTitle("tiangou");
                } else {
                    // 中间状态
                    toolbar.setTitle("");
                }
            }
        });

        // 背景图片
        ImageView imageView = (ImageView) findViewById(R.id.iv_uc_dog);
        Glide.with(this)
                .load(R.mipmap.dog)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .bitmapTransform(new BlurTransformation(this, 25, 2))
                .into(imageView);

        // 用户头像
        ImageView iv_uc_photo = (ImageView) findViewById(R.id.iv_uc_photo);
        Glide.with(this)
                .load(R.mipmap.dog)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(iv_uc_photo);

        UCViewDomain manager = new UCViewDomain(this, new UserCenter(2));
        llUCActivity.addView(manager.getUCView());
    }

    private void initData() {
        isLogin = SharedUtils.getSharedBoolean(this, SharedUtils.LOGIN, false);
        userId = SharedUtils.getSharedInt(this, SharedUtils.USERID, 0);

        uid = getIntent().getStringExtra("userId");
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
        }else if (id == R.id.action_uc_relay){
            Toast.makeText(this, "转发", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
