package com.xiao91.heiboy.mvp_m;

import android.util.Log;

import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;
import com.xiao91.heiboy.bean.LoginUser;
import com.xiao91.heiboy.utils.TianGouUrls;

/**
 * Created by xiao on 2017/1/3 0003.
 *
 */

public class LoginModel {

    private MyOkHttp okHttp;

    public LoginModel() {
        okHttp = new MyOkHttp();
    }

    public void requestData(String username, String password, final OnCompleteDataListener<LoginUser> listener) {
        String url = TianGouUrls.HOME_URL + TianGouUrls.LOGIN_URL + "username=" +  username + "&password=" + password;
        okHttp.get()
                .url(url)
                .tag(this)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e("Login", "数据：" + response);

                        Gson gson = new Gson();
                        LoginUser loginUser = gson.fromJson(response, LoginUser.class);

                        if (listener != null) {
                            listener.onComplete(loginUser);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (listener != null) {
                            listener.onError(error_msg);
                        }
                    }
                });
    }
}
