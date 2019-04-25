package com.example.android.myapplication.pojo.detailedweatherday;

public class Wind
{
    private double speed;

    private double deg;

    public void setSpeed(double speed){
        this.speed = speed;
    }
    public double getSpeed(){
        return this.speed;
    }
    public void setDeg(int deg){
        this.deg = deg;
    }
    public double getDeg(){
        return this.deg;
    }
}