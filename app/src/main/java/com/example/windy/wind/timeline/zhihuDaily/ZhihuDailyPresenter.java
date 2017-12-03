package com.example.windy.wind.timeline.zhihuDaily;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.windy.wind.data.beans.ZhihuDailyItem;
import com.example.windy.wind.data.datasource.ZhihuDailyNewsDataSource;
import com.example.windy.wind.data.local.ZhihuDailyNewsLocalDs;
import com.example.windy.wind.data.remote.ZhihuDailyNewsRemoteDs;
import com.example.windy.wind.data.repository.ZhihuDailyNewsRepository;

import java.util.List;

/**
 * Created by windy on 2017/9/29.
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {
    @NonNull
    private final ZhihuDailyContract.View mView;

    private ZhihuDailyNewsRepository mRepository;

    public ZhihuDailyPresenter(@NonNull ZhihuDailyContract.View view, @NonNull ZhihuDailyNewsRepository repository){
        mView = view;
        mView.setPresenter(this);
        mRepository = repository;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadPosts(long date, boolean clearing) {
        mView.showLoadingIndicator(true);
        mRepository.loadNews(date, new ZhihuDailyNewsDataSource.LoadZhihuDailyNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<ZhihuDailyItem> list) {
                if (mView.isActive()) {
                    mView.showResults(list);
                }
                mView.showLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable() {
                mView.showError();
            }
        });
    }
}
