package com.example.android.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.android.myapplication.data.Repository;
import com.example.android.myapplication.data.database.entities.ForecastEntity;

import java.util.List;

public class ForecastViewModel extends ViewModel {
    LiveData<List<ForecastEntity>> data;

    public LiveData<List<ForecastEntity>> getForecastData() {
        if (data ==  null) {
            data = Repository.REPOSITORY.getForecast();
        }
        return data;
    }
}
