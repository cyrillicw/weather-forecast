package com.example.android.myapplication.ui.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.android.myapplication.ui.fragments.CurrentWeatherFragment;
import com.example.android.myapplication.ui.fragments.ForecastFragment;

public class WeatherPageAdapter extends FragmentPagerAdapter {
    private static final int pageNumber = 2;

    public WeatherPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return pageNumber;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new CurrentWeatherFragment();
        }
        else {
            return new ForecastFragment();
        }
    }
}
