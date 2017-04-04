package com.xiao91.sky.mvp.presenter;

import com.xiao91.sky.bean.UserLoginEntry;
import com.xiao91.sky.mvp.model.UserModel;
import com.xiao91.sky.mvp.model.OnCompleteDataListener;
import com.xiao91.sky.mvp.view.LoginView;

/**
 *
 * Created by Administrator on 2017/1/3 0003.
 */

public class LoginPresenter extends MVPBasePresenter<LoginView> {

    private LoginView loginView;
    private final UserModel userModel;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;

        userModel = new UserModel();
    }

    public void requestData(String username, String password) {
        userModel.requestData(username, password, new OnCompleteDataListener<UserLoginEntry>() {
            @Override
            public void onComplete(UserLoginEntry result) {
                loginView.onShowData(result);
            }

            @Override
            public void onError(int errorCode) {
                loginView.onShowNetError();
            }
        });
    }
}
