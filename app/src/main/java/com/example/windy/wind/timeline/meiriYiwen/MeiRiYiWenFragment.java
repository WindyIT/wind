package com.example.windy.wind.timeline.meiriYiwen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.windy.wind.R;
import com.example.windy.wind.beans.MrywContent;
import com.example.windy.wind.beans.MrywData;
import com.example.windy.wind.beans.MrywDate;
import com.example.windy.wind.customtabs.CustomTabsHelper;
import com.example.windy.wind.network.NoOkRequest;
import com.example.windy.wind.network.RequestDataRx;
import com.example.windy.wind.value.Api;

import java.util.Calendar;
import java.util.TimeZone;

import rx.Observer;

/**
 * Created by windy on 2017/11/14.
 */

public class MeiRiYiWenFragment extends Fragment {
    private WebView mWebView;

    private int mYear, mMonth, mDay;
    private NoOkRequest noOkRequest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mryw, container, false);

        initView(view);
        //   Toast.makeText(getContext(), "Create view", Toast.LENGTH_SHORT).show();
        Calendar c = Calendar.getInstance();
        c.set(mYear, mMonth, --mDay);

        noOkRequest = NoOkRequest.newInstance();
        noOkRequest.getMrywContent(Api.MRYW, new Observer<MrywData>() {
            @Override
            public void onCompleted() {
               // Toast.makeText(getContext(), "Get Data Sc", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MrywData mrywData) {
                Log.v("mryw", mrywData.getMrywContent().getContent());
                showContent(mrywData.getMrywContent());
            }
        });

        return view;
    }

    private void initView(View view){
        mWebView = (WebView) view.findViewById(R.id.web_view_mryw);
        mWebView.setScrollbarFadingEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                CustomTabsHelper.openUrl(getContext(), url);
                return true;
            }
        });
    }

    private void showContent(MrywContent mrywContent){
         if (mrywContent != null){
             String title = mrywContent.getTitle();
             String author = mrywContent.getAuthor();
             String article = mrywContent.getContent();

             String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/mryw.css\" type=\"text/css\">";
             String theme = "<body className=\"\" onload=\"\">";
             String divide = "<hr align=center width=100% color=#7D7D7D size=1>";

             String result = "<!DOCTYPE html>\n"
                     + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                     + "<head>\n"
                     + "\t<meta charset=\"utf-8\" />"
                     + css
                     + "\n</head>\n"
                     + theme
                     + "<div style=\"font: 22px bold; text-align:center\">" + title + "</div>"
                     + divide
                     + "<div style=\"font: 16px; color: #929292;  text-align:center\">" + author + "</div>"
                     + article
                     + "</body></html>";
             mWebView.loadDataWithBaseURL("x-data://base", result,"text/html","utf-8",null);
         }
    }
}
