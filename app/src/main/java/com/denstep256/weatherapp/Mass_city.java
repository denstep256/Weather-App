package com.denstep256.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Mass_city extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass_city);
    }
    public void StartPageMoscow(View v) {
        Intent intent = new Intent(this, Moscow.class);
        startActivity(intent);
    }
}