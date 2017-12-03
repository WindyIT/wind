package com.example.windy.wind.data.repository;

import android.support.annotation.NonNull;

import com.example.windy.wind.data.beans.ZhihuDailyItem;
import com.example.windy.wind.data.beans.ZhihuDailyNews;
import com.example.windy.wind.data.datasource.ZhihuDailyNewsDataSource;
import com.example.windy.wind.data.local.ZhihuDailyNewsLocalDs;
import com.example.windy.wind.data.remote.ZhihuDailyNewsRemoteDs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void loadNews(final long date, @NonNull final LoadZhihuDailyNewsCallback callback) {
        //优先从高速缓存读取
//        if (mNewsCache != null){
//            callback.onNewsLoaded(new ArrayList<ZhihuDailyItem>(mNewsCache.values()));
//            return;
//        }

        //从网络请求数据，并保存到数据库
        mRemoteDs.loadNews(date, new LoadZhihuDailyNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<ZhihuDailyItem> list) {
                //save into the cache
                refreshCache(false, list);
                callback.onNewsLoaded(new ArrayList<ZhihuDailyItem>(mNewsCache.values()));

                //save to database
            }

            @Override
            public void onDataNotAvailable() {
                //网络请求出错，从本地数据库读取
                mLocalDs.loadNews(date, new LoadZhihuDailyNewsCallback() {
                    @Override
                    public void onNewsLoaded(@NonNull List<ZhihuDailyItem> list) {
                        refreshCache(false, list);
                        callback.onNewsLoaded(new ArrayList<ZhihuDailyItem>(mNewsCache.values()));
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

    private void refreshCache(boolean clearCache, List<ZhihuDailyItem> items){
        if (mNewsCache == null){
            mNewsCache = new HashMap<>();
        }

        if (clearCache){
            mNewsCache.clear();
        }

        for (ZhihuDailyItem item : items){
            mNewsCache.put(item.getId(), item);
        }
    }
}
