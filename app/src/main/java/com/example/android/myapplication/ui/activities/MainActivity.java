package com.example.android.myapplication.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.example.android.myapplication.R;
import com.example.android.myapplication.ui.adapters.WeatherPageAdapter;
import com.example.android.myapplication.ui.fragments.CurrentWeatherFragment;
import com.example.android.myapplication.ui.fragments.ForecastFragment;
import com.example.android.myapplication.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
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
            viewPager.addOnPageChangeListener(new WeatherPageChangedListener());
            viewPager.setCurrentItem(viewModel.getViewPagerPosition(), false);
        }
        fragmentTransaction.commit();
    }

    private class WeatherPageChangedListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            viewModel.setViewPagerPosition(position);
        }
        @Override
        public void onPageScrollStateChanged(int state) {}
    }
}
