package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.actions;

import android.content.Context;

import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.GfrMainActivity;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Action;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Message;

public interface IAction {
    void run(Action action, GfrMainActivity activityContext, Context applicationContext, Message message);
}
