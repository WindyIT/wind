package com.example.windy.wind.data.datasource;


import android.support.annotation.NonNull;

import com.example.windy.wind.data.beans.ZhihuDailyContent;
import com.example.windy.wind.data.beans.ZhihuDailyItem;
import com.example.windy.wind.data.beans.ZhihuDailyNews;

import java.util.List;

/**
 * Created by windy on 2017/11/30.
 */

public interface ZhihuDailyNewsDataSource {
    interface LoadZhihuDailyNewsCallback{

        void onNewsLoaded(@NonNull List<ZhihuDailyItem> list);

        void onDataNotAvailable();

    }

    interface LoadZhihuDailyItemCallback{

        void onItemLoaded(@NonNull ZhihuDailyNews item);

        void onDataNotAvailable();

    }

    void loadNews(long date, boolean isLoadMore, @NonNull LoadZhihuDailyNewsCallback callback);

    void loadItemContent(int id, @NonNull LoadZhihuDailyItemCallback callback);

    void saveAll(List<ZhihuDailyItem> items);
}
