package com.mahii.alltests.FinishLoopCreateNew;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.dropbox.core.v2.files.FileMetadata;
import com.mahii.alltests.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FinishAndCreateActivity extends AppCompatActivity {

    ArrayList<File> listFiles = new ArrayList<>();
    ArrayList<FileModel> mainModel = new ArrayList<>();

    Timer timer;
    TimerTask timerTask;

    private FileMetadata mSelectedFile;
    private static final int PICK_FILE_REQUEST_CODE = 1;
    public final static String EXTRA_PATH = "FilesActivity_Path";

    int currentItem = -1;
    String TAG = "Hello", mPath;
    final Handler handler = new Handler();

    ImageView imageView;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_and_start);

        String path = getIntent().getStringExtra(EXTRA_PATH);
        mPath = path == null ? "" : path;

        imageView = (ImageView) findViewById(R.id.imageView);
        videoView = (VideoView) findViewById(R.id.videoView);

        getAds();

    }

    private void getAds() {

        CallService service = new CallService(FinishAndCreateActivity.this, "https://api.myjson.com/bins/44biq", "GET", new CallService.OnServiceCall() {
            @Override
            public void onServiceCall(String response) {
                try {

                    if (mainModel.size() > 0) {

                        mainModel.clear();
                        listFiles.clear();

                        timer.cancel();
                        timerTask.cancel();

                        if (timer == null)
                            startTimer();

                        mainModel.addAll(JSON_PARSER(response));
                        performWithPermissions(FileAction.DOWNLOAD);
                    } else {
                        mainModel.addAll(JSON_PARSER(response));
                        performWithPermissions(FileAction.DOWNLOAD);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        service.execute();
    }

    private synchronized ArrayList<FileModel> JSON_PARSER(String response) throws Exception {

        ArrayList<FileModel> mainModel = new ArrayList<>();

        JSONObject jObj = new JSONObject(response);
        JSONArray jArr = jObj.getJSONArray("data");

        for (int i = 0; i < jArr.length(); i++) {
            JSONObject innerJObj = jArr.getJSONObject(i);
            FileModel model = new FileModel();
            model.setId(innerJObj.getString("id"));
            model.setClient_name(innerJObj.getString("client_name"));
            model.setImage(innerJObj.getString("image"));
            model.setVideo(innerJObj.getString("video"));
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

    private void performAction(FileAction action) {
        switch (action) {

            case DOWNLOAD:
                for (int i = 0; i < mainModel.size(); i++) {
                    downloadFile(mainModel.get(i));
                }

                if (mSelectedFile != null) {
                    Log.e(TAG, "Files Downloaded.");
                } else {
                    Log.e(TAG, "No file selected to download.");
                }

                break;

            default:
                Log.e(TAG, "Can't perform unhandled file action: " + action);
        }
    }

    private void downloadFile(final FileModel model) {
        new DownloadFileTask(FinishAndCreateActivity.this, model, new DownloadFileTask.Callback() {
            @Override
            public void onDownloadComplete(File result) {

                if (result != null) {
                    final boolean add = listFiles.add(result);
                    Log.e("Size", "" + listFiles.size());

                    if (timer == null) {
                        startTimer();
                    } else {
                        Log.e("List Ni Size", "" + listFiles.size());
                        if (currentItem == listFiles.size() - 1) {
                            startTimer();
                            showImage();
                            Log.e(TAG, "onDownloadComplete: " + add);
                        }
                    }

                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Failed to download file.", e);
                Toast.makeText(FinishAndCreateActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
            }

        }).execute();
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

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 15000 /*2000*/);
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        currentItem++;
                        Log.e("Current Item is", "" + currentItem);
                        showImage();
                    }
                });
            }
        };
    }

    private enum FileAction {
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

                String uriPath = listFiles.get(currentItem).getAbsolutePath();
                Uri uri = Uri.parse(uriPath);

                videoView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);

                videoView.setVideoURI(uri);
                videoView.start();

                timer.cancel();
                timerTask.cancel();

                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        startTimer();
                    }
                });
            }

        } catch (Exception e) {
                timerTask.cancel();
                timer.cancel();

        }

        if (currentItem == mainModel.size()) {

            listFiles.clear();
            mainModel.clear();

                timer.cancel();
                timer = null;
                timerTask.cancel();


            videoView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            if (videoView.isPlaying()) {
                videoView.stopPlayback();
            }
            currentItem = -1;

            getAds();
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
