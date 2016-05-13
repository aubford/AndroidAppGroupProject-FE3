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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class CustomAdapter extends ArrayAdapter<HashMap<String, Object>> {


    List<HashMap<String, Object>> mList;

    CustomAdapter(Context context, List<HashMap<String, Object>> decisionList) {
        super(context, R.layout.row, decisionList);
        this.mList = decisionList;
    }

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.row, parent, false);

        HashMap decision = getItem(position);
        int id = (int) decision.get("id");

        Button delt = (Button) customView.findViewById(R.id.row_delete);
        ImageView imageA = (ImageView) customView.findViewById(R.id.pic_A);
        ImageView imageB = (ImageView) customView.findViewById(R.id.pic_B);
        TextView title = (TextView) customView.findViewById(R.id.title);

        delt.setTag(id);

        imageA.setImageURI(Uri.parse((String) decision.get("picA")));
        imageB.setImageURI(Uri.parse((String) decision.get("picB")));

        title.setText((String) decision.get("title"));

        delt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = (int) v.getTag();
                String idString = String.valueOf(id);
                Log.i("IDSTRING****", idString);

                try {
                    deleteDecision(idString);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mList.remove(position);
                notifyDataSetChanged();
            }
        });
        return customView;
    }

    public void deleteDecision(String idString) throws Exception {
        Request request = new Request.Builder()
                .url("http://thisorthatdb.herokuapp.com/decision/" + idString)
                .delete()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response.body().string());
            }
        });
    }

    public List<HashMap<String, Object>> getDecisionList(){
        return mList;
    }
}
