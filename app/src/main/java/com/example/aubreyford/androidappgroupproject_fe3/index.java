package com.example.aubreyford.androidappgroupproject_fe3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


public class index extends Activity {

    private static Button newQualm;
    private static Button logout;
    private static Button indexBack;
    ListView mListView;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private SharedPreferences sharedPref;
    SharedPreferences.Editor sharedEditor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_index);

        SetNavListeners();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPref = getSharedPreferences("quandry", Context.MODE_PRIVATE);
        sharedEditor = sharedPref.edit();
        String id = String.valueOf(sharedPref.getInt("user", -1));

        new JSONAsyncTask().execute("https://thisorthatdb.herokuapp.com/posters/decisions/" + id);
        mListView = (ListView)findViewById(R.id.list);


    }

    public void SetNavListeners() {
        newQualm = (Button) findViewById(R.id.new_qualm);
        logout = (Button) findViewById(R.id.logout);

        newQualm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewSetActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedEditor.remove("user");
                Intent intent = new Intent(index.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    class JSONAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;


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

    private class ListViewLoaderTask extends AsyncTask<String, Void, ListAdapter> {
        JSONObject resultObject;
        JSONArray decisionsArray;

        @Override
        protected ListAdapter doInBackground(String... strJson){

            try {
                resultObject = new JSONObject(strJson[0]);
                decisionsArray = resultObject.getJSONArray("decisions");

            }catch(Exception e){
                Log.i("*****", "AdapterFAILB");
                Log.d("***ListViewLoaderTask", e.toString());
            }

            JSONParser theParseMaster = new JSONParser();
            List<HashMap<String, Object>> decisionList = null;

            try {
                decisionList = theParseMaster.parse(decisionsArray);
            }catch(Exception e){
                Log.i("*****", "" +
                        "AdapterFAILB");
                Log.d("***Parsing", e.toString());
            }

            ListAdapter adapter = new CustomAdapter(getBaseContext(), decisionList);


            return adapter;
        }

        @Override
        protected void onPostExecute(ListAdapter adapter) {

            mListView.setAdapter(adapter);

            for (int i = 0; i < adapter.getCount(); i++) {
                HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(i);


                String picA_Url = (String) hm.get("picA_Url");
                String picB_Url = (String) hm.get("picB_Url");
                ImageLoaderTaskA imageLoaderTaskA = new ImageLoaderTaskA();
                ImageLoaderTaskB imageLoaderTaskB = new ImageLoaderTaskB();

                HashMap<String, Object> hmDownload = new HashMap<String, Object>(); //extraneous?

                hm.put("picA_Url", picA_Url);
                hm.put("positionA", i);
                hm.put("picB_Url", picB_Url);
                hm.put("positionB", i);

                imageLoaderTaskA.execute(hm);
                imageLoaderTaskB.execute(hm);
            }
        }

        private class ImageLoaderTaskA extends AsyncTask<HashMap<String, Object>, Void, HashMap<String, Object>> {

            @Override
            protected HashMap<String, Object> doInBackground(HashMap<String, Object>... hm) {

                InputStream iStream = null;
                String imgUrl = (String) hm[0].get("picA_Url");
                int position = (Integer) hm[0].get("positionA");

                Log.i("************IMMAGEURLA", imgUrl);
                Log.i("*****", "ImageLoaderTaskSTARTA");
                URL url;
                
                try {
                    url = new URL(imgUrl);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.connect();
                    iStream = urlConnection.getInputStream();
                    File cacheDirectory = getBaseContext().getCacheDir();
                    File tmpFile = new File(cacheDirectory.getPath() + "/wpta_" + position + "a.png");
                    FileOutputStream fOutStream = new FileOutputStream(tmpFile);
                    Bitmap b = BitmapFactory.decodeStream(iStream);
                    b.compress(Bitmap.CompressFormat.PNG, 100, fOutStream);
                    fOutStream.flush();
                    fOutStream.close();
                    HashMap<String, Object> hmBitmap = new HashMap<String, Object>();
                    hmBitmap.put("picA", tmpFile.getPath());
                    Log.i("***************PATH", tmpFile.getPath());
                    hmBitmap.put("positionA", position);
                    return hmBitmap;

                } catch (Exception e) {
                    Log.i("************", "ImageLoaderTaskFAILA");
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(HashMap<String, Object> result) {
                System.out.print(result);
                String path = (String) result.get("picA");

                int position = (Integer) result.get("positionA");
                CustomAdapter adapter = (CustomAdapter) mListView.getAdapter();
                HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(position);
                hm.put("picA", path);
                adapter.notifyDataSetChanged();
            }
        }


        private class ImageLoaderTaskB extends AsyncTask<HashMap<String, Object>, Void, HashMap<String, Object>> {

            @Override
            protected HashMap<String, Object> doInBackground(HashMap<String, Object>... hm) {

                InputStream iStream = null;
                String imgUrl = (String) hm[0].get("picB_Url");
                int position = (Integer) hm[0].get("positionB");

                URL url;
                try {
                    url = new URL(imgUrl);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.connect();
                    iStream = urlConnection.getInputStream();
                    File cacheDirectory = getBaseContext().getCacheDir();
                    File tmpFile = new File(cacheDirectory.getPath() + "/wpta_" + position + "b.png");
                    FileOutputStream fOutStream = new FileOutputStream(tmpFile);
                    Bitmap b = BitmapFactory.decodeStream(iStream);
                    b.compress(Bitmap.CompressFormat.PNG, 100, fOutStream);
                    fOutStream.flush();
                    fOutStream.close();
                    HashMap<String, Object> hmBitmap = new HashMap<String, Object>();
                    hmBitmap.put("picB", tmpFile.getPath());
                    hmBitmap.put("positionB", position);
                    return hmBitmap;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(HashMap<String, Object> result) {
                String path = (String) result.get("picB");
                int position = (Integer) result.get("positionB");
                CustomAdapter adapter = (CustomAdapter) mListView.getAdapter();
                HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(position);
                hm.put("picB", path);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
