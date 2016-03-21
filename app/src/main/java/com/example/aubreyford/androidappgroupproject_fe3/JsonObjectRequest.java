//package com.example.aubreyford.androidappgroupproject_fe3;
//
//import android.app.Notification;
//import android.app.ProgressDialog;
//import android.net.Uri;
//import android.util.Log;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.VolleyLog;
//import com.android.volley.toolbox.JsonRequest;
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**test
// * Created by HomeMariaHome on 3/20/16.
// */
//public class JsonObjectRequest extends JsonRequest<JSONObject> {
//    ProgressDialog pDialog = new ProgressDialog(this);
//    String tag_json_obj = "json_obj_req";
//    String url = "https://thisorthatdb.herokuapp.com/posters/decisions";
//
//
//    public JsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
//        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener, errorListener);
//        pDialog.setMessage("Loading...");
//        pDialog.show();
//
//
//    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
//            url, null,
//            new Response.Listener<JSONObject>() {
//
//
//
//
//    @Override
//    public void onResponse(JSONObject response) {
//        Log.d(TAG, response.toString());
//        pDialog.hide();
//    }
//    }, new Response.ErrorListener() {
//
//    @Override
//    public void onErrorResponse(VolleyError error) {
//        VolleyLog.d(TAG, "Error: " + error.getMessage());
//        // hide the progress dialog
//        pDialog.hide();
//    )
//
//
//// Adding request to request queue
//    AppController.getInstance().
//
//    addToRequestQueue(jsonObjReq, tag_json_obj);
//
//    String TAG = NewSetActivity.class.getName();
//    String tag_json_obj = "json_obj_req";
//
//    String url = "https://thisorthatdb.herokuapp.com/posters/decisions";
//
//    ProgressDialog pDialog = new ProgressDialog(this);
//    pDialog.setMessage("Loading...");
//    pDialog.show();
//
//    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//            url, null,
//            new Response.Listener<JSONObject>() {
//
//                @Override
//                public void onResponse(JSONObject response) {
//                    Log.d(TAG, response.toString());
//                    pDialog.hide();
//                }
//            }, new Response.ErrorListener() {
//
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            VolleyLog.d(TAG, "Error: " + error.getMessage());
//            pDialog.hide();
//        }
//    }) {
//
//        @Override
//        protected Map<String, String> getParams() {
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("user_id", "100");
//            params.put("title", "testTitle");
//            params.put("category", "testCategory");
//            params.put("voteA", "1");
//            params.put("voteB", "2");
//            params.put("winnerA", "false");
//            params.put("winnerB", "true");
//            params.put("picA", "testPicA");
//            params.put("picB", "testPicB");
//
//            return params;
//        }
//
//
//// Adding request to request queue
//        AppController.getInstance().
//
//        addToRequestQueue(jsonObjReq, tag_json_obj);
//    };
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Notification.Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "NewSet Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.example.aubreyford.androidappgroupproject_fe3/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//}