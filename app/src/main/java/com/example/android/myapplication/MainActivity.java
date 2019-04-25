package com.example.android.myapplication;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.pager);
        WeatherPageAdapter weatherPageAdapter = new WeatherPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(weatherPageAdapter);
    }
}
