package fr.valres.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Button btnSalle = (Button) findViewById(R.id.btSalle);
        Button btnWifi = (Button) findViewById(R.id.btWifi);
        Button btnParam = (Button) findViewById(R.id.btParam);
        Button btnQuitter = (Button) findViewById(R.id.btQuitter);

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
        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }
}