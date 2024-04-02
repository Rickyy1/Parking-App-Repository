package com.example.test;

import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class fetchData {

    private RequestQueue queue;
    private static final String TAG = "MainActivity";
    private String url = "https://rwuparking.rwu.me//view_lot_data.php";
    private TextView myTextView;

    public fetchData(RequestQueue queue, TextView myTextView) {
        this.queue = queue;
        this.myTextView = myTextView;
    }
    public void getData() {
        // Request a JSON array response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        updateUI updateUI = new updateUI(myTextView);
                        updateUI.getUpdate(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e(TAG, "Volley Error: " + error.getMessage());
                    }
                });
        // Disable caching
        jsonArrayRequest.setShouldCache(false);

        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }
}
