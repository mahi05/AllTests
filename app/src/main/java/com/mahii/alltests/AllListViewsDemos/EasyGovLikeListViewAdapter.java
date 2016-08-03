package com.mahii.alltests.AllListViewsDemos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mahii.alltests.R;

import java.util.ArrayList;

public class EasyGovLikeListViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<MyModel> myModels;
    LayoutInflater inflater;

    public EasyGovLikeListViewAdapter(Context context, ArrayList<MyModel> myModels) {
        this.context = context;
        this.myModels = myModels;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class MyHolder {
        TextView tvId, tvName, tvPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyHolder holder;

        if (convertView == null) {

            holder = new MyHolder();
            convertView = inflater.inflate(R.layout.easygov_list_row, parent, false);
            holder.tvId = (TextView) convertView.findViewById(R.id.tvId);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvPosition = (TextView) convertView.findViewById(R.id.tvPosition);
            convertView.setTag(holder);

        } else {
            holder = (MyHolder) convertView.getTag();
        }

        holder.tvId.setText("" + myModels.get(position).getId());
        holder.tvName.setText(myModels.get(position).getName());
        holder.tvPosition.setText(myModels.get(position).getJob());

        return convertView;
    }

}