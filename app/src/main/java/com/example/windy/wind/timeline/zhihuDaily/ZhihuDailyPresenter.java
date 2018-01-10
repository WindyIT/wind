package com.example.windy.wind.timeline.zhihuDaily;

import android.support.annotation.NonNull;

import com.example.windy.wind.data.beans.ZhihuDailyItem;
import com.example.windy.wind.data.datasource.ZhihuDailyNewsDataSource;
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
    public void loadPosts(long date, boolean isLoadMore) {
        mView.showLoadingIndicator(true);
        mRepository.loadNews(date, isLoadMore, new ZhihuDailyNewsDataSource.LoadZhihuDailyNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<ZhihuDailyItem> list) {
                if (mView.isActive()) {
                    mView.showResults(list);
                }
                mView.showLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable() {
                mView.showLoadingIndicator(false);
                mView.showError();
            }
        });
    }
}
