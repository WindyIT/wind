package com.example.windy.wind.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by windy on 2017/9/29.
 */

public class ZhihuDailyItem {
    @Expose
    @SerializedName("images")
    private List<String> images;

    @Expose
    @SerializedName("type")
    private int type;

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("ga_prefix")
    private int gaPrefix;

    @Expose
    @SerializedName("title")
    private String title;

    public ZhihuDailyItem() {
    }

    public ZhihuDailyItem(List<String> images, int type, int id, int gaPrefix, String title) {
        this.images = images;
        this.type = type;
        this.id = id;
        this.gaPrefix = gaPrefix;
        this.title = title;
    }

    public List<String> getimages() {
        return images;
    }

    public void setimages(List<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getgaPrefix() {
        return gaPrefix;
    }

    public void setgaPrefix(int gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Image : " + images + "\n" +
                "Type : " + type + "\n" +
                "Id : " + id + "\n" +
                "GaPrefix : " + gaPrefix + "\n" +
                "Title : " + title + "\n";
    }
}


