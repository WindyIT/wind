package com.example.windy.wind.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windy on 2017/9/29.
 */

public class ZhihuDailyNews {
    @Expose
    @SerializedName("date")
    String date;

    @Expose
    @SerializedName("stories")
    List<ZhihuDailyItem> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhihuDailyItem> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhihuDailyItem> stories) {
        this.stories = stories;
    }

    @Override
    public String toString() {
        String dateStr = "Date : " + date + "\n";
        String itemsInfo = new String();
        for (ZhihuDailyItem item : stories){
            itemsInfo += item.toString() + "\n";
        }
        return dateStr + "\n" + itemsInfo;
    }
}
