package com.example.android.myapplication.data;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.work.*;
import com.example.android.myapplication.data.database.entities.CurrentWeatherEntity;
import com.example.android.myapplication.data.pojo.Forecast;
import com.example.android.myapplication.data.database.entities.ForecastEntity;
import com.example.android.myapplication.data.pojo.WeatherDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public enum Repository {
    REPOSITORY;

    private static final String workerTag = "updateWorkerTag";

    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;
    ExecutorService executorService;

    public static void initialize(Context context) {
        REPOSITORY.remoteDataSource = new RemoteDataSource();
        REPOSITORY.localDataSource = new LocalDataSource(context);
        REPOSITORY.executorService = Executors.newSingleThreadExecutor();
        REPOSITORY.scheduleUpdate();
    }

    public LiveData<CurrentWeatherEntity> getWeather() {
        return localDataSource.getCurrentWeather();
    }

    public LiveData<List<ForecastEntity>> getForecast() {
        return localDataSource.getForecasts();
    }

    public void forceCurrentWeatherUpdate() {
        Log.e("REPO", "Inweatherupdate");
        WeatherDay weatherDay = remoteDataSource.getWeatherDay();
        if (weatherDay != null) {
            Log.e("REPO", "Inweatherupdate not null");
            CurrentWeatherEntity currentWeatherEntity = new CurrentWeatherEntity();
            currentWeatherEntity.setTemp(weatherDay.getMain().getTemp());
            currentWeatherEntity.setLastUpdate(Calendar.getInstance());
            currentWeatherEntity.setWeatherId(weatherDay.getWeather().get(0).getId());
            currentWeatherEntity.setHumidity(weatherDay.getMain().getHumidity());
            currentWeatherEntity.setPressure(weatherDay.getMain().getPressure());
            currentWeatherEntity.setWindSpeed(weatherDay.getWind().getSpeed());
            currentWeatherEntity.setWindAngle(weatherDay.getWind().getDeg());
            localDataSource.updateCurrentWeather(currentWeatherEntity);
        }
    }

    public void forceForecastUpdate() {
        Forecast forecast = remoteDataSource.getWeatherForecast();
        List<ForecastEntity> forecastEntities = new ArrayList<>();
        if (forecast != null) {
            Calendar calendar = Calendar.getInstance();
            for (WeatherDay weatherDay : forecast.getWeatherDays()) {
                ForecastEntity forecastEntity = new ForecastEntity();
                forecastEntity.setTempMin(weatherDay.getMain().getTempMin());
                forecastEntity.setTempMax(weatherDay.getMain().getTempMax());
                forecastEntity.setDate(new Date(1000L * weatherDay.getDt()));
                forecastEntity.setUpdateDate(calendar);
                forecastEntity.setWeatherId(weatherDay.getWeather().get(0).getId());
                forecastEntities.add(forecastEntity);
            }
            localDataSource.updateForecast(forecastEntities);
        }
    }

    public void scheduleUpdate() {
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(SyncWorker.class, 1, TimeUnit.HOURS)
                .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork(workerTag, ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);
    }
}
