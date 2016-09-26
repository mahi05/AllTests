package com.mahii.alltests.FinishLoopCreateNew;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.mahii.alltests.R;

public class BrightnessDemo extends AppCompatActivity {

    float curBrightnessValue = 0.1F;
    Button decrease, increase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_brightness);

        decrease = (Button) findViewById(R.id.decrease);
        increase = (Button) findViewById(R.id.increase);

        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = curBrightnessValue;
        getWindow().setAttributes(layout);

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("Before", "" + curBrightnessValue);
                if (curBrightnessValue < (float) 1.0) {

                    curBrightnessValue = curBrightnessValue + 0.1F;

                    if(curBrightnessValue < (float)0.99){
                        WindowManager.LayoutParams layout = getWindow().getAttributes();
                        layout.screenBrightness = curBrightnessValue;
                        getWindow().setAttributes(layout);

                        Log.e("After", "" + curBrightnessValue);
                    } else {
                        Toast.makeText(BrightnessDemo.this, "Finish", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BrightnessDemo.this, "Finish", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("Before", "" + curBrightnessValue);

                if (curBrightnessValue > (float) 0.1) {

                    curBrightnessValue = curBrightnessValue - 0.1F;

                    WindowManager.LayoutParams layout = getWindow().getAttributes();
                    layout.screenBrightness = curBrightnessValue;
                    getWindow().setAttributes(layout);

                    Log.e("After", "" + curBrightnessValue);

                } else {
                    Toast.makeText(BrightnessDemo.this, "Finish", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
