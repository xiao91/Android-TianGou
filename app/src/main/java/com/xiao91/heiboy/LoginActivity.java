package com.xiao91.heiboy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiao91.heiboy.bean.LoginUser;
import com.xiao91.heiboy.mvp_p.LoginPresenter;
import com.xiao91.heiboy.mvp_v.LoginView;
import com.xiao91.heiboy.utils.SharedUtils;

/**
 * 登陆
 *
 * xiao
 *
 * 2017-01-07
 *
 *
 */
public class LoginActivity extends MVPAbsActivity<LoginView, LoginPresenter> implements LoginView {

    private EditText etUserName;
    private EditText etPwd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("登录");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 设置导航
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initView();

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    private void initView() {
        etUserName = (EditText) findViewById(R.id.et_username);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        Button btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUserName.getText().toString();
                String password = etPwd.getText().toString();

                mPresenter.requestData(username, password);
            }
        });
    }

    @Override
    public void showData(LoginUser result) {
        SharedUtils.setSharedBoolean(this, SharedUtils.LOGIN, true);
        SharedUtils.setSharedInt(this, SharedUtils.USERID, Integer.parseInt(result.data.userId));
    }

    @Override
    public void showErrorMessage(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }
}
