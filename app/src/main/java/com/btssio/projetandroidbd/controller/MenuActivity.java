package com.btssio.projetandroidbd.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.btssio.projetandroidbd.R;

public class MenuActivity extends AppCompatActivity {

    private Button btnSalle;
    private Button btnWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnSalle = findViewById(R.id.btn_menusalle);
        btnWifi = findViewById(R.id.btn_menuwifi);

        btnSalle.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ChoixDateSalle.class);
            startActivity(intent);
        });

        btnWifi.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, WifiActivity.class);
            startActivity(intent);
        });
    }
}