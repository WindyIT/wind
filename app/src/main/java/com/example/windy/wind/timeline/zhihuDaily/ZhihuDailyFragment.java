package com.example.windy.wind.timeline.zhihuDaily;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.windy.wind.R;
import com.example.windy.wind.ZhihuContentActivity;
import com.example.windy.wind.adapter.SlideUpwardScrollListener;
import com.example.windy.wind.adapter.UniversalItemAdpter;
import com.example.windy.wind.data.beans.ZhihuDailyItem;
import com.example.windy.wind.data.beans.ZhihuDailyNews;
import com.example.windy.wind.decoration.ItemDividerDecoration;
import com.example.windy.wind.network.RequestDataRx;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import rx.Subscriber;

/**
 * Created by windy on 2017/9/29.
 */

public class ZhihuDailyFragment extends Fragment
                                implements ZhihuDailyContract.View {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;

    private UniversalItemAdpter mUniversalItemAdpter;
    private RequestDataRx mRequestDataRx;

    private boolean isFristLoad = true;

    private ZhihuDailyContract.Presenter mPresenter;

    private int mYear, mMonth, mDay;

    public static ZhihuDailyFragment newInstance(){
        return new ZhihuDailyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        //初始化控件
        initViews(view);
        //item响应事件
        setOnClick();
        //上拉/下拉加载更多事件
        setOnScroll();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        c.set(mYear, mMonth, mDay);
        
        if (isFristLoad){
            isFristLoad = false;
            mPresenter.loadPosts(c.getTimeInMillis(), false);
        }else {
            mPresenter.loadPosts(c.getTimeInMillis(), false);
        }
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {
        if (presenter != null)
            mPresenter = presenter;
    }

    @Override
    public void initViews(View view) {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new ItemDividerDecoration(getActivity(), RecyclerView.VERTICAL));

        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);

        mUniversalItemAdpter = new UniversalItemAdpter();
        mRecyclerView.setAdapter(mUniversalItemAdpter);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), "数据加载出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIndicator(final boolean flag) {
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(flag);
                }
            });
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showResults(List<ZhihuDailyItem> list) {
        mUniversalItemAdpter.updataData(list);
    }

    private void setOnClick(){
        mUniversalItemAdpter.setmOnItemCilckListener(new UniversalItemAdpter.OnItemCilckListener() {
            @Override
            public void onClick(View view, int pos) {
               ZhihuContentActivity.actionStart(getContext(), mUniversalItemAdpter.getItemList().get(pos).getId(), mUniversalItemAdpter.getItemList().get(pos).getTitle());
            }
        });
    }

    private void setOnScroll(){
        mRecyclerView.addOnScrollListener(new SlideUpwardScrollListener() {
            @Override
            public void onLordMore() {
                Calendar c = Calendar.getInstance();
                c.set(mYear, mMonth, --mDay);
                mPresenter.loadPosts(c.getTimeInMillis(), false);
              }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Calendar c = Calendar.getInstance();
                mPresenter.loadPosts(c.getTimeInMillis(), false);
            }
        });
    }
}
