package com.xiao91.heiboy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 所有activity的基类:可以初始化第三方平台的数据
 *
 * Created by xiao91 on 2016.8.1.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
