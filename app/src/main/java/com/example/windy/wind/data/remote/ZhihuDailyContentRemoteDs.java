package com.example.windy.wind.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.windy.wind.data.beans.ZhihuDailyContent;
import com.example.windy.wind.data.datasource.ZhihuDailyContentDataSource;
import com.example.windy.wind.network.RetrofitFactory;
import com.example.windy.wind.retrofit.RetrofitService;
import com.example.windy.wind.value.Api;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by windy on 2017/11/30.
 */

public class ZhihuDailyContentRemoteDs implements ZhihuDailyContentDataSource {
    @Override
    public void loadContent(int itemId, @NonNull final LoadZhihuDailyContentCallback callback) {
        Retrofit retrofit = RetrofitFactory.create().build(Api.ZHIHU_NEWS);
        retrofit.create(RetrofitService.ZhihuDailyService.class)
                .getNewsContent(itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhihuDailyContent>() {
                    @Override
                    public void onCompleted() {
                        Log.v("Mvp", "GetRemoteContentDataSuccessfully");
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
}
