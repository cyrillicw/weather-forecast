package com.example.android.myapplication.database;

import android.util.Log;
import com.example.android.myapplication.database.entities.detailedweatherday.Forecast;
import com.example.android.myapplication.database.entities.detailedweatherday.WeatherDay;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class RemoteDataSource {
    private static final String LOG_TAG = "RemoteDataSource";
    private WeatherService weatherService;
    private static String city = "Kiev";
    private static final String API_KEY = "c89704617764eb6d325c853555bdb333";
    private static final String units = "metric";



    public RemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()))
                .build();
        weatherService = retrofit.create(WeatherService.class);
    }

    public WeatherDay getWeatherDay() {
        Call<WeatherDay> call = weatherService.getWeatherByCityName(city, API_KEY, units);
        try {
            Response<WeatherDay> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            else {
                return null;
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "ERROR");
            return null;
        }
    }

    public Forecast getWeatherForecast() {
        Call<Forecast> call = weatherService.getForecastByCityName(city, API_KEY, units);
        try {
            Response<Forecast> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            else {
                return null;
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "ERROR");
            return null;
        }
    }
}
