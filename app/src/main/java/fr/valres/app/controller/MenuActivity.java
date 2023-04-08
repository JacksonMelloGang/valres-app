package fr.valres.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnCode = (Button) findViewById(R.id.bt_code);
        Button btnWifi = (Button) findViewById(R.id.bt_wifi);
        Button btnSettings = (Button) findViewById(R.id.bt_param);

        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ChoixSalleActivity.class);
                startActivity(intent);
            }
        });
     }





}
