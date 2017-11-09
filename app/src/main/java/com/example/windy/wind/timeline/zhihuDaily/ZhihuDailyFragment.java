package com.example.windy.wind.timeline.zhihuDaily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.windy.wind.R;
import com.example.windy.wind.adapter.SlideUpwardScrollListener;
import com.example.windy.wind.adapter.UniversalItemAdpter;
import com.example.windy.wind.beans.ZhihuDailyItem;
import com.example.windy.wind.beans.ZhihuDailyNews;
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

    private int mYear, mMonth, mDay;

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
        //init
        mRequestDataRx = RequestDataRx.newInstance();
        mUniversalItemAdpter = new UniversalItemAdpter();
        mRecyclerView.setAdapter(mUniversalItemAdpter);
        setOnClick();
        setOnScrollUpward();
        //
        Calendar c = Calendar.getInstance();
        mRequestDataRx.getZhihuInfo(c.getTimeInMillis(), getDefaultSubscriber());
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Calendar c = Calendar.getInstance();
               mRequestDataRx.getZhihuInfo(c.getTimeInMillis(), getDefaultSubscriber());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFristLoad){
            isFristLoad = false;
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                }
            });
        }
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

        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
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


    }

    @Override
    public void showPickDialog() {

    }

    private void setOnClick(){
        mUniversalItemAdpter.setmOnItemCilckListener(new UniversalItemAdpter.OnItemCilckListener() {
            @Override
            public void onClick(View view, int pos) {
                Toast.makeText(getActivity(), "I am the " + pos + " text!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnScrollUpward(){
        mRecyclerView.addOnScrollListener(new SlideUpwardScrollListener() {
            @Override
            public void onLordMore() {
                --mDay;
                Calendar c = Calendar.getInstance();
                c.set(mYear, mMonth, mDay);
                mRequestDataRx.getZhihuInfo(c.getTimeInMillis(),
                        new Subscriber<ZhihuDailyNews>() {
                            @Override
                            public void onCompleted() {
                                mUniversalItemAdpter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(Throwable e) {}

                            @Override
                            public void onNext(ZhihuDailyNews zhihuDailyNews) {
                                mUniversalItemAdpter.updataData(zhihuDailyNews.getStories());
                            }
                        });
              }
        });
    }

    private Subscriber<ZhihuDailyNews> getDefaultSubscriber(){
        return new Subscriber<ZhihuDailyNews>() {
            @Override
            public void onCompleted() {
                mUniversalItemAdpter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(ZhihuDailyNews zhihuDailyNews) {
                mUniversalItemAdpter.setItemList(zhihuDailyNews.getStories());
            }
        };
    }
}
