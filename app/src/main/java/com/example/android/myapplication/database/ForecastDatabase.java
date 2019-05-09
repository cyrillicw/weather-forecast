package com.example.android.myapplication.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {ForecastEntity.class, CurrentWeatherEntity.class}, version = 3, exportSchema = false)
@TypeConverters(ForecastEntity.class)
public abstract class ForecastDatabase extends RoomDatabase {
    private static volatile ForecastDatabase instance;
    public abstract ForecastDao forecastDao();

    public static synchronized ForecastDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, ForecastDatabase.class, "weather").build();
        }
        return instance;
    }
}
