package com.quanmin.sky.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.quanmin.sky.R;
import com.quanmin.sky.activity.base.BaseActivity;
import com.quanmin.sky.fragment.FindFragment;
import com.quanmin.sky.fragment.MainFragment;
import com.quanmin.sky.fragment.MineFragment;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * 首页
 * <p>
 * @author xiao 2017-01-03
 * <p>
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_find)
    TextView tvFind;
    @BindView(R.id.tv_me)
    TextView tvMe;

    // 记录当前显示的fragment
    private Fragment currentFragment;

    private MainFragment mainFragment;
    private FindFragment findFragment;
    private MineFragment mineFragment;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void iniView(View baseView) {
        removeToolbar();
        initDefaultFragment();
    }

    @Override
    protected void initData() {

    }

    private void initDefaultFragment() {
        tvHome.setSelected(true);

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

    private void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment == fragment) {
            return;
        }

        if (!fragment.isAdded()) {
            fragmentTransaction.hide(currentFragment);
            fragmentTransaction.add(R.id.main_frame, fragment);
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.hide(currentFragment);
            fragmentTransaction.show(fragment);
            fragmentTransaction.commit();
        }

        currentFragment = fragment;
    }

    @OnClick({R.id.tv_home, R.id.tv_find, R.id.tv_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_home:
                tvHome.setSelected(true);
                tvFind.setSelected(false);
                tvMe.setSelected(false);
                if (mainFragment == null) {
                    mainFragment = MainFragment.newInstance("", "");
                }
                showFragment(mainFragment);
                break;
            case R.id.tv_find:
                tvHome.setSelected(false);
                tvFind.setSelected(true);
                tvMe.setSelected(false);
                if (findFragment == null) {
                    findFragment = FindFragment.newInstance("", "");
                }
                showFragment(findFragment);
                break;
            case R.id.tv_me:
                tvHome.setSelected(false);
                tvFind.setSelected(false);
                tvMe.setSelected(true);
                if (mineFragment == null) {
                    mineFragment = MineFragment.newInstance("", "");
                }
                showFragment(mineFragment);
                break;
            default:
                break;
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
