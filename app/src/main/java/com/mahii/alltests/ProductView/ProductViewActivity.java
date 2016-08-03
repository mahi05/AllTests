package com.mahii.alltests.ProductView;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mahii.alltests.R;

public class ProductViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        ProductShowCaseWebView wv = (ProductShowCaseWebView) findViewById(R.id.web_view);

        String imagesTag360 = "";

        /*Taking images from the assert folder*/

        for (int i = 1; i < 19; i++) {
            imagesTag360 = imagesTag360 + "<img src=\"file:///android_asset/images/image1_" + i + ".jpg\"/>";
        }


        /* For Showing Images from image url just use the image url in the src field*/

//        for(int i=0;i<imageLength;i++)
//        {
//            imagesTag360=imagesTag360+"<img src=\"http://imageurl.com/image1_.jpg\"/>" ;
//        }


        Log.d("", imagesTag360);


        wv.loadDataWithBaseURL("", imagesTag360, "text/html", "UTF-8", null);

    }

}