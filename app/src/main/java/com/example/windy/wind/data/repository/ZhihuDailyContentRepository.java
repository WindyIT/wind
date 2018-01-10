package com.example.windy.wind.data.repository;

import android.support.annotation.NonNull;

import com.example.windy.wind.data.beans.ZhihuDailyContent;
import com.example.windy.wind.data.datasource.ZhihuDailyContentDataSource;
import com.example.windy.wind.data.local.ZhihuDailyContentLocalDs;
import com.example.windy.wind.data.remote.ZhihuDailyContentRemoteDs;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by windy on 2017/11/30.
 */
public class ZhihuDailyContentRepository implements ZhihuDailyContentDataSource{
    @NonNull
    private static ZhihuDailyContentRepository INSTANCE = null;

    @NonNull
    private ZhihuDailyContentRemoteDs  mRemoteDs;

    @NonNull
    private ZhihuDailyContentLocalDs mLocalDs;

    Map<Integer, ZhihuDailyContent> mContentCache;

    public ZhihuDailyContentRepository(@NonNull ZhihuDailyContentRemoteDs mRemoteDs, @NonNull ZhihuDailyContentLocalDs mLocalDs) {
        this.mRemoteDs = mRemoteDs;
        this.mLocalDs = mLocalDs;
    }

    public static ZhihuDailyContentRepository getInstance(@NonNull ZhihuDailyContentRemoteDs mRemoteDs, @NonNull ZhihuDailyContentLocalDs mLocalDs){
        if (INSTANCE == null){
            INSTANCE = new ZhihuDailyContentRepository(mRemoteDs, mLocalDs);
        }
        return INSTANCE;
    }

    @Override
    public void loadContent(final int itemId, @NonNull final LoadZhihuDailyContentCallback callback) {
         //优先从高速缓存读取
         if (mContentCache != null && mContentCache.get(itemId) != null){
             callback.onContentLoaded(mContentCache.get(itemId));
         }

         //从网络请求数据
         mRemoteDs.loadContent(itemId, new LoadZhihuDailyContentCallback() {
             @Override
             public void onContentLoaded(@NonNull ZhihuDailyContent content) {
                 callback.onContentLoaded(content);
                 //刷新高速缓存
                 refreshCache(content);
                 //写入硬盘缓存
                 saveAll(content);
             }

             @Override
             public void onDataNotAvailable() {
                //从本地请求数据
                 mLocalDs.loadContent(itemId, new LoadZhihuDailyContentCallback() {
                     @Override
                     public void onContentLoaded(@NonNull ZhihuDailyContent content) {
                         callback.onContentLoaded(content);
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
    public void saveAll(ZhihuDailyContent content) {
        mLocalDs.saveAll(content);
    }

    private void refreshCache(ZhihuDailyContent content){
        if (mContentCache == null) mContentCache = new Hashtable<>();

        mContentCache.put(content.getId(), content);
    }
}
