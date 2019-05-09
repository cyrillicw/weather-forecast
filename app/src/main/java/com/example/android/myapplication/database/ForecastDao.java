package com.example.android.myapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ForecastDao {
    @Query("SELECT * FROM forecasts")
    List<ForecastEntity> getForecasts();

    @Query("SELECT * FROM weather")
    CurrentWeatherEntity getCurrentWeather();

    @Insert
    void addCurrentWeather(CurrentWeatherEntity currentWeatherEntity);

    @Insert
    void addForecast(ForecastEntity forecastEntity);

    @Query("DELETE FROM forecasts")
    void clearForecasts();

    @Query("DELETE FROM weather")
    void clearCurrentWeather();
}
