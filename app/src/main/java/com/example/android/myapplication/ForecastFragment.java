package com.example.android.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {
    private static String city = "Kiev";
    private static final String API_KEY = "c89704617764eb6d325c853555bdb333";
    private ForecastService forecastService;
    private RecyclerView recyclerView;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new ForecastAdapter(new ArrayList<WeatherDay>()));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()))
                .build();
        forecastService = retrofit.create(ForecastService.class);
        forecastService.getWeatherByCityName(city, API_KEY, "metric").enqueue(new WeatherForecastCallback());
        return view;
    }

    public interface ForecastService {
        @GET("/data/2.5/forecast")
        Call<Forecast> getWeatherByCityName(@Query("q")String city, @Query("appId") String appID, @Query("units") String units);
    }

    private class WeatherForecastCallback implements Callback<Forecast> {
        @Override
        public void onResponse(Call<Forecast> call, Response<Forecast> response) {
//            System.out.println("WWW");
//            Log.e("LOGG, ", "RESP");
            Forecast forecast = response.body();
            Log.e("LOG", "" + response.code());
            ((ForecastAdapter)recyclerView.getAdapter()).setWeatherDays(forecast.getWeatherDays());
        }

        @Override
        public void onFailure(Call<Forecast> call, Throwable t) {
            t.printStackTrace();
        }
    }
}
