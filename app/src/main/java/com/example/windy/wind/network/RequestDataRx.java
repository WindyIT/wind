package com.example.windy.wind.network;


import com.example.windy.wind.data.beans.MrywContent;
import com.example.windy.wind.data.beans.MrywData;
import com.example.windy.wind.data.beans.ZhihuDailyContent;
import com.example.windy.wind.data.beans.ZhihuDailyNews;
import com.example.windy.wind.retrofit.RetrofitService;
import com.example.windy.wind.utils.DateFormatUtil;
import com.example.windy.wind.value.Api;

import retrofit2.Retrofit;
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

    public void getZhihuContent(int id, Observer<ZhihuDailyContent> observer){
        Retrofit retrofit = RetrofitFactory.create().build(Api.ZHIHU_NEWS);

        retrofit.create(RetrofitService.ZhihuDailyService.class)
                .getNewsContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
