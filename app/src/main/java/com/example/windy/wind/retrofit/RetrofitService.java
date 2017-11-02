package com.example.windy.wind.retrofit;

import com.example.windy.wind.beans.ZhihuDailyNews;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by windy on 2017/9/29.
 */

public interface RetrofitService {
    interface ZhihuDailyService{
        @GET("latest")
        Observable<ZhihuDailyNews> getLatestInfo();

        /**
         * date format: 20170912
         * @param date
         * @return
         */
        @GET("before/{date}")
        Observable<ZhihuDailyNews> getBeforeInfo(@Path("date") String date);
    }
}
