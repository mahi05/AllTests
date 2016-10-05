package com.mahii.alltests.RestartAppAfterCrash;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mahii.alltests.SplashScreen;
import com.mahii.alltests.app.AppController;

import java.lang.Thread.UncaughtExceptionHandler;

public class DefaultExceptionHandler implements UncaughtExceptionHandler {

    private UncaughtExceptionHandler defaultUEH;
    Activity activity;

    public DefaultExceptionHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        Intent intent = new Intent(activity, SplashScreen.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                AppController.getInstance().getApplicationContext(), 0, intent, intent.getFlags());

        //Following code will restart your application after 2 seconds
        AlarmManager mgr = (AlarmManager) AppController.getInstance().getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, pendingIntent);

        //This will finish your activity manually
        activity.finish();

        //This will stop your application and take out from it.
        System.exit(2);

    }

}
