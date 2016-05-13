package com.example.aubreyford.androidappgroupproject_fe3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.auth.profile.ProfileCredentialsProvider;
//import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
//import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.amazonaws.services.s3.AmazonS3Client;


public class NewSetActivity extends Activity {

    private static Button PicButtonA;
    private static Button PicButtonB;
    //    private static ImageView picA;
//    private static ImageView picB;
    private static Button submitBtn;
    private static Button backBtn;
    private Uri uriPicA;
    private Uri uriPicB;
    private SharedPreferences sharedPref;
    SharedPreferences.Editor sharedEditor;
    String TAG = NewSetActivity.class.getName();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
//    private static ImageView image_test;
    public RequestQueue mRequestQueue;

    // The TransferUtility is the primary class for managing transfer to S3
    private TransferUtility transferUtility;
    private int uploadCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializes TransferUtility, always do this before using it.
          transferUtility = Util.getTransferUtility(this);
//        checkedIndex = INDEX_NOT_CHECKED;
//        transferRecordMaps = new ArrayList<HashMap<String, Object>>();
//        initUI();

        setContentView(R.layout.activity_new_set);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        TakePicA();
        TakePicB();
        Submit();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        // fetchJsonResponse();
    }



    public void Submit() {


        submitBtn = (Button) findViewById(R.id.new_submit);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String picAFileName;
                String picBFileName;


                ImageView picA = (ImageView) findViewById(R.id.pic_A);
                Bitmap bitmapA = ((BitmapDrawable) picA.getDrawable()).getBitmap();

                ImageView picB = (ImageView) findViewById(R.id.pic_B);
                Bitmap bitmapB = ((BitmapDrawable) picB.getDrawable()).getBitmap();


                EditText titleObject = (EditText) findViewById(R.id.newTitle);
                String title = titleObject.getText().toString();

                storeFiles(bitmapA, bitmapB, title);
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
            ////try something here
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picA.setImageBitmap(imageBitmap);
//            uriPicA = data.getData();


        Log.i(TAG, "************** on A snap");
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            ImageView picB = (ImageView) findViewById(R.id.pic_B);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picB.setImageBitmap(imageBitmap);
//            uriPicB = imageBitmap

            Log.i(TAG, "************** on B snap");

        }

    }

    private void storeFiles(Bitmap bitmapA, Bitmap bitmapB, String title) {
      // Upload the files to storage - first picture A
        String filenameA = title + "picA";
        String filenameB = title + "picB";
        try {
//            String path = getPath(uriPicA);
            beginUpload(bitmapA, filenameA);
        } catch (Exception e) {
            Toast.makeText(this,
                    "Unable to get the file for picture A from the given URI.  See error log for details",
                    Toast.LENGTH_LONG).show();
            Log.e("storeFiles", "Unable to upload file A from the given uri", e);
        }

      // Now upload the file to storage for picture B
      try {
//          String path = getPath(uriPicB);
          beginUpload(bitmapB, filenameB);
      } catch (Exception e) {
          Toast.makeText(this,
                  "Unable to get the file for picture B from the given URI.  See error log for details",
                  Toast.LENGTH_LONG).show();
          Log.e("storeFiles", "Unable to upload file B from the given uri", e);
      }
      // Record the decision in the database for this poster.
      storeDecision(title, filenameA, filenameB);

    }

    public void transferObserverListener(TransferObserver transferObserver){

        transferObserver.setTransferListener(new TransferListener(){

            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.e("statechange", state+"");
                if(state == TransferState.COMPLETED){
                    uploadCounter++;
                    if(uploadCounter == 2){
                        finish();
                    }
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int percentage = (int) (bytesCurrent/bytesTotal * 100);
                Log.e("percentage",percentage +"");
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("error","error");
            }

        });
    }

    private void beginUpload(Bitmap bitmap, String filename) {


        if (filename == null) {
            Toast.makeText(this, "Could not find the filepath of the selected file",
                    Toast.LENGTH_LONG).show();
            return;
        }

        File file;
        System.out.println("!!!!!!!!! File path to save in Amazon");
        file = persistImage(bitmap, filename);
        TransferObserver observer = transferUtility.upload(Constants.BUCKET_NAME, filename,
        file);

        transferObserverListener(observer);



        /*
         * Note that usually we set the transfer listener after initializing the
         * transfer. However it isn't required in this sample app. The flow is
         * click upload button -> start an activity for image selection
         * startActivityForResult -> onActivityResult -> beginUpload -> onResume
         * -> set listeners to in progress transfers.
         */
        // observer.setTransferListener(new UploadListener());
    }

    private File persistImage(Bitmap bitmap, String name) {
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 65, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("persistImage", "Error writing bitmap", e);
        }
        return imageFile;
    }

    /*
     * Gets the file path of the given Uri.
     */
    @SuppressLint("NewApi")
    private String getPath(Uri uri) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[] {
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private void storeDecision(String title, String picAFileName, String picBFileName) {
        // Pass second argument as "null" for GET requests
        Log.d(TAG, "storeDecision");

        final String finalPicAFileName = picAFileName;
        final String finalPicBFileName = picBFileName;
        final String finalTitle = title;

        StringRequest req = new StringRequest(Request.Method.POST,"https://thisorthatdb.herokuapp.com/new",

        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String result = "Uploading Images, please wait";
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
                sharedPref = getSharedPreferences("quandry", Context.MODE_PRIVATE);
                sharedEditor = sharedPref.edit();
                String id = String.valueOf(sharedPref.getInt("user", -1));
                // Make sure below that the voteA and voteB do not need
                // to be sent as integer 0.
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", id);
                params.put("title", finalTitle);
                params.put("category", "none");
                params.put("voteA", "0");
                params.put("voteB", "0");
                params.put("winnerA", "false");
                params.put("winnerB", "false");
                params.put("picA", "https://s3-us-west-2.amazonaws.com/thisorthatphotofiles/" + finalPicAFileName);
                params.put("picB", "https://s3-us-west-2.amazonaws.com/thisorthatphotofiles/" + finalPicBFileName);
                return params;

            }
        };

        /* Add your Requests to the RequestQueue to execute */
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(req);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction2 = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NewSet Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.aubreyford.androidappgroupproject_fe3/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction2);
    }


}
