package com.example.windy.wind.timeline.zhihuDaily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windy.wind.beans.ZhihuDailyItem;

import java.util.ArrayList;

/**
 * Created by windy on 2017/9/29.
 */

public class ZhihuDailyFragment extends Fragment
                                implements ZhihuDailyContract.View {
    public ZhihuDailyFragment(){}

    public static ZhihuDailyFragment newInstance(){
        return new ZhihuDailyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showResults(ArrayList<ZhihuDailyItem> list) {

    }

    @Override
    public void showPickDialog() {

    }
}
