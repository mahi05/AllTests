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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.dropbox.core.v2.files.FileMetadata;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.mahii.alltests.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NewTabRideActivity extends AppCompatActivity implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {

    ImageView imageView;
    VideoView videoView;

    ArrayList<File> listFiles = new ArrayList<>();
    ArrayList<FileModel> mainModel = new ArrayList<>();
    MediaPlayer mP = new MediaPlayer();

    Timer timer;
    TimerTask timerTask;
    Handler handler = new Handler();
    ProgressDialog progressDialog;

    String TAG = "Hello";
    private FileMetadata mSelectedFile;
    int currentItem = -1;
    int downloadFlag = 0;

    File downloadPath;
    int mDownloadFlag = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabride);

        imageView = (ImageView) findViewById(R.id.imageView);
        videoView = (VideoView) findViewById(R.id.videoView);
        downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        getAds();

    }

    private void getAds() {

        progressDialog = new ProgressDialog(NewTabRideActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        CallService service = new CallService(NewTabRideActivity.this, "https://api.myjson.com/bins/2q5tg", "GET", new CallService.OnServiceCall() {
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

    private void performAction(FileAction action) {
        switch (action) {

            case DOWNLOAD:

                for (int i = 0; i < mainModel.size(); i++) {

                    String fileName = "";
                    String url = "";
                    FileModel fileModel = mainModel.get(i);

                    if (fileModel.getType() == 0) {
                        fileName = fileModel.getFile_name() + ".jpg";
                        url = fileModel.getImage();
                    } else if (fileModel.getType() == 1) {
                        fileName = fileModel.getFile_name() + ".mp4";
                        url = fileModel.getVideo();
                    }

                    Log.d("DownloadFile", fileName + "::" + url);

                    File fileToDownload = new File(downloadPath, fileName);

                    if (!fileToDownload.exists()) {

                        Ion.with(NewTabRideActivity.this)
                                .load(url)
                                .progress(new ProgressCallback() {
                                    @Override
                                    public void onProgress(long downloaded, long total) {
                                        Log.e("Downloading", "" + downloaded + " / " + total);
                                    }
                                })
                                .write(new File(downloadPath, fileName))
                                .setCallback(new FutureCallback<File>() {
                                    @Override
                                    public void onCompleted(Exception e, File file) {
                                        listFiles.add(file);
                                        mDownloadFlag++;
                                        if (mDownloadFlag == mainModel.size()) {
                                            startTimer();
                                        }
                                    }
                                });
                    } else {
                        listFiles.add(fileToDownload);
                    }

                    if (listFiles.size() == mainModel.size()) {
                        startTimer();
                    }
                }


                break;

            default:
                Log.e(TAG, "Can't perform unhandled file action: " + action);
        }
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
        timer.schedule(timerTask, 0, 5000);
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

        progressDialog.dismiss();

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

    /*public void playVideo(String path) {

        mP.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mP.setDataSource(path);
            mP.setOnErrorListener(this);
            mP.setOnPreparedListener(this);
            mP.prepareAsync();
        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        mP.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });
    }*/

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

}
