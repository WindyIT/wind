package com.example.windy.wind.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.windy.wind.data.beans.ZhihuDailyItem;

import java.util.List;

/**
 * Created by windy on 2017/12/18.
 */
@Dao
public interface ZhihuDailyItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<ZhihuDailyItem> items);

    //返回删除的数量
    @Delete
    int delete(ZhihuDailyItem... items);

    @Update
    void update(ZhihuDailyItem item);

    @Query("SELECT * FROM zhihu_daily_item where id = :id")
    ZhihuDailyItem queryById(int id);

    //请求当天timestamp及前一天timestamp间的数据, 24h * 60min * 60s * 1000ms
    @Query("SELECT * FROM zhihu_daily_item where timestamp BETWEEN :timestamp - 24*60*60*1000+1 AND :timestamp ORDER BY timestamp ASC")
    List<ZhihuDailyItem> queryByDate(long timestamp);

    @Query("SELECT * FROM zhihu_daily_item where timestamp < :timestamp")
    List<ZhihuDailyItem> queryTimeoutItems(long timestamp);

    @Query("SELECT * FROM zhihu_daily_item")
    List<ZhihuDailyItem> queryAll();
}
