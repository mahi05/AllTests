package com.mahii.alltests.AllListViewsDemos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mahii.alltests.R;

/**
 * Created by MyWindows on 03-Aug-16.
 */
public class EasyGovLikeListViewDemo2 extends AppCompatActivity {

    Button btnAdd;
    EditText tvPositionAdd;
    int posFlag;
    Intent intent;
    String setKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easygov_demo2);

        tvPositionAdd = (EditText) findViewById(R.id.tvPositionAdd);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        intent = getIntent();
        posFlag = intent.getIntExtra("posFlag", 0);
        setKey = intent.getStringExtra("setKey");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String namePosition = tvPositionAdd.getText().toString();

                intent.putExtra("posFlag", posFlag);
                intent.putExtra("namePosition", namePosition);
                if (setKey.equals("default"))
                {
                    intent.putExtra("setKey", setKey);
                }
                else
                {
                    intent.putExtra("setKey", "update");
                }
                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }
}
