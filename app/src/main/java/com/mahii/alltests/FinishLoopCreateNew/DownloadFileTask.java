package com.mahii.alltests.FinishLoopCreateNew;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

class DownloadFileTask extends AsyncTask<Void, Void, File> {

    private final Context mContext;
    private final Callback mCallback;
    private Exception mException;
    private FileModel fileModel;
    long total = 0;

    DownloadFileTask(Context context, FileModel fileModel, Callback callback) {
        mContext = context;
        mCallback = callback;
        this.fileModel = fileModel;
    }

    @Override
    protected File doInBackground(Void... params) {

        /*FileMetadata metadata = params[0];
        long sizeOnServer = metadata.getSize();*/

        try {
            String fileName = "";
            String url = "";

            if (fileModel.getType() == 0) { // it is a image
                fileName = fileModel.getFile_name() + ".jpg";
                url = fileModel.getImage();
            } else if (fileModel.getType() == 1) { // it is a video
                fileName = fileModel.getFile_name() + ".mp4";
                url = fileModel.getVideo();
            }

            Log.d("DownloadFile", fileName + "::" + url);

            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);
            File file = new File(path, fileName);

            /* Make sure the Downloads directory exists. */
            if (!path.exists()) {
                if (!path.mkdirs()) {
                    mException = new RuntimeException("Unable to create directory: " + path);
                }
            } else if (!path.isDirectory()) {
                mException = new IllegalStateException("Download path is not a directory: " + path);
                return null;
            }

            /* Download the file. */
            if (!file.exists()) {
                downLoadFile(url, file);
            }

            /* Tell android about the file */
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            mContext.sendBroadcast(intent);

            return file;
        } catch (Exception e) {
            mException = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(File result) {
        super.onPostExecute(result);
        if (mException != null) {
            mCallback.onError(mException);
        } else {
            mCallback.onDownloadComplete(result);
        }
    }

    public interface Callback {
        void onDownloadComplete(File result);
        void onError(Exception e);
    }

    private synchronized void downLoadFile(String urlStr, File file) {
        int count;
        try {
            urlStr = urlStr.replace(" ", "%20");
            URL url = new URL(urlStr);
            URLConnection conection = url.openConnection();
            conection.connect();

            /* this will be useful so that you can show a tipical 0-100% progress bar */
            int lengthOfFile = conection.getContentLength();

            /* download the file */
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            /* Output stream */
            OutputStream output = new FileOutputStream(file);

            byte data[] = new byte[1024];

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called

                // writing data to file
                output.write(data, 0, count);
            }

            /* flushing output */
            output.flush();

            /* closing streams */
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

}
