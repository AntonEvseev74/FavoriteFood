package ru.evant.favoritefood.db;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {
    private static AppExecutor instance;
    private final Executor mainIO;
    private final Executor subIO;

    private AppExecutor(Executor mainIO, Executor subIO) {
        this.mainIO = mainIO;
        this.subIO = subIO;
    }

    public static AppExecutor getInstance(){
        if (instance == null) instance = new AppExecutor(new MainThreadHandler(), Executors.newSingleThreadExecutor());
        return instance;
    }

    public Executor getMainIO() {
        return mainIO;
    }

    public Executor getSubIO() {
        return subIO;
    }

    public static class MainThreadHandler implements Executor {
        private Handler mainHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainHandler.post(command);
        }
    }
}
