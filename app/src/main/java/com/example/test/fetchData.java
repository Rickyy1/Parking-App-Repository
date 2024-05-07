package com.example.test;

import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;

public class fetchData {

    private final RequestQueue queue;
    private static final String TAG = "MainActivity";
    private final TextView myTextView;
    private String userType;



    public fetchData(RequestQueue queue, TextView myTextView) {
        this.queue = queue;
        this.myTextView = myTextView;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    public void getData() {
        String url = "https://rwuparking.rwu.me//view_lot_data_v2.php";
        String modifiedUrl = url + "?userType=" + userType;
        Log.d(TAG, "Request URL: " + modifiedUrl);


        // Request a JSON array response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, modifiedUrl, null,
                response -> {
                    Log.d("NetworkResponse", "Received JSON: " + response.toString());
                    updateUI updateUI = new updateUI(myTextView);
                    updateUI.getUpdate(response);
                },
                error -> {
                    // Handle error
                    Log.e(TAG, "Volley Error: " + error.getMessage());
                });
        // Disable caching
        jsonArrayRequest.setShouldCache(false);

        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }
}
