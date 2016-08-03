package com.mahii.alltests;

import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class SubStringsDemo extends AppCompatActivity {

    String[] Names = {"1", "3", "4"};
    String[] shortNames = {"1", "4"};
    ImageView image;
    ArrayList<String> myNames;

    Context mContext;
    ImageView m360DegreeImageView;

    int mStartX, mStartY, mEndX, mEndY;
    int mImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_strings_demo);

        mContext = this;
        m360DegreeImageView = (ImageView)findViewById(R.id.santafe3dview);

        DatabaseHelperNew myDbHelper = new DatabaseHelperNew(this);

        try {
            myDbHelper.createDatabase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            myDbHelper.openDatabase();
        } catch (SQLException sqlE) {
            throw sqlE;
        }

        myNames = myDbHelper.vendorsOrClients();

        for (int i = 0; i < myNames.size(); i++) {
            Log.e("Months", myNames.get(i));
        }


        image = (ImageView) findViewById(R.id.image);
        String backgroundImageName = String.valueOf(image.getTag());
        Log.e("Name", backgroundImageName);
        Toast.makeText(SubStringsDemo.this, backgroundImageName, Toast.LENGTH_SHORT).show();

        String mainString = "a,b,c";

        String[] split_text = mainString.split(",");

        Toast.makeText(SubStringsDemo.this, "" + split_text[1], Toast.LENGTH_SHORT).show();

        for (int i = 0; i < Names.length; i++) {

            for (int j = 0; j < shortNames.length; j++) {

                if (Names[i] == shortNames[j]) {
                    //Log.e("Match", Names[i]);
                    Toast.makeText(SubStringsDemo.this, "" + Names[i], Toast.LENGTH_SHORT).show();
                }

            }
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :

                mStartX = (int)event.getX();
                mStartY = (int)event.getY();
                return true;

            case (MotionEvent.ACTION_MOVE) :

                mEndX = (int)event.getX();
                mEndY = (int)event.getY();

                if((mEndX - mStartX) > 3) {
                    mImageIndex++;
                    if(mImageIndex > 56 )
                        mImageIndex = 0;

                    m360DegreeImageView.setImageLevel(mImageIndex);

                }
                if((mEndX - mStartX) < -3) {
                    mImageIndex--;
                    if(mImageIndex <0)
                        mImageIndex = 56;

                    m360DegreeImageView.setImageLevel(mImageIndex);

                }
                mStartX = (int)event.getX();
                mStartY = (int)event.getY();
                return true;

            case (MotionEvent.ACTION_UP) :
                mEndX = (int)event.getX();
                mEndY = (int)event.getY();

                return true;

            case (MotionEvent.ACTION_CANCEL) :
                return true;

            case (MotionEvent.ACTION_OUTSIDE) :
                return true;

            default :
                return super.onTouchEvent(event);
        }
    }


}
