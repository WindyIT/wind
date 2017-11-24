package com.example.windy.wind.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windy on 2017/11/14.
 */

public class MrywContent {
    @Expose
    @SerializedName("date")
    private MrywDate mrywDate;

    @Expose
    @SerializedName("author")
    private String author;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("digest")
    private String digest;

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("wc")
    private int wc;

    public MrywDate getMrywDate() {
        return mrywDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDigest() {
        return digest;
    }

    public String getContent() {
        return content;
    }

    public int getWc() {
        return wc;
    }

    public void setMrywDate(MrywDate mrywDate) {
        this.mrywDate = mrywDate;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWc(int wc) {
        this.wc = wc;
    }
}
