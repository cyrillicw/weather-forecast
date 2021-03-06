package com.example.android.myapplication.viewmodels;

import androidx.lifecycle.ViewModel;
import com.example.android.myapplication.data.Repository;

public class MainViewModel extends ViewModel {
    private int viewPagerPosition;

    public MainViewModel() {
        viewPagerPosition = 0;
    }

    public int getViewPagerPosition() {
        return viewPagerPosition;
    }

    public void setViewPagerPosition(int viewPagerPosition) {
        this.viewPagerPosition = viewPagerPosition;
    }
}
