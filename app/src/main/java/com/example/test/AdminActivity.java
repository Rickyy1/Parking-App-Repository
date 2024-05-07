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
    private EditText modifyLotName;
    private EditText modifyTotalSpots;
    private EditText modifyHandicapSpots;
    private Spinner modifySpinner;


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
        modifyLotName = findViewById(R.id.modifyLotName);
        modifyTotalSpots = findViewById(R.id.modifyTotalSpots);
        modifyHandicapSpots = findViewById(R.id.modifyHandicapSpots);
        modifySpinner = findViewById(R.id.modifySpinner);


        // Retrieve options from strings.xml and set up Spinner adapter
        String[] options = getResources().getStringArray(R.array.admin_spinner_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminSpinner.setAdapter(adapter);
        modifySpinner.setAdapter(adapter);

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

            //Clear text fields after making request
            //lotnameEditText.setText("");
            //totalSpotsEditText.setText("");
            //handicapSpotsEditText.setText("");
        });

        findViewById(R.id.deleteLotSubmit).setOnClickListener(view -> {
            // Get values from EditText fields and Spinner
            String lotName = deleteLotText.getText().toString(); // Assuming lot name is constant or retrieved from elsewhere

            // Call makeRequest method with the retrieved values
            makeDelRequest(lotName);

            //Clear text fields
            //deleteLotText.setText("");
        });

        findViewById(R.id.modifyLotSubmit).setOnClickListener(view -> {
            String modLotName = modifyLotName.getText().toString(); // Assuming lot name is constant or retrieved from elsewhere
            int modTotalSpots = Integer.parseInt(modifyTotalSpots.getText().toString());
            int modHandicapSpots = Integer.parseInt(modifyHandicapSpots.getText().toString());
            String modUserType = modifySpinner.getSelectedItem().toString(); // Get the selected item as a string

            // Call makeRequest method with the retrieved values
            makeModRequest(modLotName, modTotalSpots, modHandicapSpots, modUserType);

            //Clear text field
            //modifyLotName.setText("");
            //modifyTotalSpots.setText("");
            //modifyHandicapSpots.setText("");
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
    private static final String add_lot_url = "https://rwuparking.rwu.me/admin_add_lot_test.php/";

    // Method to make HTTP request
    private void makeAddRequest(String lotName, int totalSpots, int handicapSpots, String userType) {
        final int MAX_SPOTS = 200; // Maximum number of spots allowed
        final int MAX_HANDICAPPED = 20;

        // Check if the provided spot numbers are within the allowed range
        if (totalSpots < 0 || totalSpots > MAX_SPOTS) {
            displayMessage("Error: Total spots must be between 0 and " + MAX_SPOTS);
            return; // Exit the method early
        }
        if (handicapSpots < 0 || handicapSpots > MAX_HANDICAPPED) {
            displayMessage("Error: Handicap spots must be between 0 and " + MAX_HANDICAPPED);
            return; // Exit the method early
        }

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Formulate the request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, add_lot_url +
                "?auth=password&lot_name=" + lotName +
                "&total_spots=" + totalSpots +
                "&handicap_spots=" + handicapSpots +
                "&user_type=" + userType,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle successful response
                        displayMessage("Lot added successfully");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
                displayMessage("Error: Something went wrong. Try again");
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
                        displayMessage2("Lot removed successfully");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
                // You can show an error message here
                displayMessage("Error: Lot doesn't exist or something went wrong. Try again");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    private void displayMessage2(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private static final String mod_lot_url = "https://rwuparking.rwu.me/admin_modify_lot.php/";
    private static final int MAX_SPOTS = 200; // Maximum number of spots allowed
    private static final int MAX_HANDICAPPED = 20;

    // Method to make HTTP request
    private void makeModRequest(String modLotName, int modTotalSpots, int modHandicapSpots, String modUserType) {
        // Check if the provided spot numbers are within the allowed range
        if (modTotalSpots < 0 || modTotalSpots > MAX_SPOTS) {
            displayMessage("Error: Total spots must be between 0 and " + MAX_SPOTS);
            return; // Exit the method early
        }
        if (modHandicapSpots < 0 || modHandicapSpots > MAX_HANDICAPPED) {
            displayMessage("Error: Handicap spots must be between 0 and " + MAX_HANDICAPPED);
            return; // Exit the method early
        }

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Formulate the request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, mod_lot_url +
                "?auth=password&lot_name=" + modLotName +
                "&total_spots=" + modTotalSpots +
                "&handicap_spots=" + modHandicapSpots +
                "&user_type=" + modUserType,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle successful response
                        displayMessage("Lot modified successfully");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
                displayMessage("Error: Lot doesn't exist or something went wrong. Try again");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}

