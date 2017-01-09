package com.xiao91.heiboy.mvp_p;

import com.xiao91.heiboy.bean.LoginUser;
import com.xiao91.heiboy.mvp_m.LoginModel;
import com.xiao91.heiboy.mvp_m.OnCompleteDataListener;
import com.xiao91.heiboy.mvp_v.LoginView;

/**
 *
 * Created by Administrator on 2017/1/3 0003.
 */

public class LoginPresenter extends AbsBasePresenter<LoginView> {

    private LoginView loginView;
    private final LoginModel loginModel;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;

        loginModel = new LoginModel();
    }

    public void requestData(String username, String password) {
        loginModel.requestData(username, password, new OnCompleteDataListener<LoginUser>() {
            @Override
            public void onComplete(LoginUser result) {
                loginView.showData(result);
            }

            @Override
            public void onError(String errorMsg) {
                loginView.showErrorMessage(errorMsg);
            }
        });
    }
}
