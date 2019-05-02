package com.example.android.myapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ForecastDao {
    @Query("SELECT * FROM forecasts")
    List<ForecastEntity> getForecasts();

    @Insert
    void addForecast(ForecastEntity forecastEntity);

    @Query("DELETE FROM forecasts")
    void clearForecasts();
}
