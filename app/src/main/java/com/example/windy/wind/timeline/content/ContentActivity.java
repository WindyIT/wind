package com.example.windy.wind.timeline.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class ContentActivity extends AppCompatActivity {
    private ZhihuContentFragment mZhihuContentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        mZhihuContentFragment = new ZhihuContentFragment();

        getSupportFragmentManager().beginTransaction()
                                    .add(R.id.content_container, mZhihuContentFragment)
                                    .commit();

        new ContentPresenter(mZhihuContentFragment,
                                ZhihuDailyContentRepository.getInstance(ZhihuDailyContentRemoteDs.getInstance(), ZhihuDailyContentLocalDs.getInstance(this)));
    }

    public static void actionStart(Context context, int id, String title){
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra(ZhihuContentFragment.ZHIHU_NEWS_ID, id);
        intent.putExtra(ZhihuContentFragment.ZHIHU_NEWS_TITLE, title);
        context.startActivity(intent);
    }
}
