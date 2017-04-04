package com.xiao91.sky.mvp.model;

import android.util.Log;

import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;
import com.xiao91.sky.bean.UserLoginEntry;
import com.xiao91.sky.bean.UserRegisterEntry;
import com.xiao91.sky.bean.UserUpdateEntry;
import com.xiao91.sky.utils.TGUrls;

/**
 * 用户登录
 * Created by xiao on 2017/1/3 0003.
 *
 */

public class UserModel {

    private MyOkHttp okHttp;

    public UserModel() {
        okHttp = new MyOkHttp();
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param listener
     */
    public void requestData(String username, String password, final OnCompleteDataListener<UserLoginEntry> listener) {
        String url = TGUrls.USER_LOGIN + "&username=" +  username + "&password=" + password;

        okHttp.get()
                .url(url)
                .tag(this)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e("Login", "数据：" + response);

                        Gson gson = new Gson();
                        UserLoginEntry userLoginEntry = gson.fromJson(response, UserLoginEntry.class);

                        if (listener != null) {
                            listener.onComplete(userLoginEntry);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (listener != null) {
                            listener.onError(500);
                        }
                    }
                });
    }


    /**
     * 手机号注册
     * @param phone
     * @param password
     * @param listener
     */
    public void requestRegisterData(String phone, String password, final OnCompleteDataListener<UserRegisterEntry> listener) {
        String url = TGUrls.USER_REGISTER_PHONE + "&phone=" +  phone + "&password=" + password;

        okHttp.get()
                .url(url)
                .tag(this)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e("register", "注册：" + response);

                        Gson gson = new Gson();
                        UserRegisterEntry userRegisterEntry = gson.fromJson(response, UserRegisterEntry.class);

                        if (listener != null) {
                            listener.onComplete(userRegisterEntry);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (listener != null) {
                            listener.onError(500);
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
        String url = TGUrls.USER_REGISTER_PHONE + "&user_id" + userId;
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

        okHttp.get()
                .url(url)
                .tag(this)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e("register", "用户信息更新：" + response);

                        Gson gson = new Gson();
                        UserUpdateEntry userUpdate = gson.fromJson(response, UserUpdateEntry.class);

                        if (listener != null) {
                            listener.onComplete(userUpdate);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (listener != null) {
                            listener.onError(500);
                        }
                    }
                });
    }
}
