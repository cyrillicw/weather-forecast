package com.example.android.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.myapplication.pojo.detailedweatherday.WeatherDay;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastItemHolder> {
    private List<WeatherDay> weatherDays;

    public ForecastAdapter (List<WeatherDay> weatherDays) {
        this.weatherDays = weatherDays;
    }

    public void setWeatherDays(List<WeatherDay> weatherDays) {
        this.weatherDays = weatherDays;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ForecastItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.forecast_item, viewGroup, false);
        return new ForecastItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastItemHolder forecastItemHolder, int i) {
        forecastItemHolder.setContent(weatherDays.get(i));
    }

    @Override
    public int getItemCount() {
        return weatherDays.size();
    }

    class ForecastItemHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private ImageView icon;
        private TextView minTemp;
        private TextView maxTemp;
        public ForecastItemHolder(View view) {
            super(view);
            date = view.findViewById(R.id.forecast_date);
            icon = view.findViewById(R.id.forecast_icon);
            minTemp = view.findViewById(R.id.forecast_min_temp);
            maxTemp = view.findViewById(R.id.forecast_max_temp);
        }

        void setContent(WeatherDay weatherDay) {
            this.date.setText(Utils.dateToString(weatherDay.getDt()));
            this.icon.setImageResource(Utils.getWeatherIcon(weatherDay.getWeather().get(0).getId()));
            this.minTemp.setText(Utils.celsiusToString((int)weatherDay.getMain().getTempMin()));
            this.maxTemp.setText(Utils.celsiusToString((int)weatherDay.getMain().getTempMax()));
        }
    }
}
