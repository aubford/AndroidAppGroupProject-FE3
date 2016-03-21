package com.example.aubreyford.androidappgroupproject_fe3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewSetActivity extends AppCompatActivity {

    private static Button PicButtonA;
    private static Button PicButtonB;
    //    private static ImageView picA;
//    private static ImageView picB;
    private static Button submitBtn;
    private static Button backBtn;
    String TAG = NewSetActivity.class.getName();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
//    private static ImageView image_test;
    public RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_set);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TakePicA();
        TakePicB();
        Submit();
        Back();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        fetchJsonResponse();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NewSet Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.aubreyford.androidappgroupproject_fe3/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        Log.i("***", "******************");

        ImageView picA = (ImageView) findViewById(R.id.pic_A);
        Bitmap bitmapA = ((BitmapDrawable) picA.getDrawable()).getBitmap();

        if (bitmapA != null) {
            bitmapA.recycle();
//            bitmapA = null;
        }


        ImageView picB = (ImageView) findViewById(R.id.pic_B);
        Bitmap bitmapB = ((BitmapDrawable) picB.getDrawable()).getBitmap();

        if (bitmapB != null) {
            bitmapB.recycle();
//            bitmapB = null;
        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }


    public void Submit() {

        submitBtn = (Button) findViewById(R.id.new_submit);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ImageView picA = (ImageView) findViewById(R.id.pic_A);
                Bitmap bitmapA = ((BitmapDrawable) picA.getDrawable()).getBitmap();

                ImageView picB = (ImageView) findViewById(R.id.pic_B);
                Bitmap bitmapB = ((BitmapDrawable) picB.getDrawable()).getBitmap();


                EditText titleObject = (EditText) findViewById(R.id.newTitle);
                String title = titleObject.getText().toString();


                Intent intent = new Intent(view.getContext(), index.class);
                startActivity(intent);
//
//                        image_test = (ImageView) findViewById(R.id.imageTest);
//                        image_test.setImageBitmap(bitmapB);


            }
        });
    }

    public void Back() {
        backBtn = (Button) findViewById(R.id.new_back);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), index.class);
                startActivity(intent);
            }
        });
    }


    public void TakePicA() {
        PicButtonA = (Button) findViewById(R.id.picButton_A);


        PicButtonA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(1);

            }
        });
    }


    public void TakePicB() {
        PicButtonB = (Button) findViewById(R.id.picButton_B);


        PicButtonB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(2);

            }
        });
    }


    private void dispatchTakePictureIntent(int REQUEST_IMAGE_CAPTURE) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ImageView picA = (ImageView) findViewById(R.id.pic_A);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picA.setImageBitmap(imageBitmap);
            // Store decision and retrieve id from response.
            // Build filename as "decision" appended with id
            // Call asynchronous file upload to Amazon with a parameter of this new id.

        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            ImageView picB = (ImageView) findViewById(R.id.pic_B);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picB.setImageBitmap(imageBitmap);

        }
    }

    private void fetchJsonResponse() {
        // Pass second argument as "null" for GET requests
        Log.d(TAG, "fetchJsonResponse");

        StringRequest req = new StringRequest(Request.Method.POST,"https://thisorthatdb.herokuapp.com/new",

        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String result = "Your IP Address is " + response;
                            Toast.makeText(NewSetActivity.this, result, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", "1");
                params.put("title", "testTitle");
                params.put("category", "testCategory");
                params.put("voteA", "1");
                params.put("voteB", "2");
                params.put("winnerA", "false");
                params.put("winnerB", "true");
                params.put("picA", "testPicA");
                params.put("picB", "testPicB");
                Log.i(TAG, "!!!!!!!!!!!!!!");
                Log.i(TAG, params.get("picA"));
                Log.i(TAG, "*******");
                return params;
            }
        };

        /* Add your Requests to the RequestQueue to execute */
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(req);
    }


}
