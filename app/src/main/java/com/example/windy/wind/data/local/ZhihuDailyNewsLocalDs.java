package com.example.windy.wind.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.windy.wind.data.datasource.ZhihuDailyNewsDataSource;

/**
 * Created by windy on 2017/11/30.
 */

public class ZhihuDailyNewsLocalDs implements ZhihuDailyNewsDataSource {
    private static ZhihuDailyNewsLocalDs INSTANCE = null;

    private ZhihuDailyNewsLocalDs(Context context){

    }

    public static ZhihuDailyNewsLocalDs getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new ZhihuDailyNewsLocalDs(context);
        }
        return INSTANCE;
    }

    @Override
    public void loadNews(long date, @NonNull LoadZhihuDailyNewsCallback callback) {

    }

    @Override
    public void loadItemContent(int id, @NonNull LoadZhihuDailyItemCallback callback) {

    }
}
