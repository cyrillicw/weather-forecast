package com.example.android.myapplication.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.android.myapplication.R;
import com.example.android.myapplication.adapters.WeatherPageAdapter;

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
