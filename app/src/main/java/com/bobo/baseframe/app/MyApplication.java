package com.bobo.baseframe.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;
import com.bobo.baseframe.widget.utils.ResUtils;
import com.lazy.library.logging.Builder;
import com.lazy.library.logging.Logcat;
import com.tencent.mmkv.MMKV;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static MyApplication mInstance;
    private static Context appContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        //非默认值
        if (config.fontScale != 1) {
            getResources();
        }
        super.onConfigurationChanged(config);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        appContext = getApplicationContext();
        closeAndroidPDialog();
        initialize();
        // 初始化MultiDex
        MultiDex.install(this);
        // 初始化 MMKV
        MMKV.initialize(this);
    }


    /**
     * 设置 app 字体不随系统字体设置改变
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res != null) {
            Configuration config = res.getConfiguration();
            if (config != null && config.fontScale != 1.0f) {
                config.fontScale = 1.0f;
                res.updateConfiguration(config, res.getDisplayMetrics());
            }
        }
        return res;
    }


    private void initialize() {
        ResUtils.inject(mInstance);
        initLogCat();
    }

    private void initLogCat() {
        Builder builder = Logcat.newBuilder();
        builder.topLevelTag(Constant.LOG_GLOBAL_TAG);
        builder.logCatLogLevel(Logcat.SHOW_ALL_LOG);
        Logcat.initialize(this, builder.build());
    }


    public static MyApplication getInstance() {
        return mInstance;
    }
    public static Context getAppContext() {
        return appContext;
    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        AppManager.addActivity(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        AppManager.finishActivity(activity);
    }
}
