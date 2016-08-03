package com.mahii.alltests.CameraTest;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mahii.alltests.R;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.iconics.view.IconicsButton;
import com.mikepenz.weather_icons_typeface_library.WeatherIcons;

import java.util.ArrayList;

public class CameraMainActivity extends AppCompatActivity {

    ImageView imageView, imageView2;
    IconicsButton selectImages;
    ListView imagesList;

    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    ArrayList<String> imagesEncodedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_main);

        String year = "2500";

        int years = Integer.parseInt(year);
        int s = 2500;
        int total = years + s;
        Log.e("Taotal", String.valueOf(total));

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        selectImages = (IconicsButton) findViewById(R.id.selectImages);
        imagesList = (ListView) findViewById(R.id.imagesList);

        imageView.setImageDrawable(new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_ambulance)
                .color(Color.BLUE)
                .sizeDp(100));

        imageView2.setImageDrawable(new IconicsDrawable(this)
                .icon(WeatherIcons.Icon.wic_thunderstorm)
                .color(Color.RED)
                .sizeDp(100));

        selectImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);

            }
        });

        imagesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CameraMainActivity.this, ShowImageActivity.class);
                intent.putExtra("path", "" + imagesEncodedList.get(position));
                startActivity(intent);

            }
        });

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<>();

                // Get the Image from data
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri, filePathColumn, null, null, null);

                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(CameraMainActivity.this, android.R.layout.simple_list_item_1, imagesEncodedList);
                            imagesList.setAdapter(adapter);

                        }
                        Log.e("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}
