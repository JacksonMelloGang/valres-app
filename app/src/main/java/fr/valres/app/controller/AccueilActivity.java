package fr.valres.app.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.valres.app.R;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Button btnSalle = (Button) findViewById(R.id.btSalle);
        Button btnWifi = (Button) findViewById(R.id.btWifi);
        Button btnParam = (Button) findViewById(R.id.btParam);
        Button btnDeco = (Button) findViewById(R.id.btDeco);

        btnSalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccueilActivity.this, ChoixDateSalle.class);
                startActivity(intent);
            }
        });

        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccueilActivity.this, WifiActivity.class);
                startActivity(intent);
            }
        });
        btnDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}