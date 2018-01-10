package com.example.windy.wind.data.beans;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.windy.wind.database.converter.StringTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by windy on 2017/9/29.
 */
@Entity(tableName = "zhihu_daily_item")
@TypeConverters({StringTypeConverter.class})
public class ZhihuDailyItem {
    @ColumnInfo(name = "images")
    @Expose
    @SerializedName("images")
    private List<String> images;

    @ColumnInfo(name = "type")
    @Expose
    @SerializedName("type")
    private int type;

    @PrimaryKey
    @ColumnInfo(name = "id")
    @Expose
    @SerializedName("id")
    private int id;

    @Ignore
    @Expose
    @SerializedName("ga_prefix")
    private int gaPrefix;

    @ColumnInfo(name = "title")
    @Expose
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "favorate")
    @Expose
    private boolean favorate;

    @ColumnInfo(name = "timestamp")
    @Expose
    private long timestamp;

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

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setGaPrefix(int gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public void setFavorate(boolean favorate) {
        this.favorate = favorate;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getImages() {
        return images;
    }

    public int getGaPrefix() {
        return gaPrefix;
    }

    public boolean isFavorate() {
        return favorate;
    }

    public long getTimestamp() {
        return timestamp;
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


