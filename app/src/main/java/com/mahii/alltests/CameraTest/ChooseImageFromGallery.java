package com.mahii.alltests.CameraTest;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mahii.alltests.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ChooseImageFromGallery extends AppCompatActivity {

    Button selectImage;
    ImageView displayImage;
    int ACTIVITY_SELECT_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_from_gallery);

        selectImage = (Button) findViewById(R.id.selectImage);
        displayImage = (ImageView) findViewById(R.id.displayImage);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            InputStream is= null;
            try {
                is = getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            displayImage.setImageBitmap(BitmapFactory.decodeStream(is));
        }
    }

}
