package com.mahii.alltests.FinishLoopCreateNew;

import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.mahii.alltests.R;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TabRideMostWorkedSolution extends AppCompatActivity {

    VideoView videoView;
    ImageView imageView;

    Timer timer;
    TimerTask timerTask;
    final Handler handler = new Handler();

    int currentItem = -1;

    ArrayList<File> namesList = new ArrayList<>();

    String[] paths = {
            "/storage/emulated/0/Download/abc1.jpg",
            "/storage/emulated/0/Download/2016_09_07_23_09_11_Chi Dynasty TV Commercial made by Mantashoff Production.mp4"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_ride_main_solution);

        videoView = (VideoView) findViewById(R.id.myVideoView);
        imageView = (ImageView) findViewById(R.id.imageView);

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "abc1.jpg.jpg");
        namesList.add(mediaStorageDir);

        File mediaStorageDir2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "2016_09_07_23_09_11_Chi Dynasty TV Commercial made by Mantashoff Production.mp4");
        namesList.add(mediaStorageDir2);

        startTimer();
    }

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 3000);
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        currentItem++;
                        showImage();
                    }
                });
            }
        };
    }

    public void showImage() {

        try {
            if (isImageFile(namesList.get(currentItem).getAbsolutePath())) {

                imageView.setImageBitmap(BitmapFactory.decodeFile(namesList.get(currentItem).getAbsolutePath()));

                videoView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);

                if (videoView.isPlaying()) {
                    videoView.stopPlayback();
                }

            } else if (isVideoFile(namesList.get(currentItem).getAbsolutePath())) {

                String uriPath = namesList.get(currentItem).getAbsolutePath();
                Uri uri = Uri.parse(uriPath);

                videoView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);

                timer.cancel();
                timerTask.cancel();

                videoView.setVideoURI(uri);
                videoView.requestFocus();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        videoView.start();
                    }
                });

                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        startTimer();
                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (currentItem == namesList.size()) {

            currentItem = -1;

            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);

            timer = null;
            timerTask.cancel();

            Toast.makeText(TabRideMostWorkedSolution.this, "Over", Toast.LENGTH_SHORT).show();
            startTimer();

        }
    }

    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.indexOf("image") == 0;
    }

    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.indexOf("video") == 0;
    }

}