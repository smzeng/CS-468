package com.example.cs468;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationListingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_listing);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //get previous & username from intent
            String username = extras.getString("username");
            String file = readFile();

            if (!file.isEmpty()) {
                List<Listing> curListings= getCurrentListings(file,username);

                ScrollView scrollView = new ScrollView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                scrollView.setLayoutParams(layoutParams);
                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(linearParams);

                scrollView.addView(linearLayout);
                displayCurrentListings(linearLayout, curListings);

                LinearLayout linearLayout1 = findViewById(R.id.rootContainer);
                if (linearLayout1 != null) {
                    linearLayout1.addView(scrollView);
                }
            }
        }

    }
    /** gets all current Listings for the user*/
    public List<Listing> getCurrentListings(String file, String username) {
        //check for current listing of this user
        List<Listing> curListings = new ArrayList<>();

        try {
            if (!file.isEmpty()) {
                JSONObject curUserJson = new JSONObject(file).getJSONObject(username);
                JSONArray curListingsJson = curUserJson.getJSONArray("curListings");
                for (int i = 0; i < curListingsJson.length(); i++) {
                    JSONObject listingJson = curListingsJson.getJSONObject(i);
                    String name = listingJson.getString("name");
                    Listing listing = new Listing(name, null);
                    curListings.add(listing);
                }
            } else {
                System.out.println("ERROR: USERNAME not found");
                return curListings;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return curListings;
    }

    /** displays current listings*/
    public void displayCurrentListings(LinearLayout layout, List<Listing> listings) {
        for( int i = 0; i < listings.size(); i++ ) {
//            ImageView imageView1 = new ImageView(this);
//            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(800, 800);
//            params1.gravity = Gravity.CENTER;
//            imageView1.setLayoutParams(params1);
//            imageView1.setImageURI(Uri.parse(listings.get(i).imageFile));
//            layout.addView(imageView1);

            Button b = new Button(this);
            b.setText(listings.get(i).name);
            b.setBackgroundColor(getResources().getColor(R.color.white));
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(800, 150);
            params2.gravity = Gravity.CENTER;
            params2.bottomMargin = 10;
            b.setLayoutParams(params2);
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Bundle extras = getIntent().getExtras();
                    String username = extras.getString("username");

                    Intent intent = new Intent(LocationListingActivity.this, viewLocationDetailsActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("itemName", b.getText());
                    startActivity(intent);
                }
            });
            layout.addView(b);
        }
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


    /** goes to newListing screen*/
    public void addNewListing(View view) {
        Bundle extras = getIntent().getExtras();
        String username = extras.getString("username");

        Intent intent = new Intent(this, AddNewListingActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("previous", "curListings");

        startActivity(intent);
    }
}