package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private fetchData dataFetcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference the TextView by ID
        TextView myTextView = findViewById(R.id.myTextView);
        // Initialize the RequestQueue with the application context
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        // Initialize fetchData instance
        dataFetcher = new fetchData(queue, myTextView);

        // Reference the Spinner by ID
        Spinner userSelectionSpinner = findViewById(R.id.userSelectionSpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.user_types_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        userSelectionSpinner.setAdapter(spinnerAdapter);

        // Set up spinner item selection listener
        userSelectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String userType = parent.getItemAtPosition(position).toString();
                handleUserSelection(userType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Set up the refresh button
        Button refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(view -> {
            // Handle refresh button click
            dataFetcher.getData();
        });
    }

    private void handleUserSelection(String userType) {
        Log.d(TAG, "Selected user type: " + userType);
        dataFetcher.setUserType(userType);
        dataFetcher.getData();

        if (userType.equals("Faculty") || userType.equals("Resident") || userType.equals("Visitor") || userType.equals("Commuter")) {
            findViewById(R.id.refreshButton).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.refreshButton).setVisibility(View.GONE);
        }

        // Check if the selected user type is "Admin" and start LoginActivity
        if (userType.equals("Admin")) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
