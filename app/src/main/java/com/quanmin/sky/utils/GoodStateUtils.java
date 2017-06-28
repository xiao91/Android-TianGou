package com.quanmin.sky.utils;

import android.text.TextUtils;

import com.quanmin.sky.app.QuanMinApplication;
import com.quanmin.sky.bean.ContentEntry;

/**
 * 点赞的状态
 * Created by xiao on 2017/6/21.
 */

public class GoodStateUtils {

    /**
     * 是否点过赞，如果登录有userId，没有登录，以设备号判断
     * @param content
     * @return
     */
    public static boolean isGoodState (ContentEntry.ContentData.Content content) {
        boolean contains = false;
        // 如果登录，就会有userId
        String userId = SharedUtils.getSharedString(QuanMinApplication.getInstance(), SharedUtils.USER_ID, "");
        if (TextUtils.isEmpty(userId)) {
            String deviceId = DeviceUtils.getDeviceId();
            String devicesCode = content.gDids;
            if (!TextUtils.isEmpty(devicesCode)) {
                contains = devicesCode.contains(deviceId);
            }
        }else {
            String usersId = content.gUids;
            if (!TextUtils.isEmpty(usersId)) {
                contains = usersId.contains(userId);
            }
        }
        return contains;
    }

    /**
     * 是否点过踩，如果登录有userId，没有登录，以设备号判断
     * @param content
     * @return
     */
    public static boolean isBadState (ContentEntry.ContentData.Content content) {
        boolean contains = false;
        // 如果登录，就会有userId
        String userId = SharedUtils.getSharedString(QuanMinApplication.getInstance(), SharedUtils.USER_ID, "");
        if (TextUtils.isEmpty(userId)) {
            String deviceId = DeviceUtils.getDeviceId();
            String devicesCode = content.bDids;
            if (!TextUtils.isEmpty(devicesCode)) {
                contains = devicesCode.contains(deviceId);
            }
        }else {
            String usersId = content.bUids;
            if (!TextUtils.isEmpty(usersId)) {
                contains = usersId.contains(userId);
            }
        }
        return contains;
    }
}
