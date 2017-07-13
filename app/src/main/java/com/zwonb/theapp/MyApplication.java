package com.zwonb.theapp;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * Created by zyb on 2017/7/11.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BGASwipeBackManager.getInstance().init(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
