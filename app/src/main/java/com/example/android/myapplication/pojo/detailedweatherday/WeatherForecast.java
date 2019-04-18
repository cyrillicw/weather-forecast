package com.example.android.myapplication.pojo.detailedweatherday;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MultipleWeatherForecast {

    private String cod;
    private double message;
    private Integer cnt;
    @SerializedName("list")
    private List<WeatherDay> list = null;
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

    public java.util.List<WeatherDay> getList() {
        return list;
    }

    public void setList(java.util.List<WeatherDay> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
