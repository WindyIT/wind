package com.example.windy.wind.timeline.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.windy.wind.R;
import com.example.windy.wind.customtabs.CustomTabsHelper;
import com.example.windy.wind.data.beans.ZhihuDailyContent;
import com.example.windy.wind.data.local.ZhihuDailyContentLocalDs;
import com.example.windy.wind.data.remote.ZhihuDailyContentRemoteDs;
import com.example.windy.wind.data.repository.ZhihuDailyContentRepository;

/**
 * Created by windy on 2017/11/11.
 */

public class ZhihuContentActivity extends AppCompatActivity
                                    implements ContentContract.View{
    private ContentContract.Presenter mPresenter;

    public static final String ZHIHU_NEWS_ID  = "ID";
    public static final String ZHIHU_NEWS_TITLE = "TITLE";

    private int itemId;
    private String itemTitle;

    private WebView mWebView;
    private ImageView mImgView;
    private CollapsingToolbarLayout mToolbarLayout;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhcontent);

        //get data from the intent
        Intent intent = getIntent();
        itemId = intent.getIntExtra(ZHIHU_NEWS_ID, 0);
        itemTitle = intent.getStringExtra(ZHIHU_NEWS_TITLE);

        initViews(null);

        new ContentPresenter(this,
                                ZhihuDailyContentRepository.getInstance(ZhihuDailyContentRemoteDs.getInstance(), ZhihuDailyContentLocalDs.getInstance(this)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.loadContent(itemId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setPresenter(ContentContract.Presenter presenter) {
        if (presenter != null)
            mPresenter = presenter;
    }

    @Override
    public void initViews(View view) {
        mWebView = (WebView) findViewById(R.id.web_view);
        mImgView = (ImageView)findViewById(R.id.img_view_content);
        mToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapse_layout);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_content);

        mWebView.setScrollbarFadingEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                CustomTabsHelper.openUrl(getApplicationContext(), url);
                return true;
            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        mToolbarLayout.setTitle(itemTitle);
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }

    @Override
    public void showError() {
        Toast.makeText(this, "数据加载出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showContent(ZhihuDailyContent content) {
        if (content != null) {
            String result = content.getBody();
            result = result.replace("<div class=\"img-place-holder\">", "");
            result = result.replace("<div class=\"headline\">", "");

            // result = result.replace("<img", "<img height=\"auto\" width=\"100%\"");
            //知乎日报api-4版本 .content-image 设置图片宽度自适应
            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";

            String theme = "<body className=\"\" onload=\"onLoaded()\">";

            result = "<!DOCTYPE html>\n"
                    + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                    + "<head>\n"
                    + "\t<meta charset=\"utf-8\" />"
                    //  + "\t<meta name=\"viewport\" content=\"width=device-width\">"
                    + css
                    + "\n</head>\n"
                    + theme
                    + result
                    + "</body></html>";

            mWebView.loadDataWithBaseURL("x-data://base", result,"text/html","utf-8",null);

            //加载图片
            setCover(content.getImage());
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

    public static void actionStart(Context context, int id, String title){
        Intent intent = new Intent(context, ZhihuContentActivity.class);
        intent.putExtra(ZHIHU_NEWS_ID, id);
        intent.putExtra(ZHIHU_NEWS_TITLE, title);
        context.startActivity(intent);
    }
}
