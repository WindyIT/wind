package com.example.windy.wind.timeline.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.windy.wind.R;
import com.example.windy.wind.customtabs.CustomTabsHelper;
import com.example.windy.wind.data.beans.ZhihuDailyContent;
import com.example.windy.wind.data.preferences.PreferencesHelper;

/**
 * Created by windy on 2018/1/18.
 */

public class ZhihuContentFragment extends Fragment
                                    implements ContentContract.View{

    private ContentContract.Presenter mPresenter;

    private WebView mWebView;
    private ImageView mImgView;
    private CollapsingToolbarLayout mToolbarLayout;
    private Toolbar mToolbar;

    public static final String ZHIHU_NEWS_ID  = "ID";
    public static final String ZHIHU_NEWS_TITLE = "TITLE";

    private int mItemId;
    private String mItemTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();

        setHasOptionsMenu(true);

        mItemId = intent.getIntExtra(ZHIHU_NEWS_ID, 0);
        mItemTitle = intent.getStringExtra(ZHIHU_NEWS_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhcontent, container, false);

        initViews(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null){
            mPresenter.loadContent(mItemId);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.content_menu_more, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home : {
                getActivity().onBackPressed();
            }break;
            case R.id.action_share :{
                mPresenter.share();
            }break;
            default : break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(ContentContract.Presenter presenter) {
        if (presenter != null)
            mPresenter = presenter;
    }

    @Override
    public void initViews(View view) {
        mWebView = (WebView)view.findViewById(R.id.web_view);
        mImgView = (ImageView)view.findViewById(R.id.img_view_content);
        mToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.collapse_layout);
        mToolbar = (Toolbar)view.findViewById(R.id.toolbar_content);

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
                CustomTabsHelper.openUrl(getContext(), url);
                return true;
            }
        });

        ContentActivity activity = (ContentActivity)getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        mToolbarLayout.setTitle(mItemTitle);
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
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

            if (PreferencesHelper.getInstance(getContext()).getBoolean(PreferencesHelper.NIGHT_MODE)){
                Log.v("NIGHT", "set night class");
                theme = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";
            }

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

    @Override
    public void showError() {

    }

    @Override
    public void showShare(String link) {
        try {
            Intent shareIntent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
            String shareText = "" + mItemTitle + " " + link;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, "分享至"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "分享出错", Toast.LENGTH_SHORT).show();
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
