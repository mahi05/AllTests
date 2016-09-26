package com.mahii.alltests.FinishLoopCreateNew;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;

public class Downloader extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading");
        progressDialog.show();

        Ion.with(this)
                .load("https://s3.amazonaws.com/tabrideadvertisements/2016_09_14_01_09_51_SampleVideo.mp4")
                .progressDialog(progressDialog)
                .progress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        //System.out.println("" + downloaded + " / " + total);
                        Log.e("Total", "" + downloaded + " / " + total);
                    }
                })
                .write(new File("/sdcard/xyz.mp4"))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {
                        progressDialog.dismiss();
                    }
                });

    }
}
