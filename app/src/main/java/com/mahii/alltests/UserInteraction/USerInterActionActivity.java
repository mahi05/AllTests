package com.mahii.alltests.UserInteraction;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by MaHi on 28-Sep-16.
 */

public class USerInterActionActivity extends AppCompatActivity {

    private CountDownTimer user_interaction_timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user_interaction_timer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {

                if (UserInterActionApplication.getElapsedTime() >= 5000) {
                    // Do below like stuff
                    /*if (brightnessLayout.getVisibility() == View.VISIBLE) {
                        brightnessLayout.setVisibility(View.GONE);
                    }*/
                }
                user_interaction_timer.start();
            }
        };
        user_interaction_timer.start();

    }
}
