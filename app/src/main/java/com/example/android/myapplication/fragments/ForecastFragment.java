package com.example.android.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android.myapplication.R;
import com.example.android.myapplication.adapters.ForecastAdapter;
import com.example.android.myapplication.database.entities.detailedweatherday.ForecastEntity;
import com.example.android.myapplication.database.Repository;

import java.util.ArrayList;
import java.util.Calendar;
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
        liveData = new MutableLiveData<>();
        Log.e("IN ON CREATE", Thread.currentThread().getName());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getContents();
    }

    private void getContents() {
        final Repository repository = Repository.getInstance(getActivity());
        liveData = repository.getForecast();
        liveData.observe(this, new Observer<List<ForecastEntity>>() {
            @Override
            public void onChanged(List<ForecastEntity> forecastEntities) {
                Calendar minCalendar = Calendar.getInstance();
                minCalendar.add(Calendar.HOUR_OF_DAY, -1);
                if (forecastEntities.isEmpty() || forecastEntities.get(0).getUpdateDate().before(minCalendar)) {
                    repository.forceForecastUpdate();
                    Log.e("FORECAST", "IN IF");
                }
                else {
                    ((ForecastAdapter)recyclerView.getAdapter()).setWeatherDays(forecastEntities);
                    Log.e("FORECAST", "IN ELSE");
                }
            }
        });
    }
}
