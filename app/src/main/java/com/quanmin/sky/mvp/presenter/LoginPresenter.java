package com.quanmin.sky.mvp.presenter;

import com.quanmin.sky.bean.LoginEntry;
import com.quanmin.sky.mvp.base.BaseMvpPresenter;
import com.quanmin.sky.mvp.base.OnCompleteDataListener;
import com.quanmin.sky.mvp.model.LoginModel;
import com.quanmin.sky.mvp.view.LoginView;

/**
 * 登录
 * Created by Administrator on 2017/1/3 0003.
 */

public class LoginPresenter extends BaseMvpPresenter<LoginView> {

    private LoginView loginView;
    private final LoginModel userModel;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;

        userModel = new LoginModel();
    }

    public void onLoginRequest(String username, String password, String deviceId) {
        loginView.onShowProgressbar();

        userModel.onLoginRequest(username, password, deviceId, new OnCompleteDataListener<LoginEntry>() {
            @Override
            public void onComplete(LoginEntry result) {
                loginView.onShowData(result);
            }

            @Override
            public void onError(int code) {
                loginView.onShowError(code);
            }

        });

        loginView.onHideProgressbar();
    }
}
