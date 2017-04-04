package com.xiao91.sky.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiao91.sky.R;
import com.xiao91.sky.activity.base.MVPBaseActivity;
import com.xiao91.sky.bean.UserLoginEntry;
import com.xiao91.sky.mvp.presenter.LoginPresenter;
import com.xiao91.sky.mvp.view.LoginView;
import com.xiao91.sky.utils.SharedUtils;

/**
 * 登陆
 *
 * xiao
 *
 * 2017-01-07
 *
 *
 */
public class LoginActivity extends MVPBaseActivity<LoginView, LoginPresenter> implements LoginView {

    private EditText etUserName;
    private EditText etPwd;
    private View mView;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected View setLayoutResId() {
        mView = LayoutInflater.from(this).inflate(R.layout.activity_login, null, false);
        return mView;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        etUserName = (EditText) mView.findViewById(R.id.et_username);
        etPwd = (EditText) mView.findViewById(R.id.et_pwd);
        Button btnLogin = (Button) mView.findViewById(R.id.btn_login);

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
    public void onShowData(UserLoginEntry data) {
        SharedUtils.setSharedBoolean(this, SharedUtils.LOGIN, true);
        SharedUtils.setSharedInt(this, SharedUtils.USERID, Integer.parseInt(data.data.userId));

    }

    @Override
    public void onShowEmptyData() {

    }

    @Override
    public void onShowNetError() {

    }

    @Override
    public void onShowServerError() {

    }
}
