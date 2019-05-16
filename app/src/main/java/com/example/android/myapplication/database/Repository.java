package com.example.android.myapplication.database;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.android.myapplication.database.entities.detailedweatherday.CurrentWeatherEntity;
import com.example.android.myapplication.database.entities.detailedweatherday.Forecast;
import com.example.android.myapplication.database.entities.detailedweatherday.ForecastEntity;
import com.example.android.myapplication.database.entities.detailedweatherday.WeatherDay;
import com.example.android.myapplication.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Repository {
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    private static volatile Repository instance;

    public static synchronized Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    public Repository(Context context) {
        localDataSource = new LocalDataSource(context);
        remoteDataSource = new RemoteDataSource();
    }

    public LiveData<CurrentWeatherEntity> getWeather() {
        return localDataSource.getCurrentWeather();
    }

    public LiveData<List<ForecastEntity>> getForecast() {
        return localDataSource.getForecasts();
    }

    public void forceCurrentWeatherUpdate() {
        Utils.EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                WeatherDay weatherDay = remoteDataSource.getWeatherDay();
                if (weatherDay != null) {
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
        });
    }

    public void forceForecastUpdate() {
        Utils.EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("REPOS", "HERE");
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
        });
    }
}
