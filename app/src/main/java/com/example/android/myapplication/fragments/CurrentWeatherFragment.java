package com.example.android.myapplication.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.android.myapplication.R;
import com.example.android.myapplication.pojo.detailedweatherday.WeatherDay;
import com.example.android.myapplication.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment {
    private static final String LOG_TAG = "Main";
    private static final String API_KEY = "c89704617764eb6d325c853555bdb333";
    private static final int OK = 200;
    //private ForecastService weatherService;
    private WeatherService weatherService;
    private ImageView weather;
    private TextView temperature;
    private TextView windSpeed;
    private ImageView windDir;
    private TextView humidity;
    private TextView pressure;
    private TextView city;
    private final String cityName = "Kiev";

    public CurrentWeatherFragment() {
        super();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        weather = view.findViewById(R.id.weather);
        temperature = view.findViewById(R.id.temp);
        windSpeed = view.findViewById(R.id.windSpeed);
        windDir = view.findViewById(R.id.windDir);
        humidity = view.findViewById(R.id.humidity);
        pressure = view.findViewById(R.id.pressure);
        city = view.findViewById(R.id.city);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherService = retrofit.create(WeatherService.class);
        weatherService.getWeatherByCityName(cityName, API_KEY, "metric").enqueue(new WeatherDayCallback());
        return view;
    }

    private void updateForecastView(WeatherDay weatherDay) {
        weather.setImageResource(Utils.getWeatherIcon(weatherDay.getWeather().get(0).getId()));
        weather.setVisibility(View.VISIBLE);
        temperature.setText(Double.toString(weatherDay.getMain().getTemp()));
        temperature.setVisibility(View.VISIBLE);
        //Log.e("Main", String.valueOf(weatherDay.getMain().getTemp()));
        windSpeed.setText(Double.toString(weatherDay.getWind().getSpeed()));
        windSpeed.setVisibility(View.VISIBLE);
        //Log.e("Main", "rot " + weatherDay.getWind().getDeg());
        windDir.setRotation((int)weatherDay.getWind().getDeg());
        windDir.setVisibility(View.VISIBLE);
        humidity.setText(Integer.toString(weatherDay.getMain().getHumidity()));
        humidity.setVisibility(View.VISIBLE);
        pressure.setText(Integer.toString((int)weatherDay.getMain().getPressure()));
        pressure.setVisibility(View.VISIBLE);
        city.setText(cityName);
        city.setVisibility(View.VISIBLE);
    }



    public interface WeatherService{
        @GET("/data/2.5/weather")
        Call<WeatherDay> getWeatherByCityName(@Query("q")String city, @Query("appId") String appID, @Query("units") String units);
    }

    private class WeatherDayCallback implements Callback<WeatherDay> {
        @Override
        public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {
            if (response.isSuccessful() && response.code() == OK) {
                WeatherDay weatherDay = response.body();
                updateForecastView(weatherDay);
                Log.e(LOG_TAG, Integer.toString(weatherDay.getDt()));
                // Log.e(LOG_TAG, "dddd");
            }
        }

        @Override
        public void onFailure(Call<WeatherDay> call, Throwable t) {
        }
    }

}
