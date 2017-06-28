package com.quanmin.sky.mvp.presenter;

import com.quanmin.sky.bean.RegisterEntry;
import com.quanmin.sky.bean.LoginEntry;
import com.quanmin.sky.mvp.base.BaseMvpPresenter;
import com.quanmin.sky.mvp.base.OnCompleteDataListener;
import com.quanmin.sky.mvp.model.LoginModel;
import com.quanmin.sky.mvp.model.RegisterModel;
import com.quanmin.sky.mvp.view.RegisterViewImpl;

/**
 * 注册
 * Created by xiao on 2017/1/3 0003.
 */

public class RegisterPresenter extends BaseMvpPresenter<RegisterViewImpl> {

    private RegisterViewImpl registerView;
    private final RegisterModel registerModel;

    public RegisterPresenter(RegisterViewImpl registerView) {
        this.registerView = registerView;

        registerModel = new RegisterModel();
    }

    /**
     * 注册
     * @param phone 手机号
     * @param password 密码
     */
    public void onRequestRegister(String phone, String password) {
        registerView.onShowProgressbar();
        registerModel.onRequestRegister(phone, password, new OnCompleteDataListener<RegisterEntry>() {

            @Override
            public void onComplete(RegisterEntry data) {
                registerView.onShowData(data);
            }

            @Override
            public void onError(int code) {
                registerView.onShowError(code);
            }
        });
    }

    /**
     * 登录
     * @param phone 手机号
     * @param password 密码
     */
    public void onRequestLogin(String phone, String password, String token) {
        LoginModel loginModel = new LoginModel();
//        loginModel.onLoginRequest(phone, password, token, new OnCompleteDataListener<LoginEntry>() {
//            @Override
//            public void onComplete(LoginEntry data) {
//                registerView.onLoginSuccess(data);
//            }
//
//            @Override
//            public void onError(int code) {
//                registerView.onLoginFailure(code);
//            }
//        });

        registerView.onHideProgressbar();
    }
}
