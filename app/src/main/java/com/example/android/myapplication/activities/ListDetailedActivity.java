package com.example.android.myapplication.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import com.example.android.myapplication.R;
import com.example.android.myapplication.fragments.DetailedFragment;

public class ListDetailedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detailed);
        int orientation = getResources().getConfiguration().orientation;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            fragmentTransaction.add(R.id.layout_list, new ListFragment());
        }
        else {
            fragmentTransaction.add(R.id.layout_list, new ListFragment());
            fragmentTransaction.add(R.id.layout_detailed, new DetailedFragment());
        }
        fragmentTransaction.commit();
    }
}
