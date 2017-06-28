package com.quanmin.sky.domain;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.quanmin.sky.R;
import com.quanmin.sky.bean.UserCenter;
import com.quanmin.sky.utils.ColorTextViewUtils;
import com.xl91.ui.XBlockMenuItem;
import com.xl91.ui.badgeview.QBadgeView;

/**
 * 个人中心管理View填充
 * Created by xl on 2017/2/18 0018.
 */

public class UCViewDomain implements View.OnClickListener {

    private Context context;
    private UserCenter userCenter;
    private final LayoutInflater inflater;

    public UCViewDomain(Context context, UserCenter userCenter) {
        this.context = context;
        this.userCenter = userCenter;

        inflater = LayoutInflater.from(context);
    }

    public View getUCView() {
        View view = inflater.inflate(R.layout.include_uc, null);

        XBlockMenuItem bmi_uc_live = (XBlockMenuItem) view.findViewById(R.id.bmi_uc_live);
        XBlockMenuItem bmi_uc_invitation = (XBlockMenuItem) view.findViewById(R.id.bmi_uc_invitation);
        XBlockMenuItem bmi_uc_collection = (XBlockMenuItem) view.findViewById(R.id.bmi_uc_collection);
        XBlockMenuItem bmi_uc_comment = (XBlockMenuItem) view.findViewById(R.id.bmi_uc_comment);
        XBlockMenuItem bmi_uc_msg = (XBlockMenuItem) view.findViewById(R.id.bmi_uc_msg);

        bmi_uc_live.setOnClickListener(this);
        bmi_uc_invitation.setOnClickListener(this);
        bmi_uc_collection.setOnClickListener(this);
        bmi_uc_comment.setOnClickListener(this);
        bmi_uc_msg.setOnClickListener(this);

        int sex = userCenter.sex;
        if (sex == 1) {
            bmi_uc_live.setMainText("他的Live");
            bmi_uc_invitation.setMainText("他的发帖");
            bmi_uc_collection.setMainText("他的收藏");
            bmi_uc_comment.setMainText("他的评论");
            bmi_uc_msg.setMainText("他的留言板");

        }else if (sex == 2){
            bmi_uc_live.setMainText("她的Live");
            bmi_uc_invitation.setMainText("她的发帖");
            bmi_uc_collection.setMainText("她的收藏");
            bmi_uc_comment.setMainText("她的评论");
            bmi_uc_msg.setMainText("她的留言板");

        }else {
            bmi_uc_live.setMainText("我的Live");
            bmi_uc_invitation.setMainText("我的发帖");
            bmi_uc_collection.setMainText("我的收藏");
            bmi_uc_comment.setMainText("我的评论");
            bmi_uc_msg.setMainText("我的留言板");
        }

        TextView msgMainTextView = bmi_uc_msg.getMainTextView();
        new QBadgeView(context)
                .bindTarget(msgMainTextView)
                .setGravityOffset(4, true)
                .setBadgePadding(4, true)
                .setBadgeNumber(-1);

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bmi_uc_live:
                Toast.makeText(context, "他的Live", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bmi_uc_invitation:
                Toast.makeText(context, "他的发帖", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bmi_uc_collection:
                Toast.makeText(context, "他的收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bmi_uc_comment:
                Toast.makeText(context, "他的评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bmi_uc_msg:
                Toast.makeText(context, "他的留言板", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
