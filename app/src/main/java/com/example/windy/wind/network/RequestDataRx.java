package com.example.windy.wind.network;


import com.example.windy.wind.beans.ZhihuDailyNews;
import com.example.windy.wind.retrofit.RetrofitService;

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

    public void getZhiLatestInfo(String api, Observer<ZhihuDailyNews> observer){
        Retrofit retrofit = RetrofitFactory.create().build(api);

       retrofit.create(RetrofitService.ZhihuDailyService.class)
               .getLatestInfo()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(observer);
    }
}
