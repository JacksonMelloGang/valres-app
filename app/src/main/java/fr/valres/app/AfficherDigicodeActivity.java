package fr.valres.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AfficherDigicodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_digicode);

        TextView digicode = (TextView) findViewById(R.id.digicode);

        Button btRetour = (Button) findViewById(R.id.btRetour);

        //Set digicode
        Bundle extras = getIntent().getExtras();
        String digicodeSTR = String.valueOf(extras.getInt("digicode"));
        digicode.setText(digicodeSTR);

        btRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}