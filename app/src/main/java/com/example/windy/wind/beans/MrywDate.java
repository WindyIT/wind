package com.example.windy.wind.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windy on 2017/11/14.
 */

public class MrywDate {
    @Expose
    @SerializedName("curr")
    private String curr;

    @Expose
    @SerializedName("prev")
    private String prev;

    @Expose
    @SerializedName("next")
    private String next;
}
