package com.example.android.myapplication.data;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.android.myapplication.data.Repository;

public class SyncWorker extends Worker {

    public SyncWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
    }
    @NonNull
    @Override
    public Result doWork() {
        Repository.REPOSITORY.forceCurrentWeatherUpdate();
        Repository.REPOSITORY.forceForecastUpdate();
        return Result.success();
    }
}
