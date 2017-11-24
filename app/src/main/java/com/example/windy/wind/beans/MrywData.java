package com.example.windy.wind.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windy on 2017/11/24.
 */

public class MrywData {
    @Expose
    @SerializedName("data")
    private MrywContent mrywContent;

    public MrywContent getMrywContent() {
        return mrywContent;
    }

    public void setMrywContent(MrywContent mrywContent) {
        this.mrywContent = mrywContent;
    }
}
