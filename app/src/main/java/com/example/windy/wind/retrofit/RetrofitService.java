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
        /**
         * date format: 20170912
         * would get the latest info if the date is the next day
         * @param date
         * @return
         */
        @GET("before/{date}")
        Observable<ZhihuDailyNews> getBeforeInfo(@Path("date") String date);
    }
}
