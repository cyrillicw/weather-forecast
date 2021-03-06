package com.example.android.myapplication.data;

import com.example.android.myapplication.data.pojo.Forecast;
import com.example.android.myapplication.data.pojo.WeatherDay;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("/data/2.5/weather")
    Call<WeatherDay> getWeatherByCityName(@Query("q")String city, @Query("appId") String appID, @Query("units") String units);

    @GET("/data/2.5/forecast")
    Call<Forecast> getForecastByCityName(@Query("q")String city, @Query("appId") String appID, @Query("units") String units);
}
