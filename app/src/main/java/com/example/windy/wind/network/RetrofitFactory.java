package com.example.windy.wind.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by windy on 2017/9/29.
 */

public class RetrofitFactory {
    private volatile static RetrofitFactory retrofitFactory;

    private RetrofitFactory(){}

    public static RetrofitFactory create(){
        if (retrofitFactory == null){
            synchronized (RetrofitFactory.class){
                if (retrofitFactory == null)
                    retrofitFactory = new RetrofitFactory();
            }
        }
        return retrofitFactory;
    }

    public Retrofit build(String url){
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(buildClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient buildClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                //.addInterceptor(new GzipRequestInterceptor())
                .build();
    }
}
