package com.example.test;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class AdminActivity extends AppCompatActivity {

    private EditText lotnameEditText;
    private EditText totalSpotsEditText;
    private EditText handicapSpotsEditText;
    private Spinner adminSpinner;
    private EditText deleteLotText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Initialize UI elements

        lotnameEditText = findViewById(R.id.editLotName);
        totalSpotsEditText = findViewById(R.id.editTotalSpots);
        handicapSpotsEditText = findViewById(R.id.editHandicapSpots);
        adminSpinner = findViewById(R.id.adminSpinner);
        deleteLotText = findViewById(R.id.deleteLotName);

        // Retrieve options from strings.xml and set up Spinner adapter
        String[] options = getResources().getStringArray(R.array.admin_spinner_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminSpinner.setAdapter(adapter);

        // Assuming there's a button in your layout to trigger the request
        // Assuming the button ID is submitButton
        findViewById(R.id.buttonSubmit).setOnClickListener(view -> {
            // Get values from EditText fields and Spinner
            String lotName = lotnameEditText.getText().toString(); // Assuming lot name is constant or retrieved from elsewhere
            int totalSpots = Integer.parseInt(totalSpotsEditText.getText().toString());
            int handicapSpots = Integer.parseInt(handicapSpotsEditText.getText().toString());
            String userType = adminSpinner.getSelectedItem().toString(); // Get the selected item as a string

            // Call makeRequest method with the retrieved values
            makeAddRequest(lotName, totalSpots, handicapSpots, userType);
        });

        findViewById(R.id.deleteLotSubmit).setOnClickListener(view -> {
            // Get values from EditText fields and Spinner
            String lotName = deleteLotText.getText().toString(); // Assuming lot name is constant or retrieved from elsewhere

            // Call makeRequest method with the retrieved values
            makeDelRequest(lotName);
        });
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity
                onBackPressed();
            }
        });

    }

    // URL of your PHP script
    private static final String PHP_SCRIPT_URL = "https://rwuparking.rwu.me/admin_add_lot_test.php/";

    // Method to make HTTP request
    private void makeAddRequest(String lotName, int totalSpots, int handicapSpots, String userType) { // Change userType type to String
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Formulate the request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PHP_SCRIPT_URL +
                "?auth=password&lot_name=" + lotName +
                "&total_spots=" + totalSpots +
                "&handicap_spots=" + handicapSpots +
                "&user_type=" + userType,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle successful response
                        // You can update UI or show a message here
                        displayMessage("Request Successful!");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
                // You can show an error message here
                displayMessage("Request Failed! :(");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    private void displayMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // URL of your PHP script
    private static final String delete_lot_url = "https://rwuparking.rwu.me/admin_delete_lot.php/";

    // Method to make HTTP request
    private void makeDelRequest(String lotName) { // Change userType type to String
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Formulate the request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, delete_lot_url +
                "?auth=password&lot_name=" + lotName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle successful response
                        // You can update UI or show a message here
                        displayMessage2("Request Successful!");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
                // You can show an error message here
                displayMessage("Request Failed! :(");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    private void displayMessage2(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}

