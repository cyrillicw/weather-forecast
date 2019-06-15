package com.example.android.myapplication.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import com.example.android.myapplication.data.database.entities.CurrentWeatherEntity;
import com.example.android.myapplication.data.database.entities.ForecastEntity;

import java.util.List;

@Dao
public abstract class ForecastDao {
    @Query("SELECT * FROM forecasts")
    public abstract LiveData<List<ForecastEntity>> getForecasts();

    @Query("SELECT * FROM weather")
    public abstract LiveData<CurrentWeatherEntity> getCurrentWeather();

    @Insert
    public abstract void addCurrentWeather(CurrentWeatherEntity currentWeatherEntity);

    @Insert
    public abstract void addForecast(ForecastEntity forecastEntity);

    @Query("DELETE FROM forecasts")
    public abstract void clearForecasts();

    @Query("DELETE FROM weather")
   public abstract void clearCurrentWeather();

    @Insert
    public abstract void addForecasts(List<ForecastEntity> forecastEntities);

    @Transaction
    public void updateForecast(List<ForecastEntity> forecastEntities) {
        clearForecasts();
        addForecasts(forecastEntities);
    }

    @Transaction
    public void updateCurrentWeather(CurrentWeatherEntity currentWeatherEntity) {
        clearCurrentWeather();
        addCurrentWeather(currentWeatherEntity);
    }
}
