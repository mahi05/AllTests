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

    String[] links = {
            "https://s3.amazonaws.com/tabrideadvertisements/2016_09_30_22_09_52.mp4",
            "https://s3.amazonaws.com/tabrideadvertisements/2016_09_30_22_09_55.mp4",
            "https://s3.amazonaws.com/tabrideadvertisements/2016_09_30_22_09_20.mp4",
            "https://s3.amazonaws.com/tabrideadvertisements/2016_09_30_01_09_13.mp4"
    };

    String[] names = {
            "2016_10_01_12_39_00_1.mp4",
            "2016_10_01_12_39_00_2.mp4",
            "2016_10_01_12_39_00_3.mp4",
            "2016_10_01_12_39_00_4.mp4"
    };

    int m = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < links.length; i++) {

            Ion.with(IonDownloader.this)
                    .load(links[i])
                    .progress(new ProgressCallback() {
                        @Override
                        public void onProgress(long downloaded, long total) {
                            Log.e("Downloading", "" + downloaded + " / " + total);
                        }
                    })
                    .write(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), names[i]))
                    .setCallback(new FutureCallback<File>() {
                        @Override
                        public void onCompleted(Exception e, File file) {
                            //mCallback.onDownloadComplete(file);
                            Toast.makeText(IonDownloader.this, "Download No " + m, Toast.LENGTH_SHORT).show();
                            Log.e("Download No", "" + m);
                            m++;
                        }
                    });

        }

        /*Ion.with(IonDownloader.this)
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
                });*/

    }
}
