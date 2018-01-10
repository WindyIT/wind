package com.example.windy.wind.database.converter;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by windy on 2017/12/19.
 */
public class StringTypeConverter {
    @TypeConverter
    public static String listToString(List<String> list){
         return new Gson().toJson(list);
    }

    @TypeConverter
    public static List<String> stringToList(String string){
        Type typeList = new TypeToken<ArrayList<String>>(){}.getType();
        return new Gson().fromJson(string, typeList);
    }
}
