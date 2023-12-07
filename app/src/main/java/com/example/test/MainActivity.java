package com.example.test;

import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView myTextView;
    private RequestQueue queue;
    private String url = "https://rwuparking.rwu.me/script_test.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference the TextView by ID
        myTextView = findViewById(R.id.myTextView);

        // Initialize the RequestQueue with the application context
        queue = Volley.newRequestQueue(getApplicationContext());

        // Set up the refresh button click listener
        Button refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle refresh button click
                fetchData();
            }
        });

        // Fetch data when the app is opened
        fetchData();
    }

    private void fetchData() {
        // Request a JSON array response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        updateUI(response);
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

    private void updateUI(JSONArray response) {
        try {
            // Process the JSON response and update the UI
            StringBuilder displayText = new StringBuilder();

            // Assuming each item in the array is a JSONObject
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObject = response.getJSONObject(i);

                // Extract data from the JSON object
                String lotId = jsonObject.getString("lot_id");
                String lotName = jsonObject.getString("lot_name");
                String availableSpots = jsonObject.getString("available_spots");
                String totalSpots = jsonObject.getString("total_spots");
                String availableHandicapSpots = jsonObject.getString("available_handicap_spots");
                String totalHandicapSpots = jsonObject.getString("total_handicap_spots");

                // Append the information to the displayText StringBuilder
                displayText.append("Lot ID: ").append(lotId).append("\n")
                        .append("Lot Name: ").append(lotName).append("\n")
                        .append("Available Spots: ").append(availableSpots).append("\n")
                        .append("Total Spots: ").append(totalSpots).append("\n")
                        .append("Available Handicap Spots: ").append(availableHandicapSpots).append("\n")
                        .append("Total Handicap Spots: ").append(totalHandicapSpots).append("\n\n");
            }

            // Set or update the text with the concatenated information
            myTextView.setText(displayText.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
