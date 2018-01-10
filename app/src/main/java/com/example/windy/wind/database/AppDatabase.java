package com.example.windy.wind.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.windy.wind.data.beans.ZhihuDailyContent;
import com.example.windy.wind.data.beans.ZhihuDailyItem;
import com.example.windy.wind.database.dao.ZhihuDailyContentDao;
import com.example.windy.wind.database.dao.ZhihuDailyItemDao;

/**
 * Created by windy on 2017/12/18.
 */
@Database(entities = {ZhihuDailyItem.class, ZhihuDailyContent.class},
            version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract ZhihuDailyItemDao zhihuDailyItemDao();
    public abstract ZhihuDailyContentDao zhihuDailyContentDao();
}
