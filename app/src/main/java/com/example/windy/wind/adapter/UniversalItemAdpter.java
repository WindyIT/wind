package com.example.windy.wind.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.windy.wind.R;
import com.example.windy.wind.beans.ZhihuDailyItem;

import java.util.List;

/**
 * Created by windy on 2017/10/23.
 */

public class UniversalItemAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ZhihuDailyItem> mItemList;
    private Context mContext;

    public UniversalItemAdpter(List<ZhihuDailyItem> mItemList) {
        this.mItemList = mItemList;
    }

    //定义回调接口实现点击事件
    public interface OnItemCilckListener{
        void onClick(View view, int pos);
    }

    private OnItemCilckListener mOnItemCilckListener;

    //对外提供方法，接收实例对象
    public void setmOnItemCilckListener(OnItemCilckListener onItemCilckListener){
        mOnItemCilckListener = onItemCilckListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.universal_item, parent, false);
        return new RcViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        ZhihuDailyItem item = mItemList.get(position);
        RcViewHolder rcHolder = (RcViewHolder)holder;
        if (item.getimages().get(0) == null) {
            rcHolder.pImgView.setImageResource(R.drawable.ic_nav_header);
        }else {
            Glide.with(mContext)
                    .load(item.getimages().get(0))
                    .asBitmap()
                    .placeholder(R.drawable.ic_nav_header)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.ic_nav_header)
                    .centerCrop()
                    .into(rcHolder.pImgView);
        }

        rcHolder.pTextView.setText(item.getTitle());

        if (mOnItemCilckListener != null){
            rcHolder.pTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemCilckListener.onClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class RcViewHolder extends RecyclerView.ViewHolder{
        public TextView pTextView;
        public ImageView pImgView;
        public RcViewHolder(View itemView) {
            super(itemView);
            pTextView = (TextView)itemView.findViewById(R.id.item_text_view);
            pImgView = (ImageView)itemView.findViewById(R.id.item_view_cover);
        }
    }
}
