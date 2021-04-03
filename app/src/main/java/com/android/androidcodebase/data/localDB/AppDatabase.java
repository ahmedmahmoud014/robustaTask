package com.android.androidcodebase.data.localDB;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.androidcodebase.data.model.WeatherInfo;

@Database(entities = {WeatherInfo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WeatherDAO weatherDAO();
}
