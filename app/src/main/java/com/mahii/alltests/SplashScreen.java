package com.mahii.alltests;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.mahii.alltests.CameraTest.ChooseImageFromGallery;

public class SplashScreen extends AppCompatActivity {

    public static final int permsRequestCode = 200;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        if (shouldAskPermission()) {
            if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.ACCESS_FINE_LOCATION"};
                requestPermissions(perms, permsRequestCode);

            } else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreen.this, ChooseImageFromGallery.class));
                        finish();
                    }
                }, 3000);
            }
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreen.this, ChooseImageFromGallery.class));
                    finish();
                }
            }, 3000);
        }

    }

    private boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        switch (requestCode) {
            case permsRequestCode: {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashScreen.this, ChooseImageFromGallery.class));
                            finish();
                        }
                    }, 3000);
                } else {
                    String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.ACCESS_FINE_LOCATION"};
                    requestPermissions(perms, permsRequestCode);
                }
            }
        }
    }
}
