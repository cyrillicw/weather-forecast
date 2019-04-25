package com.example.android.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
