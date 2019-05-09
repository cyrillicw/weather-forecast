package com.example.android.myapplication.utils;

import com.example.android.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Utils {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:mm dd.MM", Locale.getDefault());
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    public static int getWeatherIcon(int weatherId) {
        final int THUNDER = 2;
        final int DRIZZLE = 3;
        final int RAIN = 5;
        final int SNOW = 6;
        final int ATMOSPHERE = 7;
        final int SUN = 800;
        final int SUNNY_CLOUDS = 801;
        int group = weatherId / 100;
        int iconId;
        switch (group) {
            case THUNDER:
                iconId = R.drawable.thunder;
                break;
            case DRIZZLE:
                iconId = R.drawable.drizzle;
                break;
            case RAIN:
                iconId = R.drawable.rain;
                break;
            case SNOW:
                iconId = R.drawable.snow;
                break;
            case ATMOSPHERE:
                iconId = R.drawable.atmosphere;
                break;
            default:
                if (weatherId == SUN) {
                    iconId = R.drawable.sun;
                }
                else if (weatherId == SUNNY_CLOUDS) {
                    iconId = R.drawable.sunny_clouds;
                }
                else {
                    iconId = R.drawable.clouds;
                }
        }
        return iconId;
    }

    public static String celsiusToString(double degrees) {
        int iDegrees = (int)Math.round(degrees);
        String start = iDegrees > 0 ? "+" : "";
        return start + iDegrees + "°C";
    }

    public static String dateToString(Date date) {
        return simpleDateFormat.format(date);
    }
}
