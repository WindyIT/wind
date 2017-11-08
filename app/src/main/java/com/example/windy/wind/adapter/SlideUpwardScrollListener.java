package com.example.windy.wind.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by windy on 2017/11/8.
 */

public abstract class SlideUpwardScrollListener extends RecyclerView.OnScrollListener {
    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();

        if (newState == RecyclerView.SCROLL_STATE_IDLE){
            int lastItemPos = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            int itemCount = linearLayoutManager.getItemCount();

            //itemCount - 1 才可等于 lastItemPos
            if (lastItemPos == (itemCount - 1) && isSlidingUpward){
                onLordMore();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        isSlidingUpward = dy > 0;
    }

    public abstract void onLordMore();
}
