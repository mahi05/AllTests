package com.mahii.alltests.AllListViewsDemos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mahii.alltests.R;

import java.util.ArrayList;

public class EasyGovLikeListViewDemo extends AppCompatActivity {

    ListView basicLV;
    ArrayList<MyModel> myModels;
    MyModel myModel;
    LinearLayout doneLL;
    private final int ID_REQUEST = 100;
    ArrayList<PositionFlag> positionFlagArrayList;
    ArrayList<String> positionsList;
    EasyGovLikeListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easygov_demo);

        basicLV = (ListView) findViewById(R.id.basicLV);
        doneLL = (LinearLayout) findViewById(R.id.doneLL);

        positionFlagArrayList = new ArrayList<>();
        positionsList = new ArrayList<>();

        for (int i = 0; i < 3; i++)
            positionsList.add("");

        myModels = new ArrayList<>();

        myModel = new MyModel();
        myModel.setId(1);
        myModel.setName("MaHi");
        myModels.add(myModel);

        myModel = new MyModel();
        myModel.setId(2);
        myModel.setName("Pavan");
        myModels.add(myModel);

        myModel = new MyModel();
        myModel.setId(3);
        myModel.setName("Chirag");
        myModels.add(myModel);

        adapter = new EasyGovLikeListViewAdapter(EasyGovLikeListViewDemo.this, myModels);
        basicLV.setAdapter(adapter);

        basicLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(EasyGovLikeListViewDemo.this, EasyGovLikeListViewDemo2.class);
                i.putExtra("posFlag", (position));
                TextView tvPosition = (TextView) view.findViewById(R.id.tvPosition);
                if (tvPosition.getText().length() == 0) {
                    i.putExtra("setKey", "default");
                } else {
                    i.putExtra("setKey", "update");
                }
                startActivityForResult(i, ID_REQUEST);

            }
        });

        doneLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("ArrayList Size ", "" + positionsList.size());

                for (int i = 0; i < positionsList.size(); i++) {
                    Log.e("Lists ", positionsList.get(i));

                    if (positionsList.get(i).equals("")) {
                        Toast.makeText(EasyGovLikeListViewDemo.this, "All values are not selected", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ID_REQUEST && resultCode == RESULT_OK) {

            int position = data.getIntExtra("posFlag", 0);
            String jobPosition = data.getStringExtra("namePosition");

            View v = basicLV.getChildAt(position);
            TextView tvPosition = (TextView) v.findViewById(R.id.tvPosition);
            tvPosition.setText(jobPosition);

            if (data.getStringExtra("setKey").equals("default")) {
                positionsList.set(position, jobPosition);
            } else {
                for (int i = 0; i < positionsList.size(); i++) {
                    if (i == position) {
                        String matchString = positionsList.get(i);
                        int index = positionsList.indexOf(matchString);
                        positionsList.set(index, jobPosition);
                    }
                }
            }
        }
    }

}
