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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quanmin.sky.R;
import com.quanmin.sky.activity.base.BaseMvpActivity;
import com.quanmin.sky.bean.LoginEntry;
import com.quanmin.sky.mvp.presenter.LoginPresenter;
import com.quanmin.sky.mvp.view.LoginView;
import com.quanmin.sky.utils.DeviceUtils;
import com.quanmin.sky.utils.FixFocusedViewLeakUtils;
import com.quanmin.sky.utils.PhoneUtils;
import com.quanmin.sky.utils.SharedUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登陆
 * <p>
 *
 * @author xiao 017-01-07
 *         <p>
 */
public class LoginActivity extends BaseMvpActivity<LoginView, LoginPresenter> implements LoginView {

    @BindView(R.id.iv_login_close)
    ImageView ivLoginClose;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    @BindView(R.id.tv_login_wx)
    TextView tvLoginWx;
    @BindView(R.id.tv_login_qq)
    TextView tvLoginQq;
    @BindView(R.id.tv_login_sina)
    TextView tvLoginSina;
    private String phone;
    private String password;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void iniView(View baseView) {
        removeToolbar();

        tvLogin.setClickable(false);
        final Resources resources = getResources();

        etLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 11) {
                    tvLogin.setBackground(resources.getDrawable(R.drawable.login_tv_blue_bg_corner));
                    tvLogin.setTextColor(resources.getColor(R.color.color_white));
                    tvLogin.setClickable(true);
                } else {
                    tvLogin.setBackground(resources.getDrawable(R.drawable.login_tv_gray_bg_corner));
                    tvLogin.setClickable(false);
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
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    // 登录次数
    private int loginCount = 0;
    @Override
    public void onShowData(LoginEntry data) {
        String tokenServer = data.data.token;
        String userId = data.data.userId;
        int code = data.data.code;

        if (code == 0) {
            SharedUtils.setSharedString(this, SharedUtils.TOKEN, tokenServer);
            SharedUtils.setSharedString(this, SharedUtils.USER_ID, userId);
            // 关掉登录界面
            finish();
        }else if (code == -1){
            // TODO -1:手机号未注册或不正确：找回密码或去注册
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(R.string.prompt)
                    .setMessage(R.string.phone_error)
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(LoginActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton(R.string.go_register, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                            startActivity(intent);
                        }
                    });
            AlertDialog alertDialog = builder.create();
            Window window = alertDialog.getWindow();
            if (window != null) {
                window.setWindowAnimations(R.style.DialogStyle);
            }
            alertDialog.show();
        }else if (code == -2) {
            // -2：密码错误
            Toast.makeText(LoginActivity.this, R.string.password_error, Toast.LENGTH_SHORT).show();
        }else if (code == -3) {
            // -3：已在另一个安卓设备上登录
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(R.string.prompt)
                    .setMessage(R.string.offline)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(LoginActivity.this, R.string.ok, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(LoginActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            Window window = alertDialog.getWindow();
            if (window != null) {
                window.setWindowAnimations(R.style.DialogStyle);
            }
            alertDialog.show();
        }else if (code == -4) {
            // -4：保存token失败
            loginCount++;
            // 最多可以登录3次
            if (loginCount < 3) {
                mPresenter.onLoginRequest(phone, password, DeviceUtils.getDeviceId());
            }else {
                // 只显示一次toast
                if (loginCount > 1) {
                    showToastError(code);
                }
            }
        }
    }

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

    @OnClick({R.id.iv_login_close, R.id.tv_login_register, R.id.tv_login, R.id.tv_token_phone,
            R.id.tv_forget_pwd, R.id.tv_login_wx, R.id.tv_login_qq, R.id.tv_login_sina})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_login_close:
                finish();
                break;
            case R.id.tv_login_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_login:
                phone = etLoginUsername.getText().toString();
                password = etLoginPwd.getText().toString();

                if (!PhoneUtils.isMobile(phone)) {
                    Toast.makeText(this, R.string.toast_phone, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, R.string.toast_password, Toast.LENGTH_SHORT).show();
                    return;
                }

                String deviceId = DeviceUtils.getDeviceId();

                // 请求登录，第一次请求为false
                mPresenter.onLoginRequest(phone, password, deviceId);
                break;
            case R.id.tv_token_phone:
                Toast.makeText(this, R.string.login_quick, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_forget_pwd:
                Toast.makeText(this, R.string.forget_password, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_login_wx:
                Toast.makeText(this, R.string.login_weixin, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_login_qq:
                Toast.makeText(this, R.string.login_qq, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_login_sina:
                Toast.makeText(this, R.string.login_sina, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
