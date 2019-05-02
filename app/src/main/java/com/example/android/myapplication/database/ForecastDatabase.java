package com.example.android.myapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = ForecastEntity.class, version = 2, exportSchema = false)
@TypeConverters(ForecastEntity.class)
public abstract class ForecastDatabase extends RoomDatabase {
    public abstract ForecastDao forecastDao();
}
