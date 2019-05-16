package com.example.android.myapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import com.example.android.myapplication.database.entities.detailedweatherday.CurrentWeatherEntity;
import com.example.android.myapplication.database.entities.detailedweatherday.ForecastEntity;

import java.util.List;

@Dao
public abstract class ForecastDao {
    @Query("SELECT * FROM forecasts")
    abstract LiveData<List<ForecastEntity>> getForecasts();

    @Query("SELECT * FROM weather")
    abstract LiveData<CurrentWeatherEntity> getCurrentWeather();

    @Insert
    abstract void addCurrentWeather(CurrentWeatherEntity currentWeatherEntity);

    @Insert
    abstract void addForecast(ForecastEntity forecastEntity);

    @Query("DELETE FROM forecasts")
    abstract void clearForecasts();

    @Query("DELETE FROM weather")
    abstract void clearCurrentWeather();

    @Insert
    abstract void addForecasts(List<ForecastEntity> forecastEntities);

    @Transaction
    void updateForecast(List<ForecastEntity> forecastEntities) {
        addForecasts(forecastEntities);
    }

    @Transaction
    void updateCurrentWeather(CurrentWeatherEntity currentWeatherEntity) {
        clearCurrentWeather();
        addCurrentWeather(currentWeatherEntity);
    }
}
