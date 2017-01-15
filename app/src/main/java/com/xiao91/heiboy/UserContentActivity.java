package com.xiao91.heiboy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserContentActivity extends AppCompatActivity {

    @BindView(R.id.root_iv_back)
    ImageView rootIvBack;
    @BindView(R.id.root_tv_desc)
    TextView rootTvDesc;
    @BindView(R.id.root_tv_title)
    TextView rootTvTitle;
    @BindView(R.id.include_rl_title)
    RelativeLayout includeRlTitle;
    @BindView(R.id.me_iv_login_photo)
    ImageView meIvLoginPhoto;
    @BindView(R.id.user_tv_address)
    TextView userTvAddress;
    @BindView(R.id.user_tv_add_care)
    TextView userTvAddCare;
    @BindView(R.id.user_tv_send_msg)
    TextView userTvSendMsg;
    @BindView(R.id.tv_fensi)
    TextView tvFensi;
    @BindView(R.id.tv_guanzhu)
    TextView tvGuanzhu;
    @BindView(R.id.tv_jifen)
    TextView tvJifen;
    @BindView(R.id.activity_user_content)
    LinearLayout activityUserContent;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_content);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        int white = getResources().getColor(R.color.root_bg);
        rootTvTitle.setText("用户名");
        rootTvTitle.setTextColor(white);
        rootTvDesc.setText(R.string.tousu);
        rootTvDesc.setTextColor(white);
        rootIvBack.setImageResource(R.mipmap.back_white);
    }

    @OnClick({R.id.root_iv_back, R.id.root_tv_desc, R.id.me_iv_login_photo, R.id.user_tv_address, R.id.user_tv_add_care, R.id.user_tv_send_msg, R.id.tv_fensi, R.id.tv_guanzhu, R.id.tv_jifen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.root_iv_back:
                finish();
                break;
            case R.id.root_tv_desc:
                Toast.makeText(this, "投诉", Toast.LENGTH_SHORT).show();
                break;
            case R.id.me_iv_login_photo:
                Toast.makeText(this, "用户头像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user_tv_address:
                Toast.makeText(this, "地址", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user_tv_add_care:
                Toast.makeText(this, "添加关注", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user_tv_send_msg:
                Toast.makeText(this, "发私信", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_fensi:
                Toast.makeText(this, "粉丝", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_guanzhu:
                Toast.makeText(this, "关注", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_jifen:
                Toast.makeText(this, "积分", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
