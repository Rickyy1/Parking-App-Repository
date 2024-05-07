package com.example.test;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class updateUI {
    private final TextView myTextView;
    public updateUI(TextView textView) {
        this.myTextView = textView;
    }

    public void getUpdate(JSONArray response) {

        try {
            // Process the JSON response and update the UI
            StringBuilder displayText = new StringBuilder();

            // Assuming each item in the array is a JSONObject
            for (int i = 0; i < response.length(); i++) {

                JSONObject jsonObject = response.getJSONObject(i);
                Log.d("updateUI", "Processing object: " + jsonObject.toString());

                // Extract data from the JSON object
                String lotName = jsonObject.getString("lot_name");
                String totalSpots = jsonObject.getString("total_spots");
                String totalHandicapSpots = jsonObject.getString("handicap_spots");

                // Append the information to the displayText StringBuilder
                        displayText.append("Lot Name: ").append(lotName).append("\n")
                        .append("Available Spots: ").append(totalSpots).append("\n")
                        .append("Available Handicap Spots: ").append(totalHandicapSpots).append("\n");

                Log.d("updateUI", "Appending to displayText: " + displayText.toString());
            }

            // Set or update the text with the concatenated information
            myTextView.setText(displayText.toString());

            Log.d("updateUI", "Final displayText set to TextView: " + displayText.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
