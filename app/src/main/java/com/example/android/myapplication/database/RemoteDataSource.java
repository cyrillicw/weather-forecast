package com.example.android.myapplication.database;

import android.util.Log;
import com.example.android.myapplication.database.entities.detailedweatherday.Forecast;
import com.example.android.myapplication.database.entities.detailedweatherday.Weather;
import com.example.android.myapplication.database.entities.detailedweatherday.WeatherDay;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class RemoteDataSource {
    private static final String LOG_TAG = "RemoteDataSource";
    private WeatherService weatherService;

    public RemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherService = retrofit.create(WeatherService.class);
    }

    public WeatherDay getWeatherDay() {
        Call<WeatherDay> call = weatherService.getWeatherByCityName();
        try {
            Response<WeatherDay> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "ERROR");
            return null;
        }
    }

    public Forecast getWeatherForecast() {
        weatherService.getForecastByCityName();
    }
}
