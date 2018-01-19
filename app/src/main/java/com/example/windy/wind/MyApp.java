package com.example.windy.wind;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.example.windy.wind.data.preferences.PreferencesHelper;
import com.facebook.stetho.Stetho;

/**
 * Created by windy on 2018/1/10.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        boolean isNightMode = PreferencesHelper.getInstance(this).getBoolean(PreferencesHelper.NIGHT_MODE);
        AppCompatDelegate.setDefaultNightMode(isNightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }
}
