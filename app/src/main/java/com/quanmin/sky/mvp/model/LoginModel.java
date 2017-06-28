package com.quanmin.sky.mvp.model;

import android.util.Log;

import com.quanmin.sky.bean.LoginEntry;
import com.quanmin.sky.config.ConfigUrl;
import com.quanmin.sky.mvp.base.IBaseModel;
import com.quanmin.sky.mvp.base.OnCompleteDataListener;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;

/**
 * 登录
 * Created by xiao on 2017/1/3 0003.
 *
 */

public class LoginModel extends IBaseModel {

    @Override
    protected String getModelTag() {
        return LoginModel.class.getSimpleName();
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param listener 监听借口
     */
    public void onLoginRequest(String username, String password, String deviceId, final OnCompleteDataListener<LoginEntry> listener) {
        String url = ConfigUrl.USER_LOGIN + "&username=" +  username + "&password=" + password + "&device_id=" + deviceId + "&is_line=" + false + "&mobile_type=Android";

        mGetBuilder.url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e(tag, "onSuccess()：response=" + response);

                        if (statusCode == STATUS_CODE_SUCCESS) {
                            LoginEntry loginEntry = gson.fromJson(response, LoginEntry.class);

                            if (listener != null) {
                                listener.onComplete(loginEntry);
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e(tag, "onFailure()：statusCode=" + statusCode);

                        if (null != listener) {
                            listener.onError(statusCode);
                        }

                    }
                });
    }

}
