package com.example.windy.wind.data.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.windy.wind.data.beans.ZhihuDailyItem;
import com.example.windy.wind.data.datasource.ZhihuDailyNewsDataSource;
import com.example.windy.wind.data.local.ZhihuDailyNewsLocalDs;
import com.example.windy.wind.data.remote.ZhihuDailyNewsRemoteDs;
import com.example.windy.wind.database.AppDatabase;
import com.example.windy.wind.database.DatabaseCreator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by windy on 2017/11/30.
 */

public class ZhihuDailyNewsRepository implements ZhihuDailyNewsDataSource{
    @NonNull
    private static  ZhihuDailyNewsRepository INSTANCE = null;

    @NonNull
    private final ZhihuDailyNewsRemoteDs mRemoteDs;

    @NonNull
    private final ZhihuDailyNewsLocalDs mLocalDs;

    private Map<Integer, ZhihuDailyItem> mNewsCache;

    public ZhihuDailyNewsRepository(@NonNull ZhihuDailyNewsRemoteDs mRemoteDs, @NonNull ZhihuDailyNewsLocalDs mLocalDs) {
        this.mRemoteDs = mRemoteDs;
        this.mLocalDs = mLocalDs;
    }

    public static ZhihuDailyNewsRepository getInstance(@NonNull ZhihuDailyNewsRemoteDs remoteDataSource,
                                                       @NonNull ZhihuDailyNewsLocalDs localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ZhihuDailyNewsRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    public void destroyInstance(){ INSTANCE = null; }

    //repository 实现 缓存/远程/本地 分支选择，读取逻辑在presenter实现
    @Override
    public void loadNews(final long date, final boolean isLoadMore, @NonNull final LoadZhihuDailyNewsCallback callback) {
       // 优先从高速缓存读取
        if (mNewsCache != null && !isLoadMore){
            callback.onNewsLoaded(new ArrayList<>(mNewsCache.values()));
            return;
        }

        //从网络请求数据，并保存到数据库
        mRemoteDs.loadNews(date, isLoadMore, new LoadZhihuDailyNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<ZhihuDailyItem> list) {
                //save into the cache
                refreshCache(isLoadMore, list);
                Log.v("LOAD_TAG", "Load More Succ...");
                callback.onNewsLoaded(new ArrayList<>(mNewsCache.values()));

                //save to database
                saveAll(list);
            }

            @Override
            public void onDataNotAvailable() {
                //网络请求出错，从本地数据库读取
                mLocalDs.loadNews(date, isLoadMore, new LoadZhihuDailyNewsCallback() {
                    @Override
                    public void onNewsLoaded(@NonNull List<ZhihuDailyItem> list) {
                        refreshCache(isLoadMore, list);
                        callback.onNewsLoaded(new ArrayList<>(mNewsCache.values()));
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void loadItemContent(int id, @NonNull LoadZhihuDailyItemCallback callback) {

    }

    @Override
    public void saveAll(List<ZhihuDailyItem> items) {
        //set timestamp
        for (ZhihuDailyItem item : items){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            item.setTimestamp(calendar.getTimeInMillis());
        }
        mLocalDs.saveAll(items);
    }

    private void refreshCache(boolean isLoadMore, List<ZhihuDailyItem> items){
        if (mNewsCache == null){
            mNewsCache = new LinkedHashMap<>();
        }

        if (!isLoadMore){
            //非加载更多，清空缓存
            mNewsCache.clear();
        }

        for (ZhihuDailyItem item : items){
            mNewsCache.put(item.getId(), item);
        }
    }
}
