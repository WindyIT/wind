package com.example.windy.wind;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.windy.wind.beans.ZhihuDailyContent;
import com.example.windy.wind.network.RequestDataRx;

import rx.Observer;

/**
 * Created by windy on 2017/11/11.
 */

public class ZhihuContentActivity extends AppCompatActivity {
    public static final String ZHIHU_NEWS_ID  = "ID";
    private WebView mWebView;
    private ImageView mImgView;
    private CollapsingToolbarLayout mToolbarLayout;
    private Toolbar toolbar;
    private NestedScrollView mScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhcontent);
        initView();
        Intent intent = getIntent();
        int id = intent.getIntExtra(ZHIHU_NEWS_ID, 0);

        RequestDataRx requestDataRx = RequestDataRx.newInstance();
        requestDataRx.getZhihuContent(id, new Observer<ZhihuDailyContent>() {
            @Override
            public void onCompleted() {
               // Toast.makeText(ZhihuContentActivity.this, "Show Data Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ZhihuDailyContent zhihuDailyContent) {
                Log.v("Content", zhihuDailyContent.getBody());
                showContent(zhihuDailyContent);
                setCover(zhihuDailyContent.getImage());
            }
        });
    }

    private void initView(){
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.setScrollbarFadingEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               view.loadUrl(url);
                return true;
            }
        });


        mImgView = (ImageView)findViewById(R.id.img_view_content);
    }

    private void showContent(ZhihuDailyContent content){
        if (content.getBody() != null) {
            String result = content.getBody();
            result = result.replace("<div class=\"img-place-holder\">", "");
            result = result.replace("<div class=\"headline\">", "");

            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";

            String theme = "<body className=\"\" onload=\"onLoaded()\">";

            result = "<!DOCTYPE html>\n"
                    + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                    + "<head>\n"
                    + "\t<meta charset=\"utf-8\" />"
                    + css
                    + "\n</head>\n"
                    + theme
                    + result
                    + "</body></html>";

            mWebView.loadDataWithBaseURL("x-data://base", result,"text/html","utf-8",null);
        } else {
            mWebView.loadDataWithBaseURL("x-data://base", content.getShare_url(),"text/html","utf-8",null);
        }
    }

    private void setCover(String url){
        if (url != null){
            Glide.with(this)
                    .load(url)
                    .asBitmap()
                    .placeholder(R.drawable.ic_nav_header)
                    .centerCrop()
                    .error(R.drawable.ic_nav_header)
                    .into(mImgView);
        }else {
            mImgView.setImageResource(R.drawable.ic_nav_header);
        }

    }
}
