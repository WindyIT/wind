package com.example.windy.wind.database.cache;

/**
 * Created by windy on 2018/1/10.
 */

public interface ClearCacheInterface {
    interface ClearCacheCallback {
        void onNotCacheExist();

        void onOcurrError();

        void onClearSuccessfullt(int deletedItemsCnt);
    }

    void clearCache(ClearCacheCallback callback);
}
