package com.example.windy.wind.data.local;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.windy.wind.data.beans.ZhihuDailyItem;
import com.example.windy.wind.data.datasource.ZhihuDailyNewsDataSource;
import com.example.windy.wind.database.AppDatabase;
import com.example.windy.wind.database.DatabaseCreator;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by windy on 2017/11/30.
 */
public class ZhihuDailyNewsLocalDs implements ZhihuDailyNewsDataSource {
    private static ZhihuDailyNewsLocalDs INSTANCE = null;

    private AppDatabase mDatabase;

    private ZhihuDailyNewsLocalDs(Context context){
            //创建数据库
            Log.v("Database", "Start creating database");
            DatabaseCreator.getInstance().createDatabase(context);
            mDatabase = DatabaseCreator.getInstance().getDatabase();
    }

    public static ZhihuDailyNewsLocalDs getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new ZhihuDailyNewsLocalDs(context);
        }
        return INSTANCE;
    }

    @Override
    public void loadNews(final long date, boolean isLoadMore, @NonNull final LoadZhihuDailyNewsCallback callback) {
        if (mDatabase == null){
           mDatabase = DatabaseCreator.getInstance().getDatabase();
        }

//        if (mDatabase != null) {
//            Log.v("Database", "Start reading data from database");
//            new AsyncTask<Void, Void, List<ZhihuDailyItem>>() {
//                @Override
//                protected List<ZhihuDailyItem> doInBackground(Void... voids) {
//                    return mDatabase.zhihuDailyItemDao().queryByDate(date);
//                }
//
//                @Override
//                protected void onPostExecute(List<ZhihuDailyItem> items) {
//                    super.onPostExecute(items);
//                    if (items == null || items.size() == 0){
//                        Log.v("Database", "No item loaded");
//                        callback.onDataNotAvailable();
//                    }
//                    else {
//                        callback.onNewsLoaded(items);
//                    }
//                }
//            }.execute();
//        }
        //当未创建database时 获取到的db为Null
        //if (mDatabase != null){
            //从数据库中读取数据
            getObservable(mDatabase, date)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<ZhihuDailyItem>>() {
                        @Override
                        public void onCompleted() {
                            Log.v("Database", "Read From Db Successfully");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.v("Database", "Read From Db Occur Error");
                            e.printStackTrace();
                            callback.onDataNotAvailable();
                        }

                        @Override
                        public void onNext(List<ZhihuDailyItem> items) {
                            if (items == null || items.size() == 0){
                                Log.v("Database", "No item loaded");
                                callback.onDataNotAvailable();
                            }else {
                                callback.onNewsLoaded(items);
                            }
                        }
                    });
//        }else {
//                callback.onDataNotAvailable();
//        }
    }

    @Override
    public void loadItemContent(int id, @NonNull LoadZhihuDailyItemCallback callback) {

    }

    @Override
    public void saveAll(final List<ZhihuDailyItem> items) {
        if (mDatabase == null) mDatabase = DatabaseCreator.getInstance().getDatabase();

        if (mDatabase != null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mDatabase.beginTransaction();
                    try {
                       // Log.v("ITEM", items.get(0).toString()); items is available
                        mDatabase.zhihuDailyItemDao().insertAll(items);
                        mDatabase.setTransactionSuccessful();
                        Log.v("Database", "Save data Succesfully");
                    }finally {
                        mDatabase.endTransaction();
                    }
                }
            }).start();
        }
    }

    private Observable<List<ZhihuDailyItem>> getObservable(final AppDatabase database, final long date){
         return Observable.create(new Observable.OnSubscribe<List<ZhihuDailyItem>>() {
             @Override
             public void call(Subscriber<? super List<ZhihuDailyItem>> subscriber) {
                    subscriber.onNext(database.zhihuDailyItemDao().queryByDate(date));
                    subscriber.onCompleted();
             }
         });
    }
}
