package com.example.windy.wind.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by windy on 2018/1/19.
 */

public class PreferencesHelper {
    public static String SP_TAG = "MY_SP";
    public static String NIGHT_MODE = "night-mode";

    private static PreferencesHelper INSTANCE = null;

    private SharedPreferences mSharePreferences;

    private PreferencesHelper(Context context){
        mSharePreferences = context.getSharedPreferences(SP_TAG, Context.MODE_PRIVATE);
    }

    public static PreferencesHelper getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new PreferencesHelper(context);
        }
        return INSTANCE;
    }

    public SharedPreferences.Editor getEditor(){
        return mSharePreferences.edit();
    }

    public boolean getBoolean(String key){
        return mSharePreferences.getBoolean(key, false);
    }
}
