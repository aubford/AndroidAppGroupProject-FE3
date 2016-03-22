package com.example.aubreyford.androidappgroupproject_fe3;

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
import java.lang.String;
import java.lang.Boolean;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class index extends AppCompatActivity {

    private static Button newQualm;
    private static Button indexBack;
    ArrayList<Decision> decisionList;

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


        decisionList = new ArrayList<Decision>();

        new JSONAsyncTask().execute("https://thisorthatdb.herokuapp.com/posters/decisions");

//        String[][] testArray = {{"hello", "watasdg"},{"hello1", "222222"},{"hello2", "33333333"}};
        ListAdapter adapter = new DecisionAdapter(this, decisionList);
        ListView listview = (ListView)findViewById(R.id.list);
        listview.setAdapter(adapter);


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



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "index Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.aubreyford.androidappgroupproject_fe3/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "index Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.aubreyford.androidappgroupproject_fe3/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }












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
        }


            return result.toString();

        }

        protected void onPostExecute(String result) {


            try {
                String resultLog = result.toString();

                JSONObject resultObject = new JSONObject(result);

                JSONArray decisions = resultObject.getJSONArray("decisions");
//
//
            for (int i = 0; i < decisions.length(); i++) {
                JSONObject decisionObject = decisions.getJSONObject(i);
                Decision decision = new Decision(decisionObject.getInt("id"), decisionObject.getInt("user_id"), decisionObject.getString("title"), decisionObject.getString("category"), decisionObject.getString("picA"), decisionObject.getString("picB"));

                decisionList.add(decision);
                }



            }

            catch (Exception e) {
            }

//
//            JSONArray jarray = jsono.getJSONArray("decisions");
//
//
//            for (int i = 0; i < jarray.length(); i++) {
//                JSONObject object = jarray.getJSONObject(i);
//
//                Decisions actor = new Decisions();
//
//                actor.setTitle(object.getString("title"));
//                actor.setCategory(object.getString("category"));
////                        actor.setDob(object.getString("dob"));
////                        actor.setCountry(object.getString("country"));
////                        actor.setHeight(object.getString("height"));
////                        actor.setSpouse(object.getString("spouse"));
////                        actor.setChildren(object.getString("children"));
////                        actor.setImage(object.getString("image"));
////
//                actorsList.add(actor);
//            };
        }











        //                } catch (Exception e) {
//                }
//                HttpResponse response = httpclient.execute(httppost);
//
//                // StatusLine stat = response.getStatusLine();
//                int status = response.getStatusLine().getStatusCode();
//
//                if (status == 200) {
//                    HttpEntity entity = response.getEntity();
//                    String data = EntityUtils.toString(entity);

//
//                    JSONObject jsono = new JSONObject(result);
//
//                    JSONArray jarray = jsono.getJSONArray("decisions");
//
//
//                    for (int i = 0; i < jarray.length(); i++) {
//                        JSONObject object = jarray.getJSONObject(i);
//
//                        Decisions actor = new Decisions();
//
//                        actor.setTitle(object.getString("title"));
//                        actor.setCategory(object.getString("category"));
////                        actor.setDob(object.getString("dob"));
////                        actor.setCountry(object.getString("country"));
////                        actor.setHeight(object.getString("height"));
////                        actor.setSpouse(object.getString("spouse"));
////                        actor.setChildren(object.getString("children"));
////                        actor.setImage(object.getString("image"));
////
//                        actorsList.add(actor);
//                    }
//                    return true;
//                }
//
//                //------------------>>
//        } catch (MalformedURLException e1) {
//            e1.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//

//            } catch (ParseException e1) {
//                e1.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return false;

//    dialog.cancel();
//            adapter.notifyDataSetChanged();
//            if (result == false)
//                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
//
//        }
    }

}
