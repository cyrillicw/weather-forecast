package com.example.android.myapplication.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.example.android.myapplication.R;
import com.example.android.myapplication.ui.adapters.WeatherPageAdapter;
import com.example.android.myapplication.ui.fragments.CurrentWeatherFragment;
import com.example.android.myapplication.ui.fragments.ForecastFragment;
import com.example.android.myapplication.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        fillFragments();
    }

    private void fillFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Log.e("MainActivity", "Fragments size before" + fragmentManager.getFragments().size());
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
        Log.e("MainActivity", "Fragments size after " + fragmentManager.getFragments().size());
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.weather_layout, new CurrentWeatherFragment(), "current");
            fragmentTransaction.replace(R.id.forecast_layout, new ForecastFragment(), "forecast");
            fragmentTransaction.commit();
        }
        else {
            ViewPager viewPager = findViewById(R.id.pager);
            WeatherPageAdapter weatherPageAdapter = new  WeatherPageAdapter(getSupportFragmentManager());
            viewPager.setAdapter(weatherPageAdapter);
            viewPager.addOnPageChangeListener(new WeatherPageChangedListener());
            viewPager.setCurrentItem(viewModel.getViewPagerPosition(), false);
        }
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
