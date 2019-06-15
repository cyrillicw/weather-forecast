package com.example.android.myapplication.data.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "forecasts")
public class ForecastEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double tempMin;
    private double tempMax;
    private int weatherId;
    private Date date;
    private Calendar updateDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Calendar getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Calendar updateDate) {
        this.updateDate = updateDate;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null :date.getTime();
    }

    @TypeConverter
    public static Calendar toCalendar(Long dateLong){
        if (dateLong == null) {
            return null;
        }
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateLong);
            return calendar;
        }
    }

    @TypeConverter
    public static Long fromCalendar(Calendar calendar){
        if (calendar == null) {
            return null;
        }
        else {
            return calendar.getTimeInMillis();
        }
    }
}
