package com.example.test;

import android.widget.TextView;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class updateUI {

    private TextView myTextView;

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
