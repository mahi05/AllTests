package com.mahii.alltests.FinishLoopCreateNew;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.dropbox.core.v2.sharing.FileAction;
import com.mahii.alltests.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TimerAndTimerTask extends AppCompatActivity {

    Timer timer;
    TimerTask timerTask;
    final Handler handler = new Handler();

    int currentItem = -1;

    //TextView textView, textView2;
    VideoView videoView;
    ImageView imageView;
    /* ArrayLists & HashMaps */
    ArrayList<File> listFiles = new ArrayList<>();
    ArrayList<FileModel> mainModel= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_ride_main_solution);

        //textView = (TextView) findViewById(R.id.textView);
        //textView2 = (TextView) findViewById(R.id.textView2);

        imageView = (ImageView) findViewById(R.id.imageView);
        videoView = (VideoView) findViewById(R.id.myVideoView);

        //startTimer();
        getAds();
    }

    private void getAds() {

        CallService service = new CallService(TimerAndTimerTask.this, "See gst file"/* + lastZip*/, "POST", new CallService.OnServiceCall() {
            @Override
            public void onServiceCall(String response) {
                try {
                    try {
                        if (mainModel.size() > 0) {
                            mainModel.clear();
                            listFiles.clear();
                            timer = null;
                            timerTask.cancel();
                        }
                        startTimer();
                        mainModel.addAll(JSON_PARSER(response));
                        performWithPermissions(FileAction.DOWNLOAD);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        service.execute();
    }

    private synchronized ArrayList<FileModel> JSON_PARSER(String response) throws Exception {
        JSONObject jObj = new JSONObject(response);
        JSONArray jArr = jObj.getJSONArray("data");
        ArrayList<FileModel> mainModel = new ArrayList<>();
        for (int i = 0; i < jArr.length(); i++) {
            JSONObject innerJObj = jArr.getJSONObject(i);
            FileModel model = new FileModel();
            model.setId(innerJObj.getString("id"));
            model.setClient_name(innerJObj.getString("client_name"));
            model.setImage(innerJObj.getString("image"));
            model.setVideo(innerJObj.getString("video"));
            model.setTime(innerJObj.getString("time"));
            model.setPriority(innerJObj.getString("priority"));
            model.setFile_name(innerJObj.getString("file_name"));

            if (model.getImage().equalsIgnoreCase("")) {
                model.setType(1);
            } else if (model.getVideo().equalsIgnoreCase("")) {
                model.setType(0);
            }

            mainModel.add(model);
        }
        return mainModel;
    }

    private void performWithPermissions(final FileAction action) {
        if (hasPermissionsForAction(action)) {
            performAction(action);
            return;
        }
        requestPermissionsForAction(action);
    }

    private boolean hasPermissionsForAction(FileAction action) {
        for (String permission : action.getPermissions()) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissionsForAction(FileAction action) {
        ActivityCompat.requestPermissions(
                this,
                action.getPermissions(),
                action.getCode()
        );
    }

    private void performAction(FileAction action) {
        switch (action) {

            case DOWNLOAD:
                for (int i = 0; i < mainModel.size(); i++) {
                    downloadFile(mainModel.get(i));
                }

                break;

            default:
                Log.e("TT", "Can't perform unhandled file action: " + action);
        }
    }

    private void downloadFile(final FileModel model) {
        new DownloadFileTask(TimerAndTimerTask.this, model, new DownloadFileTask.Callback() {
            @Override
            public void onDownloadComplete(File result) {

                if (result != null) {
                    boolean add = listFiles.add(result);
                    Log.e("Size", "" + listFiles.size());
                    if (timer == null) {
                        startTimer();
                    } else if (currentItem == listFiles.size() - 1) {
                        startTimer();
                        showImage();
                        Log.e("FilesActivity", "onDownloadComplete: " + add);
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(TimerAndTimerTask.this, "An error has occurred", Toast.LENGTH_SHORT).show();
            }

        }).execute();
    }

    public enum FileAction {
        DOWNLOAD(Manifest.permission.WRITE_EXTERNAL_STORAGE),
        UPLOAD(Manifest.permission.READ_EXTERNAL_STORAGE);

        private static final FileAction[] values = values();

        private final String[] permissions;

        FileAction(String... permissions) {
            this.permissions = permissions;
        }

        public static FileAction fromCode(int code) {
            if (code < 0 || code >= values.length) {
                throw new IllegalArgumentException("Invalid FileAction code: " + code);
            }
            return values[code];
        }

        public int getCode() {
            return ordinal();
        }

        public String[] getPermissions() {
            return permissions;
        }
    }

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 10000);
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        currentItem++;
                        Log.e("CurrentItem", "" + currentItem);
                        //showImage();
                        //showImage2();

                        showImage();
                    }
                });
            }
        };
    }

    /*public void showImage() {
        textView.setText(String.format("%d", currentItem));
        if (currentItem == 10) {
            Toast.makeText(TimerAndTimerTask.this, "Over1", Toast.LENGTH_SHORT).show();
            timer = null;
            timerTask.cancel();
            currentItem = -1;
            startTimer();
        }
    }

    public void showImage2() {
        textView2.setText(String.format("%d", currentItem));
        if (currentItem == 5) {
            Toast.makeText(TimerAndTimerTask.this, "Over2", Toast.LENGTH_SHORT).show();
            timer = null;
            timerTask.cancel();
            currentItem = -1;
            startTimer();
        }
    }*/

    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.indexOf("image") == 0;
    }

    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.indexOf("video") == 0;
    }

    public void showImage() {


        try {
            if (isImageFile(listFiles.get(currentItem).getAbsolutePath())) {

                imageView.setImageBitmap(BitmapFactory.decodeFile(listFiles.get(currentItem).getAbsolutePath()));
                videoView.setVisibility(View.GONE);

                imageView.setVisibility(View.VISIBLE);
                if (videoView.isPlaying()) {
                    videoView.stopPlayback();
                }

            } else if (isVideoFile(listFiles.get(currentItem).getAbsolutePath())) {

                File mediaStorageDir = new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        listFiles.get(currentItem).getName()
                );
                String uriPath = mediaStorageDir.getAbsolutePath();
                Uri uri = Uri.parse(uriPath);

                /*String uriPath = listFiles.get(currentItem).getAbsolutePath();
                Uri uri = Uri.parse(uriPath);*/

                videoView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);

                timer = null;
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

        if (currentItem == mainModel.size()) {

            /* Set Current item to its default value */
            currentItem = -1;

            /* Removes all cancelled or running tasks from this timer's task queue. */
            if (timer != null) {
                timer.cancel();
                timer.purge();
            }
            timer = null;
            timerTask.cancel();

            /* Clear All Lists related to ads */
            listFiles.clear();
            mainModel.clear();

            if (videoView.isPlaying()) {
                videoView.stopPlayback();
                videoView.setVisibility(View.GONE);
            }

            /* Call Again */
            /*if (listFiles.size() == 0 && mainModel.size() == 0 && timer == null)
                getAds();*/

        }

    }

}
