package com.quanmin.sky.mvp.model;

import android.util.Log;

import com.quanmin.sky.bean.LoginEntry;
import com.quanmin.sky.bean.UserUpdateEntry;
import com.quanmin.sky.config.ConfigUrl;
import com.quanmin.sky.mvp.base.IBaseModel;
import com.quanmin.sky.mvp.base.OnCompleteDataListener;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;

/**
 * 用户登录
 * Created by xiao on 2017/1/3 0003.
 *
 */

public class UserModel extends IBaseModel {

    @Override
    protected String getModelTag() {
        return UserModel.class.getSimpleName();
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param listener 监听借口
     */
    public void onLoginRequestData(String username, String password, String token, final OnCompleteDataListener<LoginEntry> listener) {
        String url = ConfigUrl.USER_LOGIN + "&username=" +  username + "&password=" + password + "&token=" + token;

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

    /**
     * 更新用户信息
     * @param userId
     * @param username
     * @param userTruthName
     * @param userAge
     * @param userEmail
     * @param userSex
     * @param userQQ
     * @param userPhoto
     * @param listener
     */
    public void requestUserUpdateData(String userId, String username, String userTruthName,
                                      String userAge,  String userEmail, String userSex, String userQQ, String userPhoto,
                                      final OnCompleteDataListener<UserUpdateEntry> listener) {
        String url = ConfigUrl.USER_UPDATE_INFO + "&user_id" + userId;
        if (!username.isEmpty()) {
            url += "$username=" + username;
        }
        if (!username.isEmpty()) {
            url += "$username=" + username;
        }
        if (!userTruthName.isEmpty()) {
            url += "user_truth_name=" + userTruthName;
        }
        if (!userAge.isEmpty()) {
            url += "user_age=" + userAge;
        }
        if (!userEmail.isEmpty()) {
            url += "user_email=" + userEmail;
        }
        if (!userSex.isEmpty()) {
            url += "user_sex=" + userSex;
        }
        if (!userQQ.isEmpty()) {
            url += "user_qq=" + userQQ;
        }
        if (!userPhoto.isEmpty()) {
            url += "user_photo=" + userPhoto;
        }

        mGetBuilder.url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e("register", "用户信息更新：" + response);

                        UserUpdateEntry userUpdate = gson.fromJson(response, UserUpdateEntry.class);

                        if (listener != null) {
                            listener.onComplete(userUpdate);
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
