package com.example.windy.wind.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.windy.wind.data.beans.ZhihuDailyContent;
import com.example.windy.wind.data.datasource.ZhihuDailyContentDataSource;
import com.example.windy.wind.database.AppDatabase;
import com.example.windy.wind.database.DatabaseCreator;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by windy on 2017/11/30.
 */

public class ZhihuDailyContentLocalDs implements ZhihuDailyContentDataSource {
    private static ZhihuDailyContentLocalDs INSTANCE;
    private AppDatabase mDb;

    public ZhihuDailyContentLocalDs(Context context) {
        DatabaseCreator.getInstance().createDatabase(context);
        mDb = DatabaseCreator.getInstance().getDatabase();
        Log.v("Database", "Get database for content : " + (mDb == null));
    }

    public static ZhihuDailyContentLocalDs getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new ZhihuDailyContentLocalDs(context);
        }
        return INSTANCE;
    }

    @Override
    public void loadContent(int itemId, @NonNull final LoadZhihuDailyContentCallback callback) {
        getObservable(itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhihuDailyContent>() {
                    @Override
                    public void onCompleted() {
                        Log.v("Database", "Load content from DB suc");
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onDataNotAvailable();
                    }

                    @Override
                    public void onNext(ZhihuDailyContent content) {
                        callback.onContentLoaded(content);
                    }
                });
    }

    @Override
    public void saveAll(final ZhihuDailyContent content) {
       if (mDb == null) mDb = DatabaseCreator.getInstance().getDatabase();

       new Thread(new Runnable() {
           @Override
           public void run() {
               try{
                   mDb.beginTransaction();
                   mDb.zhihuDailyContentDao().insert(content);
                   mDb.setTransactionSuccessful();
               }catch (SQLiteException e){
                   e.printStackTrace();
               }finally {
                   mDb.endTransaction();
               }
           }
       }).start();
    }

    private Observable<ZhihuDailyContent> getObservable(final int itemId){
        return Observable.create(new Observable.OnSubscribe<ZhihuDailyContent>() {
            @Override
            public void call(Subscriber<? super ZhihuDailyContent> subscriber) {
                    subscriber.onNext(mDb.zhihuDailyContentDao().query(itemId));
                    subscriber.onCompleted();
            }
        });
    }
}
