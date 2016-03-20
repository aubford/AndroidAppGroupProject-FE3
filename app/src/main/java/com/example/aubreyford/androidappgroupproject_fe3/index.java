package com.example.aubreyford.androidappgroupproject_fe3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;

import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.String;
import java.lang.Boolean;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class index extends AppCompatActivity {

    private static Button newQualm;
    private static Button indexBack;
    ListView mListView;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ///////////////////////////////////////////////////

        new JSONAsyncTask().execute("https://thisorthatdb.herokuapp.com/posters/decisions");
        mListView = (ListView)findViewById(R.id.list);

        ///////////////////////////////////////////////////



        NewSetNavListener();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    public void NewSetNavListener() {
        newQualm = (Button) findViewById(R.id.new_qualm);
        indexBack = (Button) findViewById(R.id.index_back);

        newQualm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewSetActivity.class);
                startActivity(intent);

            }
        });

        indexBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "index Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.example.aubreyford.androidappgroupproject_fe3/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "index Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.example.aubreyford.androidappgroupproject_fe3/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
//    }


    class JSONAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = new ProgressDialog(MainActivity.this);
//            dialog.setMessage("Loading, please wait");
//            dialog.setTitle("Connecting server");
//            dialog.show();
//            dialog.setCancelable(false);
//        }

        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection connection = null;
            StringBuilder result = new StringBuilder();


            try {
                    URL url = new URL(urls[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    int length = connection.getContentLength();
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);
                    BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    rd.close();

            } catch (Exception e) {
                Log.d("***JSONAsyncTask", e.toString());
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();
            listViewLoaderTask.execute(result);
        }
    }

    private class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter> {
        JSONObject resultObject;
        JSONArray decisionsArray;

        @Override
        protected SimpleAdapter doInBackground(String... strJson){

            try {
                resultObject = new JSONObject(strJson[0]);
                decisionsArray = resultObject.getJSONArray("decisions");
//                for (int i = 0; i < decisions.length(); i++) {
//                    JSONObject decisionObject = decisions.getJSONObject(i);
//                    /////////////
//                    Decision decision = new Decision(decisionObject.getInt("id"), decisionObject.getInt("user_id"), decisionObject.getString("title"), decisionObject.getString("category"), decisionObject.getString("picA"), decisionObject.getString("picB"));
//                    ////////////
//
            }catch(Exception e){
                Log.i("*****", "SimpleAdapterFAILB");
                Log.d("***ListViewLoaderTask", e.toString());
            }

            JSONParser theParseMaster = new JSONParser();
            List<HashMap<String, Object>> decisionList = null;

            try {
                decisionList = theParseMaster.parse(decisionsArray);
            }catch(Exception e){
                Log.i("*****", "SimpleAdapterFAILB");
                Log.d("***Parsing", e.toString());
            }

            String[] from = {"title", "picA", "picB"};
            int[] to = {R.id.title, R.id.pic_A, R.id.pic_B};
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), decisionList, R.layout.row, from, to);

            return adapter;
        }

        @Override
        protected void onPostExecute(SimpleAdapter adapter) {

            mListView.setAdapter(adapter);

            for (int i = 0; i < adapter.getCount(); i++) {
                HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(i);
                String picA_Url = (String) hm.get("picA_Url");
                String picB_Url = (String) hm.get("picB_Url");
                ImageLoaderTask imageLoaderTask = new ImageLoaderTask();

                HashMap<String, Object> hmDownload = new HashMap<String, Object>(); //extraneous?

                hm.put("picA_Url", picA_Url);
                hm.put("positionA", i);
                hm.put("picB_Url", picB_Url);
                hm.put("positionB", i);

                imageLoaderTask.execute(hm);
            }
        }

        private class ImageLoaderTask extends AsyncTask<HashMap<String, Object>, Void, HashMap<String, Object>> {

            @Override
            protected HashMap<String, Object> doInBackground(HashMap<String, Object>... hm) {

                InputStream iStream = null;
                String imgUrl = (String) hm[0].get("picA_Url");
                int position = (Integer) hm[0].get("positionA");

                Log.i("***********************IMMAGEURL", imgUrl);
                Log.i("*****", "ImageLoaderTaskSTARTT");
                URL url;
                try {
                    url = new URL(imgUrl);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.connect();
                    iStream = urlConnection.getInputStream();
                    File cacheDirectory = getBaseContext().getCacheDir();
                    File tmpFile = new File(cacheDirectory.getPath() + "/wpta_" + position + ".png");
                    FileOutputStream fOutStream = new FileOutputStream(tmpFile);
                    Bitmap b = BitmapFactory.decodeStream(iStream);
                    b.compress(Bitmap.CompressFormat.PNG, 100, fOutStream);
                    fOutStream.flush();
                    fOutStream.close();
                    HashMap<String, Object> hmBitmap = new HashMap<String, Object>();
                    hmBitmap.put("picA", tmpFile.getPath());
                    hmBitmap.put("positionA", position);
                    return hmBitmap;

                } catch (Exception e) {
                    Log.i("************", "ImageLoaderTaskFAIL");
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(HashMap<String, Object> result) {
                String path = (String) result.get("picA");
                int position = (Integer) result.get("positionA");
                SimpleAdapter adapter = (SimpleAdapter) mListView.getAdapter();
                HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(position);
                hm.put("picA", path);
                adapter.notifyDataSetChanged();
            }
        }
    }
















}
