package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.actions;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.GfrMainActivity;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Action;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Message;

import java.util.HashMap;

@SuppressWarnings("unused")
public class SayQuestionAction implements IAction {

    private TextToSpeech textToSpeech;

    @Override
    public void run(Action action, final GfrMainActivity activityContext, Context applicationContext, final Message message) {
        final String text = action.details.getText();
        if (text.length() >= 2) {
            final String uniqueID = "GoferSayQuestionAction";
            //final Bundle params = new Bundle();
            //params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, uniqueID);
            textToSpeech = new TextToSpeech(applicationContext, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status == TextToSpeech.SUCCESS) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, uniqueID);
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params);
                        //TODO: How to dispose of it... textToSpeech.shutdown();
                    }
                }
            });
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {

                }

                @Override
                public void onDone(String utteranceId) {
                    //activityContext.startSpeechRecognitionForAnswer(message.messageId);
                }

                @Override
                public void onError(String utteranceId) {

                }
            });
        }
    }
}
