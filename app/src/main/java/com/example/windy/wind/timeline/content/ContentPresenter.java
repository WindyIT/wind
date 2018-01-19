package com.example.windy.wind.timeline.content;

import android.support.annotation.NonNull;

import com.example.windy.wind.data.beans.ZhihuDailyContent;
import com.example.windy.wind.data.datasource.ZhihuDailyContentDataSource;
import com.example.windy.wind.data.repository.ZhihuDailyContentRepository;

/**
 * Created by windy on 2018/1/10.
 */

public class ContentPresenter implements ContentContract.Presenter {
    @NonNull
    private final ContentContract.View mView;

    @NonNull
    private ZhihuDailyContentRepository mRepository;

    private String shareUrl;

    public ContentPresenter(@NonNull ContentContract.View mView, @NonNull ZhihuDailyContentRepository mRepository) {
        this.mView = mView;
        mView.setPresenter(this);
        this.mRepository = mRepository;
    }

    @Override
    public void loadContent(int itemId) {
        mRepository.loadContent(itemId, new ZhihuDailyContentDataSource.LoadZhihuDailyContentCallback() {
            @Override
            public void onContentLoaded(@NonNull ZhihuDailyContent content) {
                shareUrl = content.getShare_url();
                mView.showContent(content);
            }

            @Override
            public void onDataNotAvailable() {
                mView.showError();
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void share() {
        mView.showShare(shareUrl);
    }
}
