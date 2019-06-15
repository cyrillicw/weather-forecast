package com.example.android.myapplication.ui.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import com.example.android.myapplication.R;
import com.example.android.myapplication.viewmodels.WeatherViewModel;
import com.example.android.myapplication.data.database.entities.CurrentWeatherEntity;
import com.example.android.myapplication.utils.Utils;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment {
    private static final String LOG_TAG = "Main";

    private ImageView weather;
    private TextView temperature;
    private TextView windSpeed;
    private ImageView windDir;
    private TextView humidity;
    private TextView pressure;
    private TextView city;
    private LiveData<CurrentWeatherEntity> liveData;
    private final String cityName= "Kiev";

    public CurrentWeatherFragment() {
        super();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        WeatherViewModel weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        liveData = weatherViewModel.getWeatherData();
        weather = view.findViewById(R.id.weather);
        temperature = view.findViewById(R.id.temp);
        windSpeed = view.findViewById(R.id.windSpeed);
        windDir = view.findViewById(R.id.windDir);
        humidity = view.findViewById(R.id.humidity);
        pressure = view.findViewById(R.id.pressure);
        city = view.findViewById(R.id.city);
        getContents();
        return view;
    }

    private void getContents() {
        liveData.observe(this, this::onDataChanged);
    }

    private void onDataChanged(CurrentWeatherEntity currentWeatherEntity) {
        Log.e("CuurentWeather", "" + (currentWeatherEntity == null));
        if (currentWeatherEntity != null) {
            Calendar minCalendar = Calendar.getInstance();
            minCalendar.add(Calendar.HOUR_OF_DAY, -1);
            weather.setImageResource(Utils.getWeatherIcon(currentWeatherEntity.getWeatherId()));
            weather.setVisibility(View.VISIBLE);
            temperature.setText(Utils.celsiusToString(currentWeatherEntity.getTemp()));
            temperature.setVisibility(View.VISIBLE);
            windSpeed.setText(Double.toString(currentWeatherEntity.getWindSpeed()));
            windSpeed.setVisibility(View.VISIBLE);
            windDir.setRotation((int) currentWeatherEntity.getWindAngle());
            windDir.setVisibility(View.VISIBLE);
            humidity.setText(Integer.toString(currentWeatherEntity.getHumidity()));
            humidity.setVisibility(View.VISIBLE);
            pressure.setText(Integer.toString((int) currentWeatherEntity.getPressure()));
            pressure.setVisibility(View.VISIBLE);
            city.setText(cityName);
            city.setVisibility(View.VISIBLE);
        }
    }
}
