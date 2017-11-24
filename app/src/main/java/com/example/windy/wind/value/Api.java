package com.example.windy.wind.value;

/**
 * Created by windy on 2017/10/27.
 */

public class Api {
    //base uri
    //拼接 latest 获取最新讯息(json格式)
    //拼接 before/date, date 形式为例 20170101  不得小于20130520 获取历史讯息
    //拼接 id 获取指定新闻详细讯息
    public static final String ZHIHU_NEWS = "https://news-at.zhihu.com/api/4/news/";

    public static final String MRYW = "https://interface.meiriyiwen.com/article/day";
}
