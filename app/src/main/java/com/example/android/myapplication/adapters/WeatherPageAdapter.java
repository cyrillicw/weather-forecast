package com.example.android.myapplication.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.android.myapplication.fragments.CurrentWeatherFragment;
import com.example.android.myapplication.fragments.ForecastFragment;

public class WeatherPageAdapter extends FragmentPagerAdapter {
    private static final int pageNumber = 2;
    private Fragment[] pages;

    public WeatherPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        pages = new Fragment[pageNumber];
        pages[0] = new CurrentWeatherFragment();
        pages[1] = new ForecastFragment();
    }

    @Override
    public int getCount() {
        return pageNumber;
    }

    @Override
    public Fragment getItem(int i) {
        return pages[i];
    }
}
