package com.essay.loki;

import android.app.Application;

import com.essay.baselibrary.ExceptionCrashHandler;

/**
 * Created by Loki on 2017年10月11日.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //设置异常拦截写入本地
        ExceptionCrashHandler.getInstance().init(this);
    }


}
