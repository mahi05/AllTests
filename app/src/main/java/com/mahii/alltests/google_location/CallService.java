package com.mahii.alltests.google_location;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallService extends AsyncTask<Void, String, String> {

    OnServiceCall OnServiceCall;
    Context context;
    String urlStr;
    ProgressDialog dialog;
    String method = "";

    public interface OnServiceCall {
        void onServiceCall(String response);
    }

    public CallService(Context context, String urlStr, String method, OnServiceCall OnServiceCall) {
        this.context = context;
        this.urlStr = urlStr;
        this.OnServiceCall = OnServiceCall;
        this.method = method;
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Loading");
        dialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        return getData(method);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        dialog.dismiss();
        OnServiceCall.onServiceCall(result);
    }

    private String getData(String mehtod) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(mehtod);
            conn.setDoInput(true);
            //conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            conn.connect();

			/*if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}*/

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuffer response = new StringBuffer();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            conn.disconnect();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
