package com.xiao91.sky.bean;

import com.xiao91.sky.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 分享
 *
 * 2017-01-14
 *
 */

public class Share {
    public int resId;
    public String title;

    public Share(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }

    public static List<Share> getShareData() {
        List<Share> list = new ArrayList<>();
        list.add(new Share(R.mipmap.umeng_socialize_sina, "新浪"));
        list.add(new Share(R.mipmap.umeng_socialize_qq, "QQ"));
        list.add(new Share(R.mipmap.umeng_socialize_qzone, "QQ空间"));
        list.add(new Share(R.mipmap.umeng_socialize_tx, "腾讯微博"));
        list.add(new Share(R.mipmap.umeng_socialize_wechat, "微信"));
        list.add(new Share(R.mipmap.umeng_socialize_wxcircle, "微信朋友圈"));
        list.add(new Share(R.mipmap.umeng_socialize_fav, "微信收藏"));
        list.add(new Share(R.mipmap.umeng_socialize_gmail, "邮件"));
        list.add(new Share(R.mipmap.umeng_socialize_sms, "短信"));
        list.add(new Share(R.mipmap.umeng_socialize_more, "更多"));

        return list;
    }
}
