package com.mahii.alltests.RestartAppAfterCrash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class RestartAppAfterCrash extends AppCompatActivity {

    int a = 2;
    int b = 0;
    int c;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(this));

        c = a / b;
        Log.e("Crash", "" + c);

    }
}
