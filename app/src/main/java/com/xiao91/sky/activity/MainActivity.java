package com.xiao91.sky.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xiao91.sky.R;
import com.xiao91.sky.activity.base.BaseActivity;
import com.xiao91.sky.fragment.FindFragment;
import com.xiao91.sky.fragment.MainFragment;
import com.xiao91.sky.fragment.MyFragment;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * 首页
 * <p>
 * xiao
 * <p>
 * 2017-01-03
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private View mView;

    // 记录当前显示的fragment
    private Fragment currentFragment;

    private MainFragment mainFragment;
    private FindFragment findFragment;
    private MyFragment myFragment;

    private TextView tv_home;
    private TextView tv_find;
    private TextView tv_me;

    @Override
    protected View setLayoutResId() {
        mView = LayoutInflater.from(this).inflate(R.layout.activity_main, null, false);
        return mView;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        removeToolbar();

        initNavigation();

        initDefaultFragment();

    }

    private void initNavigation() {
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_find = (TextView) findViewById(R.id.tv_find);
        tv_me = (TextView) findViewById(R.id.tv_me);

        tv_home.setOnClickListener(this);
        tv_find.setOnClickListener(this);
        tv_me.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home:
                tv_home.setSelected(true);
                tv_find.setSelected(false);
                tv_me.setSelected(false);
                if (mainFragment == null) {
                    mainFragment = MainFragment.newInstance("", "");
                }
                showFragment(mainFragment);
                break;
            case R.id.tv_find:
                tv_home.setSelected(false);
                tv_find.setSelected(true);
                tv_me.setSelected(false);
                if (findFragment == null) {
                    findFragment = FindFragment.newInstance("", "");
                }
                showFragment(findFragment);
                break;
            case R.id.tv_me:
                tv_home.setSelected(false);
                tv_find.setSelected(false);
                tv_me.setSelected(true);
                if (myFragment == null) {
                    myFragment = MyFragment.newInstance("", "");
                }
                showFragment(myFragment);
                break;
            default:
                break;
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment == fragment) {
            return;
        }

        if (!fragment.isAdded()) {
            fragmentTransaction.hide(currentFragment);
            fragmentTransaction.add(R.id.main_frame, fragment);
            fragmentTransaction.commit();
        }else {
            fragmentTransaction.hide(currentFragment);
            fragmentTransaction.show(fragment);
            fragmentTransaction.commit();
        }

        currentFragment = fragment;
    }

    private void initDefaultFragment() {
        tv_home.setSelected(true);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mainFragment == null)
            mainFragment = MainFragment.newInstance("", "");

        if (!mainFragment.isAdded()) {
            fragmentTransaction.add(R.id.main_frame, mainFragment);
            fragmentTransaction.commit();

            // 记录当前fragment
            currentFragment = mainFragment;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


}
