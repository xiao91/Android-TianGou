package com.quanmin.sky.mvp.model;

import android.util.Log;

import com.quanmin.sky.bean.RegisterEntry;
import com.quanmin.sky.config.ConfigUrl;
import com.quanmin.sky.mvp.base.IBaseModel;
import com.quanmin.sky.mvp.base.OnCompleteDataListener;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;

/**
 * 用户注册
 * Created by xiao on 2017/1/3 0003.
 *
 */

public class RegisterModel extends IBaseModel {

    @Override
    protected String getModelTag() {
        return RegisterModel.class.getSimpleName();
    }

    /**
     * 手机号注册
     * @param phone 手机号
     * @param password 密码
     * @param listener 接口监听
     */
    public void onRequestRegister(String phone, String password, final OnCompleteDataListener<RegisterEntry> listener) {
        String url = ConfigUrl.USER_REGISTER_PHONE + "&phone=" +  phone + "&password=" + password;

        mGetBuilder.url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e(tag, "onSuccess()：response=" + response);

                        if (statusCode == STATUS_CODE_SUCCESS) {
                            RegisterEntry registerEntry = gson.fromJson(response, RegisterEntry.class);

                            if (listener != null) {
                                listener.onComplete(registerEntry);
                            }
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (null != listener) {
                            listener.onError(statusCode);
                        }
                    }
                });
    }


}
