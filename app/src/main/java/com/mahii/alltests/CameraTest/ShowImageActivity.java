package com.mahii.alltests.CameraTest;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mahii.alltests.R;

public class ShowImageActivity extends AppCompatActivity {

    ImageView imageShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        imageShow = (ImageView) findViewById(R.id.imageShow);

        Bundle bundle = getIntent().getExtras();
        String path = bundle.getString("path");

        Uri myUri = Uri.parse(path);

        imageShow.setImageURI(myUri);

    }

}
