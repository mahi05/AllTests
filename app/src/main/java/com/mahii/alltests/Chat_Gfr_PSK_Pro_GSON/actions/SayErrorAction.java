package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.actions;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.GfrMainActivity;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Action;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Message;

@SuppressWarnings("unused")
public class SayErrorAction implements IAction {

    private TextToSpeech textToSpeech;

    @Override
    public void run(Action action, GfrMainActivity activityContext, Context applicationContext, Message message) {
        final String text = action.details.getText();
        if (text.length() >= 2) {
            textToSpeech = new TextToSpeech(applicationContext, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status == TextToSpeech.SUCCESS) {
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        //TODO: How to dispose of it... textToSpeech.shutdown();
                    }
                }
            });
        }
    }
}
