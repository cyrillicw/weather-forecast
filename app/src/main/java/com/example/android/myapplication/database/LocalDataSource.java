package com.example.android.myapplication.database;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import com.example.android.myapplication.database.entities.detailedweatherday.CurrentWeatherEntity;
import com.example.android.myapplication.database.entities.detailedweatherday.ForecastEntity;

import java.util.List;

public class LocalDataSource {
    private static volatile ForecastDatabase db;

    LocalDataSource (Context context) {
        db = Room.databaseBuilder(context, ForecastDatabase.class, "weather").fallbackToDestructiveMigration().build();
    }

    LiveData<List<ForecastEntity>> getForecasts() {
        return db.forecastDao().getForecasts();
    }

    public void updateForecast(List<ForecastEntity> forecastEntities) {
        db.clearAllTables();
        db.forecastDao().updateForecast(forecastEntities);
    }

    public void updateCurrentWeather(CurrentWeatherEntity currentWeatherEntity) {
        db.forecastDao().updateCurrentWeather(currentWeatherEntity);
    }

    LiveData<CurrentWeatherEntity> getCurrentWeather() {
        return db.forecastDao().getCurrentWeather();
    }

    void addCurrentWeather(CurrentWeatherEntity currentWeatherEntity) {}

    void addForecast(ForecastEntity forecastEntity){}

    void clearForecasts() {}

    void clearCurrentWeather() {}
}
