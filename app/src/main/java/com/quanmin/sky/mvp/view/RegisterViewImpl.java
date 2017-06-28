package com.quanmin.sky.mvp.view;

import com.quanmin.sky.bean.LoginEntry;
import com.quanmin.sky.bean.RegisterEntry;
import com.quanmin.sky.mvp.base.IBaseMvpView;

/**
 * 注册
 * Created by xiao on 2017/1/3 0003.
 */

public interface RegisterViewImpl extends IBaseMvpView<RegisterEntry> {

    /**
     * 登录成功
     * @param loginEntry 登录数据
     */
    void onLoginSuccess(LoginEntry loginEntry);

    /**
     * 登录失败
     * @param code 错误码
     */
    void onLoginFailure(int code);

}
