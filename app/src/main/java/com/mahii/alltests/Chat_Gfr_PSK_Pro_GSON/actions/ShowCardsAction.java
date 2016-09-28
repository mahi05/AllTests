package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.actions;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.GfrMainActivity;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Action;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Cards;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Message;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sqlHelpers.SQLiteHelper;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.utils.StringUtil;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class ShowCardsAction implements IAction {

    @Override
    public void run(Action action, GfrMainActivity activityContext, Context applicationContext, Message message) {

        SQLiteHelper helper;
        helper = new SQLiteHelper(applicationContext);
        helper.open();

        ArrayList<Cards> cards = action.details.getCards();

        Gson gson = new Gson();
        String inputString = gson.toJson(cards);
        Log.e("inputString", "" + inputString);

        helper.insertData(
                StringUtil.getStringPreference(activityContext, StringUtil.LOGGED_IN_USER_EMAIL),
                "card",
                2,
                "",
                "",
                inputString
        );

        Intent intent = new Intent(applicationContext, GfrMainActivity.class);
        activityContext.startActivity(intent);
        activityContext.finish();

    }
}
