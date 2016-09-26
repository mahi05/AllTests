package com.mahii.alltests.FinishLoopCreateNew;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;

public class IonDownloader extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Ion.with(IonDownloader.this)
                .load("https://s3.amazonaws.com/tabrideadvertisements/2016_09_14_23_09_43.mp4")
                .progress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        Log.e("Downloading", "" + downloaded + " / " + total);
                    }
                })
                .write(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "2016_09_14_23_09_43.mp4"))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {
                        Toast.makeText(IonDownloader.this, "Download Complete", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
