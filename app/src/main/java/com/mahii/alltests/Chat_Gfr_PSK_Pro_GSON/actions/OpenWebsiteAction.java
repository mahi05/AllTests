package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.GfrMainActivity;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Action;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Message;

@SuppressWarnings("unused")
public class OpenWebsiteAction implements IAction {
    @Override
    public void run(Action action, GfrMainActivity activityContext, Context applicationContext, Message message) {
        Log.d("OpenWebsiteAction", "1");
        String url = action.details.getUrl();
        Log.d("OpenWebsiteAction", "url = " + url);
        if (url.startsWith("http")) {
            Log.d("OpenWebsiteAction", "2");
            Intent openWebsiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            Log.d("OpenWebsiteAction", "3");
            activityContext.startActivity(openWebsiteIntent);
            Log.d("OpenWebsiteAction", "4");
        }
    }
}
