package com.example.android.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android.myapplication.R;
import com.example.android.myapplication.adapters.ForecastAdapter;
import com.example.android.myapplication.database.ForecastDao;
import com.example.android.myapplication.database.ForecastDatabase;
import com.example.android.myapplication.database.ForecastEntity;
import com.example.android.myapplication.pojo.detailedweatherday.Forecast;
import com.example.android.myapplication.pojo.detailedweatherday.WeatherDay;
import com.example.android.myapplication.utils.Utils;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment implements Observer<List<ForecastEntity>> {
    private static String city = "Kiev";
    private static final String API_KEY = "c89704617764eb6d325c853555bdb333";
    private ForecastService forecastService;
    private RecyclerView recyclerView;
    private ForecastDatabase roomDatabase;
    private MutableLiveData<List<ForecastEntity>> liveData;

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
        roomDatabase = ForecastDatabase.getInstance(getContext());
        liveData = new MutableLiveData<>();
        liveData.observe(this, this);
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

    @Override
    public void onChanged(List<ForecastEntity> forecastEntities) {
        ((ForecastAdapter)recyclerView.getAdapter()).setWeatherDays(forecastEntities);
    }

    private void getContents() {
        // ExecutorService executorService = Executors.newSingleThreadExecutor();
        Utils.EXECUTOR_SERVICE.execute(new Runnable() {
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
        // executorService.shutdown();
    }

    private void loadValidData() {
        Log.e("FORECAST", "THREADS RUNNING "+Thread.activeCount());
        Log.e("IN LOAD VALID DATA", Thread.currentThread().getName());
        ForecastDao forecastDao = roomDatabase.forecastDao();
        final List<ForecastEntity> forecastEntities = forecastDao.getForecasts();
        liveData.postValue(forecastEntities);
        /*getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((ForecastAdapter)recyclerView.getAdapter()).setWeatherDays(forecastEntities);
            }
        });*/
    }

    private class WeatherForecastCallback implements Callback<Forecast> {
        @Override
        public void onResponse(Call<Forecast> call, final Response<Forecast> response) {
            // ExecutorService executorService = Executors.newSingleThreadExecutor();
            Utils.EXECUTOR_SERVICE.execute(new Runnable() {
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
            // executorService.shutdown();
        }

        @Override
        public void onFailure(Call<Forecast> call, Throwable t) {
            t.printStackTrace();
        }
    }
}
