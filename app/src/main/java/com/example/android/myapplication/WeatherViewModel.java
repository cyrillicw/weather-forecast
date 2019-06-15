package com.example.android.myapplication;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.android.myapplication.database.Repository;
import com.example.android.myapplication.database.entities.detailedweatherday.CurrentWeatherEntity;
import com.example.android.myapplication.database.entities.detailedweatherday.WeatherDay;

public class WeatherViewModel extends ViewModel {
    private Repository repository;
    LiveData<CurrentWeatherEntity> data;

    private void loadData(Context context) {
        if (data ==  null) {
            repository = Repository.getInstance(context);
            data = repository.getWeather();
        }
    }

    public LiveData<CurrentWeatherEntity> getWeather() {
        return data;
    }
}
