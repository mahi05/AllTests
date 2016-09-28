package com.mahii.alltests;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mahii.alltests.animations.FoldAnimation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EasyGovLoginUsingVolley extends AppCompatActivity {

    EditText tv_email, tv_pw;
    Button btn_login;
    FoldAnimation foldAnimation;

    String url = "See gst file";
    ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easygov_login);

        tv_email = (EditText) findViewById(R.id.tv_email);
        tv_pw = (EditText) findViewById(R.id.tv_pw);
        btn_login = (Button) findViewById(R.id.btn_login);

        /*Animation bottomUp3 = AnimationUtils.loadAnimation(EasyGovLoginUsingVolley.this, R.anim.bottom_to_up3);
        LinearLayout hiddenPanel3 = (LinearLayout) findViewById(R.id.upPart);
        hiddenPanel3.startAnimation(bottomUp3);
        hiddenPanel3.setVisibility(View.VISIBLE);*/

        /*Animation bottomUp = AnimationUtils.loadAnimation(EasyGovLoginUsingVolley.this, R.anim.bottom_to_up4);
        LinearLayout hiddenPanel = (LinearLayout) findViewById(R.id.middlePart);
        hiddenPanel.startAnimation(bottomUp);
        hiddenPanel.setVisibility(View.VISIBLE);

        Animation bottomUp2 = AnimationUtils.loadAnimation(EasyGovLoginUsingVolley.this, R.anim.bottom_to_up5);
        LinearLayout hiddenPanel2 = (LinearLayout) findViewById(R.id.lastPart);
        hiddenPanel2.startAnimation(bottomUp2);
        hiddenPanel2.setVisibility(View.VISIBLE);

        Animation tv11 = AnimationUtils.loadAnimation(EasyGovLoginUsingVolley.this, R.anim.bottom_to_up3);
        tv_email.startAnimation(tv11);
        tv_email.setVisibility(View.VISIBLE);

        Animation tv22 = AnimationUtils.loadAnimation(EasyGovLoginUsingVolley.this, R.anim.bottom_to_up);
        tv_pw.startAnimation(tv22);
        tv_pw.setVisibility(View.VISIBLE);

        Animation b11 = AnimationUtils.loadAnimation(EasyGovLoginUsingVolley.this, R.anim.bottom_to_up2);
        btn_login.startAnimation(b11);
        btn_login.setVisibility(View.VISIBLE);*/

        /*BounceInterpolator bounceInterpolator = new BounceInterpolator();

        foldAnimation = new FoldAnimation(FoldAnimation.FoldAnimationMode.UNFOLD_DOWN, 1500);
        foldAnimation.withInterpolator(bounceInterpolator);
        foldAnimation.setRepeatCount(0);
        btn_login.setAnimation(foldAnimation);*/

        progressDialog = new ProgressDialog(EasyGovLoginUsingVolley.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tv_email.getText().toString().equals("") && !tv_pw.getText().toString().equals("")) {

                    progressDialog.show();
                    makeCall();

                }
            }
        });
    }

    public void makeCall() {

        Map<String, Object> jsonParams = new HashMap<>();

        //jsonParams.put("email", tv_email.getText().toString()); // Parameter 1
        //jsonParams.put("password", tv_pw.getText().toString()); // Parameter 2
        //jsonParams.put("device_token", "1234567890"); // Parameter 3



        RequestQueue queue = Volley.newRequestQueue(EasyGovLoginUsingVolley.this);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,

                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressDialog.dismiss();

                        Log.e("Res", response.toString());
                        try {
                            String error = response.getString("error");

                            if (error.equals("false")) {

                                Toast.makeText(EasyGovLoginUsingVolley.this, "Login successfully done!", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(EasyGovLoginUsingVolley.this, "There is some error occurred while login in ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Handle Error
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        queue.add(postRequest);

    }

}
