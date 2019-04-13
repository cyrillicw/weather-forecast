package com.example.android.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.myapplication.pojo.Weather;
import com.example.android.myapplication.pojo.WeatherDay;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "Main";
    private static final String API_KEY = "c89704617764eb6d325c853555bdb333";

    private WeatherService weatherService;
    private TextView temperature;
    private TextView windSpeed;
    private ImageView windDir;
    private TextView humidity;
    private TextView pressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperature = findViewById(R.id.temp);
        windSpeed = findViewById(R.id.windSpeed);
        windDir = findViewById(R.id.windDir);
        humidity = findViewById(R.id.humidity);
        pressure = findViewById(R.id.pressure);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherService = retrofit.create(WeatherService.class);
        weatherService.getWeatherByCityName("donetsk", API_KEY, "metric").enqueue(new Callback<WeatherDay>() {
            @Override
            public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {
                Log.e(LOG_TAG, Integer.toString(response.code()));
                if (response.isSuccessful() && response.code() == 200) {
                    System.out.println(response.body());
                    WeatherDay weatherDay = response.body();
                    updateForecastView(weatherDay);
                    // Log.e("MAIN", "TEMP: " + Double.toString(weatherDay.getMain().getTemp()));
                }
                else {
                    Log.e(LOG_TAG, Integer.toString(response.code()));
                    // Log.e("Main", response.toString());
                    // Log.e("MAIN", "code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherDay> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());

            }
        });
    }

    private void updateForecastView(WeatherDay weatherDay) {
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
    }

    public interface WeatherService{
        @GET("/data/2.5/weather")
        Call<WeatherDay> getWeatherByCityName(@Query("q")String city, @Query("appId") String appID, @Query("units") String units);
    }
}
