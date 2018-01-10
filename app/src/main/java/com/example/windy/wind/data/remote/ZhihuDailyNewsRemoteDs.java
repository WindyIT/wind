package com.example.windy.wind.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.windy.wind.data.beans.ZhihuDailyContent;
import com.example.windy.wind.data.beans.ZhihuDailyItem;
import com.example.windy.wind.data.beans.ZhihuDailyNews;
import com.example.windy.wind.data.datasource.ZhihuDailyNewsDataSource;
import com.example.windy.wind.network.RetrofitFactory;
import com.example.windy.wind.retrofit.RetrofitService;
import com.example.windy.wind.utils.DateFormatUtil;
import com.example.windy.wind.value.Api;

import java.util.List;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by windy on 2017/11/30.
 */

public class ZhihuDailyNewsRemoteDs implements ZhihuDailyNewsDataSource {
    private static ZhihuDailyNewsRemoteDs INSTANCE = null;

    private ZhihuDailyNewsRemoteDs(){}

    public static ZhihuDailyNewsRemoteDs getInstance(){
        if (INSTANCE == null){
            INSTANCE = new ZhihuDailyNewsRemoteDs();
        }
        return INSTANCE;
    }

    @Override
    public void loadNews(long date, boolean isLoadMore, @NonNull final LoadZhihuDailyNewsCallback callback) {
        Retrofit retrofit = RetrofitFactory.create().build(Api.ZHIHU_NEWS);
        retrofit.create(RetrofitService.ZhihuDailyService.class)
                .getBeforeInfo(DateFormatUtil.formatZhihuDailyDateLongToString(date))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhihuDailyNews>() {
                    @Override
                    public void onCompleted() {
                        Log.v("Mvp", "GetRemoteNewsDataSuccessfully");
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onDataNotAvailable();
                    }

                    @Override
                    public void onNext(ZhihuDailyNews zhihuDailyNews) {
                        callback.onNewsLoaded(zhihuDailyNews.getStories());
                    }
                });
    }

    @Override
    public void loadItemContent(int id, @NonNull final LoadZhihuDailyItemCallback callback) {
        // not required for remote data
    }

    @Override
    public void saveAll(List<ZhihuDailyItem> items) {

    }
}
