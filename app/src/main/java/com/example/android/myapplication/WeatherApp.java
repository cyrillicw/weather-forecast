package com.example.android.myapplication;

import android.app.Application;
import com.example.android.myapplication.data.Repository;

public class WeatherApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Repository.initialize(this);
    }
}
