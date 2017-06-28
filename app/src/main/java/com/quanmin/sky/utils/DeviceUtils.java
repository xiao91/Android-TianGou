package com.quanmin.sky.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.quanmin.sky.app.QuanMinApplication;

/**
 * 设备号工具类
 * Created by xiao on 2017/6/14.
 */

public class DeviceUtils {

    /**
     * 获取设备号id
     * @return deviceId 设备号
     */
    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) QuanMinApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("HardwareIds") String deviceId = tm.getDeviceId();
        if (!TextUtils.isEmpty(deviceId)) {
            Log.d("device", "设备id=" + deviceId);
            return deviceId;
        }

        return "";
    }
}
