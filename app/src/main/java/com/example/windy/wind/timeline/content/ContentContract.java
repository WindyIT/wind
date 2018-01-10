package com.example.windy.wind.timeline.content;

import com.example.windy.wind.BasePresenter;
import com.example.windy.wind.BaseView;
import com.example.windy.wind.data.beans.ZhihuDailyContent;
import com.example.windy.wind.data.datasource.ZhihuDailyContentDataSource;

/**
 * Created by windy on 2018/1/10.
 */

public interface ContentContract {
    interface View extends BaseView<Presenter>{

        void showContent(ZhihuDailyContent content);

        void showError();
    }

    interface Presenter extends BasePresenter{
        void loadContent(int itemId);

        void share();

        void back();
    }
}
