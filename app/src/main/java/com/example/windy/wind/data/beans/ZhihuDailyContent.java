package com.example.windy.wind.data.beans;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.example.windy.wind.database.converter.StringTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by windy on 2017/11/11.
 */
@Entity(tableName = "zhihu_daily_content")
@TypeConverters({StringTypeConverter.class})
public class ZhihuDailyContent {
    @ColumnInfo(name = "body")
    @Expose
    @SerializedName("body")
    private String body;

    @ColumnInfo(name = "image_source")
    @Expose
    @SerializedName("image_source")
    private String image_source;

    @ColumnInfo(name = "title")
    @Expose
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "image")
    @Expose
    @SerializedName("image")
    private String image;

    @ColumnInfo(name = "share_url")
    @Expose
    @SerializedName("share_url")
    private String share_url;

    @Ignore
    @Expose
    @SerializedName("js")
    private List<String> js;

    @Ignore
    @Expose
    @SerializedName("ga_prefix")
    private String ga_prefix;

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
    @SerializedName("css")
    private List<String> css;

    public String getBody() {
        return body;
    }

    public String getImage_source() {
        return image_source;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getShare_url() {
        return share_url;
    }

    public List<String> getJs() {
        return js;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public List<String> getImages() {
        return images;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public List<String> getCss() {
        return css;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }
}
