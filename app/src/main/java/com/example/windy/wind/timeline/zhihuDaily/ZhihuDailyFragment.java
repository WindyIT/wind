package com.example.windy.wind.timeline.zhihuDaily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.windy.wind.R;
import com.example.windy.wind.adapter.UniversalItemAdpter;
import com.example.windy.wind.beans.ZhihuDailyItem;
import com.example.windy.wind.beans.ZhihuDailyNews;
import com.example.windy.wind.decoration.ItemDividerDecoration;
import com.example.windy.wind.network.RetrofitFactory;
import com.example.windy.wind.retrofit.RetrofitService;
import com.example.windy.wind.value.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by windy on 2017/9/29.
 */

public class ZhihuDailyFragment extends Fragment
                                implements ZhihuDailyContract.View {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    public ZhihuDailyFragment(){}

    public static ZhihuDailyFragment newInstance(){
        return new ZhihuDailyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        //初始化控件
        initViews(view);

        Retrofit retrofit = RetrofitFactory.create().build(Api.ZHIHU_NEWS);
        //获取数据
        retrofit.create(RetrofitService.ZhihuDailyService.class)
                .getLatestInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuDailyNews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ZhihuDailyNews zhihuDailyNews) {
                         Log.v("Rx", zhihuDailyNews.toString());
                        showResults(zhihuDailyNews.getStories());
                    }
                });
        return view;
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new ItemDividerDecoration(getActivity(), RecyclerView.VERTICAL));
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
    public void showResults(List<ZhihuDailyItem> list) {
        UniversalItemAdpter itemAdapter = new UniversalItemAdpter(list);
        itemAdapter.setmOnItemCilckListener(new UniversalItemAdpter.OnItemCilckListener() {
            @Override
            public void onClick(View view, int pos) {
                Toast.makeText(getActivity(), "I am the " + pos + " text!", Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setAdapter(itemAdapter);
    }

    @Override
    public void showPickDialog() {

    }
}
