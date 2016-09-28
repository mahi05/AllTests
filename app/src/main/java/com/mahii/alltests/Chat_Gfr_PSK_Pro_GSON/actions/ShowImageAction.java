package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.actions;

import android.content.Context;
import android.content.Intent;

import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.GfrMainActivity;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Action;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Message;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sqlHelpers.SQLiteHelper;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.utils.StringUtil;

@SuppressWarnings("unused")
public class ShowImageAction implements IAction {

    @Override
    public void run(Action action, GfrMainActivity activityContext, Context applicationContext, Message message) {
        SQLiteHelper helper;
        helper = new SQLiteHelper(applicationContext);
        helper.open();

        final String imagePath = action.details.getImage();
        if (imagePath.length() >= 2) {

            helper.insertData(StringUtil.getStringPreference(activityContext, StringUtil.LOGGED_IN_USER_EMAIL), "image", 2, "", imagePath, "");

            Intent intent = new Intent(applicationContext, GfrMainActivity.class);
            activityContext.startActivity(intent);
            activityContext.finish();
        }
    }
}
