package com.example.test;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    private String userType;
    private TextView myTextView;
    private RequestQueue queue;
    private RadioGroup myRadioGroup;
    private fetchData dataFetcher;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference the TextView by ID
        myTextView = findViewById(R.id.myTextView);

        //Reference Radio Group by ID
        myRadioGroup = findViewById(R.id.userSelectionRadioGroup);
        // Initialize the RequestQueue with the application context
        queue = Volley.newRequestQueue(getApplicationContext());

        dataFetcher = new fetchData(queue, myTextView);
        // Set up the refresh button click listener
        Button refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle refresh button click
                dataFetcher.getData();
            }
        });

        // Fetch data when the app is opened
        dataFetcher.getData();
    }


    private void addRadioButtonLogic() {
        RadioGroup userSelectionRadioGroup = findViewById(R.id.userSelectionRadioGroup);
        userSelectionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle radio button selection

                if (R.id.facultyRadioButton == checkedId) {
                    handleUserSelection("Faculty");
                }
                else if (R.id.residentRadioButton == checkedId){
                    handleUserSelection("Resident");
                }
                else if (R.id.commuterRadioButton == checkedId){
                    handleUserSelection("Commuter");
                }
                else if (R.id.visitorRadioButton == checkedId){
                    handleUserSelection("Visitor");
                }
            }
        });
    }
    private void handleUserSelection(String userType) {
        if (userType == "Faculty") {
            dataFetcher.getData();
            findViewById(R.id.refreshButton).setVisibility(View.VISIBLE);
        }
        else if (userType == "Resident"){
            dataFetcher.getData();
            findViewById(R.id.refreshButton).setVisibility(View.VISIBLE);
        }
        else if (userType == "Commuter"){
            dataFetcher.getData();
            findViewById(R.id.refreshButton).setVisibility(View.VISIBLE);
        }
        else if (userType == "Visitor"){
            dataFetcher.getData();
        }
        // Handle user selection based on userType
        // For example, update UI or perform actions specific to the selected user type
    }

}
