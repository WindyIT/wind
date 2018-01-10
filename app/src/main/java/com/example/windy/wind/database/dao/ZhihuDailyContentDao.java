package com.example.windy.wind.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.windy.wind.data.beans.ZhihuDailyContent;

/**
 * Created by windy on 2017/12/18.
 */
@Dao
public interface ZhihuDailyContentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ZhihuDailyContent content);

    @Delete
    void delete(ZhihuDailyContent content);

    @Query("SELECT * FROM zhihu_daily_content WHERE id = :id")
    ZhihuDailyContent query(int id);

    @Update
    void update(ZhihuDailyContent content);
}
