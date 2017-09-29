package com.example.windy.wind;

import android.view.View;

/**
 * Created by windy on 2017/9/29.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void initViews(View view);
}
