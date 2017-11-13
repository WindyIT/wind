package com.example.windy.wind;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.windy.wind.beans.ZhihuDailyContent;
import com.example.windy.wind.customtabs.CustomTabsHelper;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_zhcontent);
        initView();
        setCollapsingToolbarLayoutTitle("test");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

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
               CustomTabsHelper.openUrl(getApplicationContext(), url);
                return true;
            }
        });


        mImgView = (ImageView)findViewById(R.id.img_view_content);
        mToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapse_layout);
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

    private void setCollapsingToolbarLayoutTitle(String title){
        mToolbarLayout.setTitle("Test");
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }
}
