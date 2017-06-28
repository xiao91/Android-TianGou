package com.quanmin.sky.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.quanmin.sky.R;
import com.quanmin.sky.activity.base.BaseMvpActivity;
import com.quanmin.sky.bean.LoginEntry;
import com.quanmin.sky.bean.RegisterEntry;
import com.quanmin.sky.mvp.presenter.RegisterPresenter;
import com.quanmin.sky.mvp.view.RegisterViewImpl;
import com.quanmin.sky.utils.DeviceUtils;
import com.quanmin.sky.utils.FixFocusedViewLeakUtils;
import com.quanmin.sky.utils.MD5Utils;
import com.quanmin.sky.utils.PhoneUtils;
import com.quanmin.sky.utils.SharedUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 * <p>
 *
 * @author xiao 017-01-07
 *         <p>
 */
public class RegisterActivity extends BaseMvpActivity<RegisterViewImpl, RegisterPresenter> implements RegisterViewImpl {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    private String token;
    private String password;
    private String phone;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void iniView(View baseView) {
        removeToolbar();

        tvRegister.setClickable(false);
        final Resources resources = getResources();

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 11) {
                    tvRegister.setBackground(resources.getDrawable(R.drawable.login_tv_blue_bg_corner));
                    tvRegister.setTextColor(resources.getColor(R.color.color_white));
                    tvRegister.setClickable(true);
                } else {
                    tvRegister.setBackground(resources.getDrawable(R.drawable.login_tv_gray_bg_corner));
                    tvRegister.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {
        // 修复输入法的内存泄漏
        FixFocusedViewLeakUtils.fixFocusedViewLeak(getApplication());
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    // 注册次数
    private int registerCount = 0;
    /**
     * 注册成功
     *
     * @param data 对应bean数据
     */
    @Override
    public void onShowData(RegisterEntry data) {
        int code = data.data.code;

        if (code == 0) {
            String deviceId = DeviceUtils.getDeviceId();
            String md5 = phone + password + deviceId;
            token = MD5Utils.getMd5Value(md5);

            // 请求登录
            mPresenter.onRequestLogin(phone, password, token);
        }else if (code == -1){
            // TODO 等于-1，提示手机号码已被注册：找回密码或去登录
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(R.string.prompt)
                    .setMessage(R.string.register_again)
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(RegisterActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton(R.string.go_login, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNeutralButton(R.string.find_password, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(RegisterActivity.this, R.string.find_password, Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            Window window = alertDialog.getWindow();
            if (window != null) {
                window.setWindowAnimations(R.style.DialogStyle);
            }
            alertDialog.show();
        }else {
            registerCount++;
            if (registerCount < 3) {
                // 请求注册
                mPresenter.onRequestRegister(phone, password);
            }else {
                showToastError(code);
            }
        }
    }

    /**
     * 注册失败
     *
     * @param code 错误码
     */
    @Override
    public void onShowError(int code) {
        showToastError(code);
    }

    @Override
    public void onShowProgressbar() {
        setShowProgressbar();
    }

    @Override
    public void onHideProgressbar() {
        setHideProgressbar();
    }

    // 登录次数
    private int loginCount = 0;
    /**
     * 登录成功
     *
     * @param loginEntry 登录数据
     */
    @Override
    public void onLoginSuccess(LoginEntry loginEntry) {
        String tokenServer = loginEntry.data.token;
        String userId = loginEntry.data.userId;
        int code = loginEntry.data.code;

        if (code == 0) {
            SharedUtils.setSharedString(this, SharedUtils.TOKEN, tokenServer);
            SharedUtils.setSharedString(this, SharedUtils.USER_ID, userId);
            // 关掉注册界面
            finish();
        }else {
            loginCount++;
            // 最多可以登录3次
            if (loginCount < 3) {
                mPresenter.onRequestLogin(phone, password, token);
            }else {
                // 只显示一次toast
                if (loginCount > 1) {
                    showToastError(code);
                }
            }
        }
    }

    /**
     * 登录失败
     *
     * @param code 错误码
     */
    @Override
    public void onLoginFailure(int code) {
        showToastError(code);
    }

    @OnClick({R.id.tv_register, R.id.iv_register_close, R.id.tv_register_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_register_close:
                finish();
                break;
            case R.id.tv_register_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_register:
                phone = etUsername.getText().toString();
                password = etPwd.getText().toString();

                if (!PhoneUtils.isMobile(phone)) {
                    Toast.makeText(this, R.string.toast_phone, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, R.string.toast_password, Toast.LENGTH_SHORT).show();
                    return;
                }

                // 请求注册
                mPresenter.onRequestRegister(phone, password);
                break;
            default:
                break;
        }
    }


}
