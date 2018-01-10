package com.example.windy.wind;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by windy on 2018/1/10.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
