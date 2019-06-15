package com.example.android.myapplication.viewmodels;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.android.myapplication.data.Repository;
import com.example.android.myapplication.data.database.entities.CurrentWeatherEntity;

public class WeatherViewModel extends ViewModel {
    LiveData<CurrentWeatherEntity> data;

    public LiveData<CurrentWeatherEntity> getWeatherData() {
        Log.e("get weather data", "before");
        if (data ==  null) {
            data = Repository.REPOSITORY.getWeather();
        }
        return data;
    }
}
