package com.example.test;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
    private fetchData dataFetcher;
    private Spinner userSelectionSpinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference the TextView by ID
        myTextView = findViewById(R.id.myTextView);
        // Initialize the RequestQueue with the application context
        queue = Volley.newRequestQueue(getApplicationContext());
        // Initialize fetchData instance
        dataFetcher = new fetchData(queue, myTextView);

        // Reference the Spinner by ID
        userSelectionSpinner = findViewById(R.id.userSelectionSpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        spinnerAdapter = ArrayAdapter.createFromResource(this,
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
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle refresh button click
                dataFetcher.getData();
            }
        });
        // Fetch data when the app is opened
        // dataFetcher.getData();
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
    }
}


