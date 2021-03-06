package com.example.windy.wind.retrofit;

import com.example.windy.wind.data.beans.MrywContent;
import com.example.windy.wind.data.beans.ZhihuDailyContent;
import com.example.windy.wind.data.beans.ZhihuDailyItem;
import com.example.windy.wind.data.beans.ZhihuDailyNews;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
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

        @GET("{id}")
        Observable<ZhihuDailyContent> getNewsContent(@Path("id") int id);
    }

    interface MrywService{
        /**
         * date format: 20171115
         * @param date
         * @return
         */
        @GET("article/day")
        Observable<MrywContent> getArticle(@Query("date") String date);
    }
}
