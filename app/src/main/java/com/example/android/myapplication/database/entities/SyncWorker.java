package com.example.android.myapplication.database.entities;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SyncWorker extends Worker {

    public SyncWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
    }
    @NonNull
    @Override
    public Result doWork() {
        return null;
    }
}
