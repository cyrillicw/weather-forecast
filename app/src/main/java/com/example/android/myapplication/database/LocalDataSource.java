package com.example.android.myapplication.database;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;

import java.util.List;

public class LocalDataSource {
    private static volatile ForecastDatabase instance;

    public static synchronized ForecastDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, ForecastDatabase.class, "weather").build();
        }
        return instance;
    }

    LiveData<List<ForecastEntity>> getForecasts() {
        return null;
    }

    LiveData<CurrentWeatherEntity> getCurrentWeather() {
        return null;
    }

    void addCurrentWeather(CurrentWeatherEntity currentWeatherEntity) {}

    void addForecast(ForecastEntity forecastEntity){}

    void clearForecasts() {}

    void clearCurrentWeather() {}
}
