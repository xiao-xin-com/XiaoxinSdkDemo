package com.example.myapplication;


import android.util.Log;

/**
 * Created by ljman on 2018/4/13.
 */

public class MyUn implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "UncaughtException";

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.d(TAG, "uncaughtException() called with: " +
                "t = [" + t.getName() + "], e = [" + e + "]");
        e.printStackTrace();
    }
}
