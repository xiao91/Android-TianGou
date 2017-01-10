package com.xiao91.heiboy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.xiao91.heiboy.fragment.FindFragment;
import com.xiao91.heiboy.fragment.MainFragment;
import com.xiao91.heiboy.fragment.MeFragment;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * 首页
 * <p>
 * xiao
 * <p>
 * 2017-01-03
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initDefaultFragment();

    }

    private void initView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.icon_home:
                        fragment = supportFragmentManager.findFragmentByTag("MainFragment");
                        if (fragment == null) {
                            fragment = MainFragment.newInstance("", "");
                        }
                        if (!fragment.isAdded()) {
                            fragmentTransaction.replace(R.id.main_frame, fragment, "MainFragment");
                        }
                        fragmentTransaction.commit();
                        break;
                    case R.id.icon_find:
                        fragment = supportFragmentManager.findFragmentByTag("FindFragment");
                        if (fragment == null) {
                            fragment = FindFragment.newInstance("", "");
                        }
                        if (!fragment.isAdded()) {
                            fragmentTransaction.replace(R.id.main_frame, fragment, "FindFragment");
                        }
                        fragmentTransaction.commit();
                        break;
                    case R.id.icon_me:
                        fragment = supportFragmentManager.findFragmentByTag("MeFragment");
                        if (fragment == null) {
                            fragment = MeFragment.newInstance("", "");
                        }
                        if (!fragment.isAdded()) {
                            fragmentTransaction.replace(R.id.main_frame, fragment, "MeFragment");
                        }
                        fragmentTransaction.commit();
                        break;
                    default:
                        break;
                }

                return true;
            }
        });

    }

    private void initDefaultFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        MainFragment mainFragment = MainFragment.newInstance("", "");
        if (!mainFragment.isAdded()) {
            fragmentTransaction.replace(R.id.main_frame, mainFragment);
        }
        fragmentTransaction.commit();
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
