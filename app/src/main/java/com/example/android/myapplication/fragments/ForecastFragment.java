package com.example.android.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.android.myapplication.R;
import com.example.android.myapplication.adapters.ForecastAdapter;
import com.example.android.myapplication.database.ForecastDao;
import com.example.android.myapplication.database.ForecastDatabase;
import com.example.android.myapplication.database.ForecastEntity;
import com.example.android.myapplication.pojo.detailedweatherday.Forecast;
import com.example.android.myapplication.pojo.detailedweatherday.WeatherDay;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {
    private static String city = "Kiev";
    private static final String API_KEY = "c89704617764eb6d325c853555bdb333";
    private ForecastService forecastService;
    private RecyclerView recyclerView;
    private ForecastDatabase roomDatabase;

    public ForecastFragment() {
        super();
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        recyclerView = view.findViewById(R.id.forecast_recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new ForecastAdapter(new ArrayList<ForecastEntity>()));
        roomDatabase = Room.databaseBuilder(getContext(), ForecastDatabase.class, "weather").fallbackToDestructiveMigration().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()))
                .build();
        forecastService = retrofit.create(ForecastService.class);
        Log.e("IN ON CREATE", Thread.currentThread().getName());
        return view;
    }

    public interface ForecastService {
        @GET("/data/2.5/forecast")
        Call<Forecast> getWeatherByCityName(@Query("q")String city, @Query("appId") String appID, @Query("units") String units);
    }

    @Override
    public void onStart() {
        super.onStart();
        getContents();
    }

    private void getContents() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("IN GET CONTENTS", Thread.currentThread().getName());
                ForecastDao forecastDao = roomDatabase.forecastDao();
                final List<ForecastEntity> forecastEntities = forecastDao.getForecasts();
                Calendar minCalendar = Calendar.getInstance();
                minCalendar.add(Calendar.HOUR_OF_DAY, -1);
                if (forecastEntities.isEmpty() || forecastEntities.get(0).getUpdateDate().before(minCalendar)) {
                    forecastDao.clearForecasts();
                    forecastService.getWeatherByCityName(city, API_KEY, "metric").enqueue(new WeatherForecastCallback());
                }
                else {
                    loadValidData();
                }
            }
        });
        executorService.shutdown();
    }

    private void loadValidData() {
        Log.e("THREADS RUNNING", ""+Thread.activeCount());
        Log.e("IN LOAD VALID DATA", Thread.currentThread().getName());
        ForecastDao forecastDao = roomDatabase.forecastDao();
        final List<ForecastEntity> forecastEntities = forecastDao.getForecasts();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((ForecastAdapter)recyclerView.getAdapter()).setWeatherDays(forecastEntities);
            }
        });
    }

    private class WeatherForecastCallback implements Callback<Forecast> {
        @Override
        public void onResponse(Call<Forecast> call, final Response<Forecast> response) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Log.e("IN GET RESPONSE", Thread.currentThread().getName());
                    Forecast forecast = response.body();
                    Calendar calendar = Calendar.getInstance();
                    for (WeatherDay weatherDay : forecast.getWeatherDays()) {
                        ForecastEntity forecastEntity = new ForecastEntity();
                        forecastEntity.setTempMin(weatherDay.getMain().getTempMin());
                        forecastEntity.setTempMax(weatherDay.getMain().getTempMax());
                        forecastEntity.setDate(new Date(1000L * weatherDay.getDt()));
                        forecastEntity.setUpdateDate(calendar);
                        forecastEntity.setWeatherId(weatherDay.getWeather().get(0).getId());
                        roomDatabase.forecastDao().addForecast(forecastEntity);
                    }
                    loadValidData();
                }
            });
            executorService.shutdown();
        }

        @Override
        public void onFailure(Call<Forecast> call, Throwable t) {
            t.printStackTrace();
        }
    }
}
