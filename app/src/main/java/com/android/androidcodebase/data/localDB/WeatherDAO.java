package com.android.androidcodebase.data.localDB;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.androidcodebase.data.model.WeatherInfo;

import java.util.List;

@Dao
public interface WeatherDAO {

    @Query("SELECT * FROM WeatherInfo")
    List<WeatherInfo> getAll();

    @Insert
    void insert(WeatherInfo weatherInfo);

    @Delete
    void delete(WeatherInfo weatherInfo);

    @Update
    void update(WeatherInfo weatherInfo);
}
