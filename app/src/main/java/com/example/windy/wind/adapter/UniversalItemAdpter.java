package com.example.windy.wind.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windy.wind.R;
import com.example.windy.wind.beans.ZhihuDailyItem;

import java.util.List;

/**
 * Created by windy on 2017/10/23.
 */

public class UniversalItemAdpter extends RecyclerView.Adapter<UniversalItemAdpter.ViewHolder>{
    private List<ZhihuDailyItem> mItemList;

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.universal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        holder.pTextView.setText(mItemList.get(position).getTitle());
        //holder.pImgView.setImageResource(mItemList.get(position).getgaPrefix());
        holder.pImgView.setImageResource(R.drawable.ic_nav_header);

        if (mOnItemCilckListener != null){
            holder.pTextView.setOnClickListener(new View.OnClickListener() {
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView pTextView;
        public ImageView pImgView;
        public ViewHolder(View itemView) {
            super(itemView);
            pTextView = (TextView)itemView.findViewById(R.id.item_text_view);
            pImgView = (ImageView)itemView.findViewById(R.id.item_view_cover);
        }
    }
}
