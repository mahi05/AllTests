package com.mahii.alltests.FinishLoopCreateNew;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallService extends AsyncTask<Void, String, String> {

	OnServiceCall OnServiceCall;
	Context context;
	String urlStr;
	Uri.Builder values;
	String method = "";
	ProgressDialog progressDialog;

	public interface OnServiceCall {
		void onServiceCall(String response);
	}

	public CallService(Context context, String urlStr, String method, OnServiceCall OnServiceCall) {
		this.context = context;
		this.urlStr = urlStr;
		this.OnServiceCall = OnServiceCall;
		this.method = method;
	}

	public CallService(Context context, String urlStr, String method, Uri.Builder values, OnServiceCall OnServiceCall) {
		this.context = context;
		this.urlStr = urlStr;
		this.OnServiceCall = OnServiceCall;
		this.method = method;
		this.values = values;
	}

	@SuppressLint("InflateParams")
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Loading");
		progressDialog.setCancelable(true);
		progressDialog.show();
	}

	@Override
	protected String doInBackground(Void... params) {
		return getData(method);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		progressDialog.dismiss();
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
