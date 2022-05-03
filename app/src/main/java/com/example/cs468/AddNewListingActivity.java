package com.example.cs468;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddNewListingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_listing);

        //set variables for each text field
        EditText Name = findViewById(R.id.Name);

        EditText Quantity = findViewById(R.id.Quantity);

        EditText Description = findViewById(R.id.Description);

        Button SubmitListing = findViewById(R.id.SubmitListing);

        SubmitListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get username from intent
                Bundle extras = getIntent().getExtras();
                String username = extras.getString("username");

                String name = Name.getText().toString();
                //String imageFile = img.getData();
                String quantity = Quantity.getText().toString();
                String description = Description.getText().toString();
                List<Profile> customerProfiles = new ArrayList<>();
                Listing curListing = new Listing(name, null, null, null, quantity, null, description, customerProfiles);

                //get previous JSON
                String file = readFile();

                //get curlisting json
                JSONObject jsonData = null;
                try {
                    jsonData = new JSONObject(file);
                    JSONObject curUserJson = jsonData.getJSONObject(username);
                    JSONArray curListingsJson = curUserJson.getJSONArray("curListings");

                    //make new json listing
                    JSONObject newListingJSON = new JSONObject();
                    newListingJSON.put("name", name);
                    newListingJSON.put("imageFile", null);
                    newListingJSON.put("unitCost", null);
                    newListingJSON.put("timeFrame", null);
                    newListingJSON.put("quantity", quantity);
                    newListingJSON.put("location", null);
                    newListingJSON.put("description", description);

                    //random num of customers
                    Random rand = new Random();
                    int int_random = rand.nextInt(Integer.parseInt(quantity));
                    newListingJSON.put("customers", int_random);

                    System.out.println(newListingJSON.toString());

                    //add new json listing to curlisting json
                    curListingsJson.put(newListingJSON);

                    writeNewListingToFile(username,jsonData);

                } catch (JSONException | IOException jsonException) {
                    jsonException.printStackTrace();
                }


                //send username & previous screen
                Intent i = new Intent(AddNewListingActivity.this, LocationListingActivity.class);
                i.putExtra("username", username);
                i.putExtra("previous", "addNewListing");
                startActivity(i);
                finish();
            }

        });
    }
    /** reads file and returns a string version of it*/
    public String readFile() {
        File directory = getFilesDir();
        File myObj = new File(directory, "data.txt");

        try {
            FileReader fileReader = new FileReader(myObj.getAbsoluteFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String response = stringBuilder.toString();
            JSONObject jsonData;
            if (!response.isEmpty()) {
                return response;
            } else {
                System.out.println("file contents are empty");
                return "File contents are empty!";
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR: IO or json exception";
        }
    }

    public void writeNewListingToFile(String username, JSONObject update) throws IOException, JSONException {
        //open file
        File directory = getFilesDir();
        File myObj = new File(directory, "data.txt");

        //write to file
        FileWriter fileWriter = new FileWriter(myObj.getAbsoluteFile());
        fileWriter.write(update.toString());
        fileWriter.flush();
        fileWriter.close();
    }
}