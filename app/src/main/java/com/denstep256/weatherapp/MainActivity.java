package com.denstep256.weatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText user_field;
    private TextView result_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_field = findViewById(R.id.user_field);
        Button main_button = findViewById(R.id.main_button);
        result_info = findViewById(R.id.result_info);

        main_button.setOnClickListener(v -> {
            if(user_field.getText().toString().trim().equals(""))
                Toast.makeText(MainActivity.this, R.string.no_users_input, Toast.LENGTH_LONG).show();
            else {
                String city = user_field.getText().toString();
                String key = "5a09686f1b9d2e0392dcb820a4d93bd8";
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key + "&units=metric&lang=ru";

                new GetURLData().execute(url);
            }
        });


    }
    public void StartNewPage(View v) {
        Intent intent = new Intent(this, Mass_city.class);
        startActivity(intent);
    }

    private class GetURLData extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            result_info.setText("Ожидайте");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpsURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String  line = "";

                while ((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null)
                    connection.disconnect();

                try {
                    if(reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsn_obj = new JSONObject(result);
                result_info.setText("Температура: " + jsn_obj.getJSONObject("main").getDouble("temp"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }



    }
}