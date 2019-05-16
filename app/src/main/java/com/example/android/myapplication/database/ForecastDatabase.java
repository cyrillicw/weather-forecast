package com.example.android.myapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.android.myapplication.database.entities.detailedweatherday.CurrentWeatherEntity;
import com.example.android.myapplication.database.entities.detailedweatherday.ForecastEntity;

@Database(entities = {ForecastEntity.class, CurrentWeatherEntity.class}, version = 4, exportSchema = false)
@TypeConverters(ForecastEntity.class)
public abstract class ForecastDatabase extends RoomDatabase {
    private static volatile ForecastDatabase instance;
    public abstract ForecastDao forecastDao();
}
