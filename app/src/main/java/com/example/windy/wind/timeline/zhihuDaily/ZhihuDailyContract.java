package com.example.windy.wind.timeline.zhihuDaily;

import com.example.windy.wind.BasePresenter;
import com.example.windy.wind.BaseView;
import com.example.windy.wind.data.beans.ZhihuDailyItem;

import java.util.List;

/**
 * Created by windy on 2017/9/29.
 */

public class ZhihuDailyContract {
    interface View extends BaseView<Presenter>{
        // Fragment是否活跃
        boolean isActive();
        // 显示加载或其他类型的错误
        void showError();
        // 是否显示加载动画
        void showLoadingIndicator(boolean flag);
        // 成功获取到数据后，在界面中显示
        void showResults(List<ZhihuDailyItem> list);
    }

    interface Presenter extends BasePresenter{
        // 请求数据
        void loadPosts(long date, boolean isLoadMore);
    }
}
