package com.example.windy.wind.database.cache;

import android.os.AsyncTask;
import android.util.Log;

import com.example.windy.wind.data.beans.ZhihuDailyContent;
import com.example.windy.wind.data.beans.ZhihuDailyItem;
import com.example.windy.wind.database.AppDatabase;
import com.example.windy.wind.database.DatabaseCreator;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by windy on 2018/1/10.
 */
public class CacheManager implements ClearCacheInterface{
    @Nullable
    private AppDatabase mDb;
    private static CacheManager INSTANCE = null;

    private boolean hasCleared = false;

    private CacheManager() {
        mDb = DatabaseCreator.getInstance().getDatabase();
        Log.v("Database", "" + (mDb == null));
    }

    public static CacheManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new CacheManager();
        }

        return INSTANCE;
    }

    @Override
    public void clearCache(final ClearCacheCallback callback) {
        if (mDb == null) mDb = DatabaseCreator.getInstance().getDatabase();

        if (!hasCleared){
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... voids) {
                    int cnt = 0;
                    if (!hasCleared) {
                        List<ZhihuDailyItem> items = mDb.zhihuDailyItemDao().queryAll();

                        for (ZhihuDailyItem item : items) {
                            //删除列表元素缓存
                            cnt += mDb.zhihuDailyItemDao().delete(item);

                            //删除详细内容缓存
                            ZhihuDailyContent content = mDb.zhihuDailyContentDao().query(item.getId());
                            if (content != null) {
                                mDb.zhihuDailyContentDao().delete(content);
                            }
                        }
                        hasCleared = true;
                    }
                    return cnt;
                }

                @Override
                protected void onPostExecute(Integer integer) {
                    super.onPostExecute(integer);
                    if (integer.intValue() == 0) {
                        callback.onNotCacheExist();
                    } else {
                        callback.onClearSuccessfullt(integer.intValue());
                    }
                }
            }.execute();
        } else {
            callback.onNotCacheExist();
        }
    }
}
