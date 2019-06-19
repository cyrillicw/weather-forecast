package com.example.android.myapplication.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android.myapplication.R;
import com.example.android.myapplication.ui.adapters.ForecastAdapter;
import com.example.android.myapplication.data.database.entities.ForecastEntity;
import com.example.android.myapplication.viewmodels.ForecastViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {
    private RecyclerView recyclerView;
    private LiveData<List<ForecastEntity>> liveData;

    public ForecastFragment() {
        super();
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        recyclerView = view.findViewById(R.id.forecast_recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new ForecastAdapter(new ArrayList<ForecastEntity>()));
        ForecastViewModel viewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
        liveData = viewModel.getForecastData();
        Log.e("FORECAST", "ONCREATEVIEW");
        getContents();
        return view;
    }

    private void getContents() {
        liveData.observe(this, this::onDataChanged);
    }

    private void onDataChanged(List<ForecastEntity> forecastEntities) {
        ((ForecastAdapter)recyclerView.getAdapter()).setWeatherDays(forecastEntities);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
