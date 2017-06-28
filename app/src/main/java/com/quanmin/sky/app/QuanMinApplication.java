package com.quanmin.sky.app;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Application
 * Created by xiao on 2017/3/10.
 */

public class QuanMinApplication extends Application {

    private static QuanMinApplication sSkyApplication;

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        sSkyApplication = this;

//        initOkGo();
        initLeakCanary();
    }

    public static QuanMinApplication getInstance() {
        return sSkyApplication;
    }

    /**
     * LeakCanary内存泄漏检测工具安装
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    /**
     * 在Activity和fragmen中使用
     * RefWatcher refWatcher = MVPApplication.getRefWatcher(getActivity());
     * refWatcher.watch(this);
     *
     * @param context
     * @return
     */
    public static RefWatcher getRefWatcher(Context context) {
        QuanMinApplication application = (QuanMinApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
