package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.actions.ActionFactory;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.actions.IAction;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.models.ChatMessages;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Action;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Message;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.MessageRequest;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.MessageResponse;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Pro;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sqlHelpers.SQLiteHelper;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.utils.StringUtil;
import com.mahii.alltests.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GfrMainActivity extends AppCompatActivity {

    /* For SQLite */
    SQLiteHelper helper;

    Button sendMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gfr);

        helper = new SQLiteHelper(GfrMainActivity.this);
        helper.open();

        sendMessage = (Button) findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage("Find accounts near san diego");
            }
        });

    }

    public void sendMessage(final String text) {

        ChatMessages chatMessages = new ChatMessages();
        chatMessages.setMessage_type("text");
        chatMessages.setMessage_from(true);
        chatMessages.setMessage_text(text);
        chatMessages.setMessage_image("");
        chatMessages.setShow_card("");

        if (helper.insertData(StringUtil.getStringPreference(GfrMainActivity.this, StringUtil.LOGGED_IN_USER_EMAIL), "text", 1, text, "", "") == 1) {
            Log.e("Data", "InsertData Successfully");
        } else {
            Log.e("Data", "Error in insert data");
        }

        RequestQueue queue = Volley.newRequestQueue(GfrMainActivity.this);
        String url = Pro.baseURL + "/messages";

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setText(text);
        messageRequest.setSourceId(1);

        JSONObject jsonBody = messageRequest.toJsonObject();

        final ProgressDialog progressDialog = new ProgressDialog(GfrMainActivity.this);
        progressDialog.setMessage("Thinking...");
        progressDialog.show();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                Gson gson = new Gson();
                Message message = gson.fromJson(response.toString(), Message.class);

                if (message.goferReplyText != null) {

                    ChatMessages chatMessages = new ChatMessages();
                    chatMessages.setMessage_type("text");
                    chatMessages.setMessage_from(false);
                    chatMessages.setMessage_text(message.goferReplyText);
                    chatMessages.setMessage_image("");
                    chatMessages.setShow_card("");

                    if (helper.insertData(
                            StringUtil.getStringPreference(GfrMainActivity.this, StringUtil.LOGGED_IN_USER_EMAIL), "text", 2, message.goferReplyText, "", "") == 1) {
                        Log.e("Data", "InsertData Successfully");
                    } else {
                        Log.e("Data", "Error in insert data");
                    }

                } else if (message.messageIntent != null && message.messageIntent.intent != null && message.messageIntent.intent.intentName != null) {
                    Log.e("Image", "" + message.messageIntent.intent.intentName + System.getProperty("line.separator"));
                }

                if (message.messageResponseList != null && message.messageResponseList.size() >= 1) {
                    for (MessageResponse messageResponse : message.messageResponseList) {
                        Action action = messageResponse.action;
                        ActionFactory actionFactory = new ActionFactory(action);
                        IAction actionClass = actionFactory.build();
                        actionClass.run(action, GfrMainActivity.this, getApplicationContext(), message);
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Problem contacting Pro - " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //See headers at gst file
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

}
