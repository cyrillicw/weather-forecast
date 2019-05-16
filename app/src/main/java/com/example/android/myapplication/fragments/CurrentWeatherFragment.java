package com.example.android.myapplication.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.example.android.myapplication.R;
import com.example.android.myapplication.database.CurrentWeatherEntity;
import com.example.android.myapplication.database.ForecastDao;
import com.example.android.myapplication.database.ForecastDatabase;
import com.example.android.myapplication.database.entities.detailedweatherday.WeatherDay;
import com.example.android.myapplication.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment implements Observer<CurrentWeatherEntity> {
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
    private ForecastDatabase roomDatabase;
    private MutableLiveData<CurrentWeatherEntity> liveData;
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
        roomDatabase = ForecastDatabase.getInstance(getContext());
        liveData = new MutableLiveData<>();
        liveData.observe(this, this);
        getContents();
        return view;
    }

    @Override
    public void onChanged(CurrentWeatherEntity currentWeatherEntity) {
        weather.setImageResource(Utils.getWeatherIcon(currentWeatherEntity.getWeatherId()));
        weather.setVisibility(View.VISIBLE);
        temperature.setText(Utils.celsiusToString(currentWeatherEntity.getTemp()));
        temperature.setVisibility(View.VISIBLE);
        //Log.e("Main", String.valueOf(weatherDay.getMain().getTemp()));
        windSpeed.setText(Double.toString(currentWeatherEntity.getWindSpeed()));
        windSpeed.setVisibility(View.VISIBLE);
        //Log.e("Main", "rot " + weatherDay.getWind().getDeg());
        windDir.setRotation((int)currentWeatherEntity.getWindAngle());
        windDir.setVisibility(View.VISIBLE);
        humidity.setText(Integer.toString(currentWeatherEntity.getHumidity()));
        humidity.setVisibility(View.VISIBLE);
        pressure.setText(Integer.toString((int)currentWeatherEntity.getPressure()));
        pressure.setVisibility(View.VISIBLE);
        city.setText(cityName);
        city.setVisibility(View.VISIBLE);
    }

    private void getContents() {
        // ExecutorService executorService = Executors.newSingleThreadExecutor();
        Utils.EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("IN GET CONTENTS", Thread.currentThread().getName());
                ForecastDao forecastDao = roomDatabase.forecastDao();
                final CurrentWeatherEntity currentWeatherEntity = forecastDao.getCurrentWeather();
                Calendar minCalendar = Calendar.getInstance();
                minCalendar.add(Calendar.HOUR_OF_DAY, -1);
                if (currentWeatherEntity == null || currentWeatherEntity.getLastUpdate().before(minCalendar)) {
                    Log.e("NEED UPDATE", "HERE");
                    forecastDao.clearCurrentWeather();
                    weatherService.getWeatherByCityName(cityName, API_KEY, "metric").enqueue(new WeatherDayCallback());
                }
                else {
                    Log.e("NOT NEED UPDATE", "HERE");
                    loadValidData();
                }
            }
        });
        // executorService.shutdown();
    }

    private void loadValidData() {
        Log.e("CURRENT", "THREADS RUNNING "+Thread.activeCount());
        Log.e("IN LOAD VALID DATA", Thread.currentThread().getName());
        ForecastDao forecastDao = roomDatabase.forecastDao();
        final CurrentWeatherEntity currentWeatherEntity = forecastDao.getCurrentWeather();
        liveData.postValue(currentWeatherEntity);
        /*getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((ForecastAdapter)recyclerView.getAdapter()).setWeatherDays(forecastEntities);
            }
        });*/
    }

    private class WeatherDayCallback implements Callback<WeatherDay> {
        @Override
        public void onResponse(final Call<WeatherDay> call, final Response<WeatherDay> response) {
            Log.e("AFTER RESPONSE", "GG");
            if (response.isSuccessful() && response.code() == OK) {
                Utils.EXECUTOR_SERVICE.execute(new Runnable() {
                    @Override
                    public void run() {
                        WeatherDay weatherDay = response.body();
                        CurrentWeatherEntity currentWeatherEntity = new CurrentWeatherEntity();
                        currentWeatherEntity.setTemp(weatherDay.getMain().getTemp());
                        currentWeatherEntity.setLastUpdate(Calendar.getInstance());
                        currentWeatherEntity.setWeatherId(weatherDay.getWeather().get(0).getId());
                        currentWeatherEntity.setHumidity(weatherDay.getMain().getHumidity());
                        currentWeatherEntity.setPressure(weatherDay.getMain().getPressure());
                        currentWeatherEntity.setWindSpeed(weatherDay.getWind().getSpeed());
                        currentWeatherEntity.setWindAngle(weatherDay.getWind().getDeg());
                        roomDatabase.forecastDao().addCurrentWeather(currentWeatherEntity);
                        //updateForecastView(weatherDay);
                        Log.e("AFTER RESPONSE", Integer.toString(weatherDay.getDt()));
                        loadValidData();
                        // Log.e(LOG_TAG, "dddd");
                    }

                    ;
                });
            }
        }

        @Override
        public void onFailure(Call<WeatherDay> call, Throwable t) {
            Log.e("FAILURE", t.getMessage());
        }
    }

}
