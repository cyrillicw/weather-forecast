package com.example.android.myapplication.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import com.example.android.myapplication.R;
import com.example.android.myapplication.ui.adapters.WeatherPageAdapter;
import com.example.android.myapplication.ui.fragments.CurrentWeatherFragment;
import com.example.android.myapplication.ui.fragments.ForecastFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int orientation = getResources().getConfiguration().orientation;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentTransaction.add(R.id.weather_layout, new CurrentWeatherFragment());
            fragmentTransaction.add(R.id.forecast_layout, new ForecastFragment());
        }
        else {
            ViewPager viewPager = findViewById(R.id.pager);
            WeatherPageAdapter weatherPageAdapter = new WeatherPageAdapter(getSupportFragmentManager());
            viewPager.setAdapter(weatherPageAdapter);
        }
        fragmentTransaction.commit();
    }
}
