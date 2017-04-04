package com.xiao91.sky.app;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Application
 * Created by xiao on 2017/3/10.
 */

public class TianGouApplication extends Application {

    private static TianGouApplication sSkyApplication;

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        sSkyApplication = this;

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    public static TianGouApplication getInstance() {
        return sSkyApplication;
    }

    /**
     * 在Activity和fragmen中使用
     * RefWatcher refWatcher = TianGouApplication.getRefWatcher(getActivity());
     * refWatcher.watch(this);
     * @param context
     * @return
     */
    public static RefWatcher getRefWatcher(Context context) {
        TianGouApplication application = (TianGouApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
