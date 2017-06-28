package com.quanmin.sky.manager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * 检查权限，注意当权限被拒绝的回调处理
 *
 * @author xiao 2017-05-24
 */

public class PermissionManager {

    private Activity activity;

    // 大部分权限
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    // 录音权限
    private String[] RECORD_PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO
    };

    // 读取手机状态权限
    private String[] READ_PHONE_STATE = {
            Manifest.permission.READ_PHONE_STATE
    };

    // 读取手机内存
    private static final String[] EXTERNAL_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    // 定位权限
    private String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    public PermissionManager(Activity activity) {
        this.activity = activity;
    }

    public void initAllPermission() {
        // 检查运行时权限6.0
        int permission1 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission2 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission3 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        int permission4 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int permission5 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        int permission6 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        // 未授权则打开授权
        if (permission1 != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED
                || permission3 != PackageManager.PERMISSION_GRANTED || permission4 != PackageManager.PERMISSION_GRANTED
                || permission5 != PackageManager.PERMISSION_GRANTED || permission6 != PackageManager.PERMISSION_GRANTED) {
            int REQUEST_EXTERNAL_STORAGE = 1;

            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /**
     * 录音权限
     */
    public void initRecordPermission() {
        // 检查运行时权限6.0
        int permissionCamera = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio = ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        // 未授权则打开授权
        if (permissionCamera != PackageManager.PERMISSION_GRANTED || permission != PackageManager.PERMISSION_GRANTED
                || record_audio != PackageManager.PERMISSION_GRANTED) {
            int REQUEST_EXTERNAL_STORAGE = 1;

            ActivityCompat.requestPermissions(
                    activity,
                    RECORD_PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /**
     * 读取手机状态
     */
    public void initPhoneStatePermission() {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        // 未授权则打开授权
        if (permission != PackageManager.PERMISSION_GRANTED) {
            int REQUEST_EXTERNAL_STORAGE = 1;

            ActivityCompat.requestPermissions(
                    activity,
                    READ_PHONE_STATE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /**
     * 读取存储权限
     */
    public void initExternalStoragePermission() {
        int permission1 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission2 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // 未授权则打开授权
        if (permission1 != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED) {
            int REQUEST_EXTERNAL_STORAGE = 1;

            ActivityCompat.requestPermissions(
                    activity,
                    EXTERNAL_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /**
     * 定位权限
     */
    public void initLocationPermission() {
        int permission1 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission1 != PackageManager.PERMISSION_GRANTED) {
            int REQUEST_EXTERNAL_STORAGE = 1;

            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_LOCATION,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
