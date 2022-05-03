package com.example.cs468;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs468.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cs468.databinding.ActivityMainBinding;


public class LoginActivity extends AppCompatActivity {
    private LoginActivity binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        File directory = getFilesDir();
        File myObj = new File(directory, "data.txt");
        try {
            if (!(myObj.exists() && !myObj.isDirectory())) {
                System.out.println("data.txt created: " + myObj.getName());
                JSONObject jsonObject = new JSONObject();
                FileWriter fileWriter = new FileWriter(myObj.getAbsoluteFile());
                fileWriter.write(jsonObject.toString());
                fileWriter.flush();
                fileWriter.close();
            } else {
                System.out.println("data.txt already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        EditText Username = findViewById(R.id.username);
        EditText Password = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.login);
        Button signupButton = findViewById(R.id.signup);

        loginButton.setOnClickListener(v -> {
            String username = Username.getText().toString();
            String password = Password.getText().toString();
            Profile curUser = new Profile(username, password);

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
                    jsonData = new JSONObject(response);
                    ;
                } else {
                    Toast.makeText(LoginActivity.this, "username and/or password invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println(jsonData.toString());

                if (jsonData.has(username) && jsonData.getJSONObject(username).getString("password").equals(password)) {
                    System.out.println(username);
                } else {
                    Toast.makeText(LoginActivity.this, "username and/or password invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.putExtra("username", username);
            i.putExtra("previous", "login");
            startActivity(i);
            finish();
        });

        signupButton.setOnClickListener(v -> {
            String username = Username.getText().toString();
            String password = Password.getText().toString();
            Profile curUser = new Profile(username, password);

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
                    jsonData = new JSONObject(response);
                    ;
                } else {
                    jsonData = new JSONObject();
                }

                if (jsonData.has(username)) {
                    Toast.makeText(LoginActivity.this, "user already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject curUserJson = new JSONObject();
                JSONArray pastListings = new JSONArray();
                JSONArray curListings = new JSONArray();
                curUserJson.put("username", username);
                curUserJson.put("password", password);
                curUserJson.put("description", "Likes to cook! I swim in my free time which is why my stomach is a black hole. Boba is life - if you see any good places, pls DM me!");
                curUserJson.put("contact", "630-570-1868");
                curUserJson.put("area", "Champaign,IL");
                curUserJson.put("pastListings", pastListings);
                curUserJson.put("curListings", curListings);

                jsonData.put(username, curUserJson);
                System.out.println(jsonData.toString());


                FileWriter fileWriter = new FileWriter(myObj.getAbsoluteFile());
                fileWriter.write(jsonData.toString());
                fileWriter.flush();
                fileWriter.close();

                Toast.makeText(LoginActivity.this, "Created account", Toast.LENGTH_SHORT).show();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
