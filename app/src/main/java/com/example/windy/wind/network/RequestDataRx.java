package com.example.windy.wind.network;


import com.example.windy.wind.beans.ZhihuDailyNews;
import com.example.windy.wind.retrofit.RetrofitService;
import com.example.windy.wind.utils.DateFormatUtil;
import com.example.windy.wind.value.Api;

import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by windy on 2017/11/5.
 */

public class RequestDataRx {
    public static RequestDataRx newInstance(){
        return new RequestDataRx();
    }

    public void getZhihuInfo(long date, Observer<ZhihuDailyNews> observer){
        Retrofit retrofit = RetrofitFactory.create().build(Api.ZHIHU_NEWS);
       retrofit.create(RetrofitService.ZhihuDailyService.class)
               .getBeforeInfo(DateFormatUtil.formatZhihuDailyDateLongToString(date))
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(observer);
    }
}
