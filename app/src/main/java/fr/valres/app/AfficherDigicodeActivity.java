package fr.valres.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AfficherDigicodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_digicode);

        Button btRetour = (Button) findViewById(R.id.btRetour);

        btRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(AfficherDigicodeActivity.this, ChoixDateSalle.class);
                startActivity(intent);*/
                finish();
            }
        });
    }
}