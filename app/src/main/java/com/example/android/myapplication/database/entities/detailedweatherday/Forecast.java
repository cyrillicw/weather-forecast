package com.example.android.myapplication.database.entities.detailedweatherday;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {

    private String cod;
    private double message;
    private Integer cnt;
    @SerializedName("list")
    private List<WeatherDay> weatherDays;
    private City city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public List<WeatherDay> getWeatherDays() {
        return weatherDays;
    }

    public void setWeatherDays(List<WeatherDay> weatherDays) {
        this.weatherDays = weatherDays;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
