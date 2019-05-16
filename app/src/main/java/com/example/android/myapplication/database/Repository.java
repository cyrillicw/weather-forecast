package com.example.android.myapplication.database;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.android.myapplication.database.entities.detailedweatherday.WeatherDay;
import com.example.android.myapplication.utils.Utils;

public class Repository {
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    public Repository(Context context) {
        localDataSource = new LocalDataSource();
        remoteDataSource = new RemoteDataSource();
    }

    public LiveData<CurrentWeatherEntity> getWeather(String city) {
        Utils.EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        return localDataSource.getCurrentWeather();
    }
}
