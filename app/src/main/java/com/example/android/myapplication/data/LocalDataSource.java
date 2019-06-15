package com.example.android.myapplication.data;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import com.example.android.myapplication.data.database.ForecastDatabase;
import com.example.android.myapplication.data.database.entities.CurrentWeatherEntity;
import com.example.android.myapplication.data.database.entities.ForecastEntity;

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
        db.forecastDao().updateForecast(forecastEntities);
    }

    public void updateCurrentWeather(CurrentWeatherEntity currentWeatherEntity) {
        Log.e("LOCAL", "IS NULL " + (currentWeatherEntity == null));
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
