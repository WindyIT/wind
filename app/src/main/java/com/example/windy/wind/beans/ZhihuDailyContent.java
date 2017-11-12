package com.example.windy.wind.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by windy on 2017/11/11.
 */

public class ZhihuDailyContent {
    @Expose
    @SerializedName("body")
    private String body;

    @Expose
    @SerializedName("image_source")
    private String image_source;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("image")
    private String image;

    @Expose
    @SerializedName("share_url")
    private String share_url;

    @Expose
    @SerializedName("js")
    private List<String> js;

    @Expose
    @SerializedName("ga_prefix")
    private String ga_prefix;

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
