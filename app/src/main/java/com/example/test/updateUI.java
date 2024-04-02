package com.example.test;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class updateUI {
    private TextView myTextView;
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

                // Extract data from the JSON object
                String lotName = jsonObject.getString("lot_name");
                String availableSpots = jsonObject.getString("available_spots");
                String availableHandicapSpots = jsonObject.getString("available_handicap_spots");

                // Append the information to the displayText StringBuilder
                        displayText.append("Lot Name: ").append(lotName).append("\n")
                        .append("Available Spots: ").append(availableSpots).append("\n")
                        .append("Available Handicap Spots: ").append(availableHandicapSpots).append("\n");
            }

            // Set or update the text with the concatenated information
            myTextView.setText(displayText.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
