package com.example.aubreyford.androidappgroupproject_fe3;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aubreyford on 4/1/16.
 */
class CustomAdapter extends ArrayAdapter<HashMap<String, Object>> {
    CustomAdapter(Context context, List<HashMap<String, Object>> decisionList) {
        super(context, R.layout.row, decisionList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.row, parent, false);

        HashMap decision = getItem(position);
        Button delt = (Button) customView.findViewById(R.id.row_delete);
        ImageView imageA = (ImageView) customView.findViewById(R.id.pic_A);
        ImageView imageB = (ImageView) customView.findViewById(R.id.pic_B);
        TextView title = (TextView) customView.findViewById(R.id.title);

        delt.setTag(decision.get("id"));
        title.setText((String) decision.get("title"));

        imageA.setImageURI(Uri.parse((String) decision.get("picA")));
        imageB.setImageURI(Uri.parse((String) decision.get("picB")));






        return customView;
    }
}
