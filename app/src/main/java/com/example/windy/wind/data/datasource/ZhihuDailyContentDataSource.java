package com.example.windy.wind.data.datasource;

import android.support.annotation.NonNull;

import com.example.windy.wind.data.beans.ZhihuDailyContent;

/**
 * Created by windy on 2017/11/30.
 */

public interface ZhihuDailyContentDataSource {
    interface LoadZhihuDailyContentCallback{

        void onContentLoaded(@NonNull ZhihuDailyContent content);

        void onDataNotAvailable();

    }

    void loadContent(int itemId, @NonNull LoadZhihuDailyContentCallback callback);

    void saveAll(ZhihuDailyContent content);
}
