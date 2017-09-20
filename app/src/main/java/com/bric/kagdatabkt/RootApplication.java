package com.bric.kagdatabkt;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by joyopeng on 17-9-18.
 */

public class RootApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
        JPushInterface.onResume(this);
        SDKInitializer.initialize(this);
    }

}
