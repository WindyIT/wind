package com.example.windy.wind.network;

import android.util.Log;

import com.example.windy.wind.beans.MrywData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by windy on 2017/11/23.
 */

public class NoOkRequest {
    private static volatile NoOkRequest noOkRequest;

    private NoOkRequest(){}

    public static NoOkRequest newInstance(){
        if (noOkRequest == null){
            synchronized (NoOkRequest.class){
                if (noOkRequest == null)
                    noOkRequest = new NoOkRequest();
            }
        }
        return noOkRequest;
    }

    public void getMrywContent(final String url, Observer<MrywData> observer){
        Observable<MrywData> observable = Observable.create(new Observable.OnSubscribe<MrywData>() {
             @Override
             public void call(Subscriber<? super MrywData> subscriber) {
                 String result = getData(url);
                 Gson gson = new Gson();
                 MrywData content = gson.fromJson(result, MrywData.class);
                 Log.v("NoOk!", "hehe" + content.getMrywContent().getContent());
                 subscriber.onNext(content);
                 subscriber.onCompleted();
             }
         });

        //rx io线程请求数据响应给主线程，并订阅
        observable.subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(observer);
    }

    private String getData(String baseUrl){
        String result = "";
        HttpURLConnection connection = null;
        try{
            URL url = new URL(baseUrl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET"); // 设置请求方式
            connection.setConnectTimeout(8000); // 设置连接服务端超时时间(毫秒)
            connection.setReadTimeout(8000); // 设置从服务端读取超时时间(毫秒)
            connection.setUseCaches(false); // 设置是否使用缓存, 默认true
            // 设置请求头信息
            // connection.setRequestProperty("Content-Type", "text/html"); // 设置请求中的媒体类型信息
            // connection.addRequestProperty("Connection", "Keep-Alive"); // 设置客户端与服务端连接类型
            connection.connect(); // 开始连接

            if (connection.getResponseCode() == 200) {
                result = inputStramToString(connection.getInputStream());
                Log.v("NoOk!", result);
            } else {
                Log.i("NoOk!","fail");
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    private String inputStramToString(InputStream in) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        StringBuilder result = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null){
            result.append(line);
        }

        reader.close();

        return result.toString();
    }
}
