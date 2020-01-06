package com.titi.remotbayi.utils;

import androidx.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.orhanobut.logger.LogStrategy;

public class AppController extends MultiDexApplication {

    LogStrategy logStrategy;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }


}
