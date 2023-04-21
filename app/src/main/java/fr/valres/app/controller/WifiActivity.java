package fr.valres.app.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.valres.app.R;

public class WifiActivity extends AppCompatActivity {
    public static String genCodeWifi()
    {
        String[] car = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String str = "";

        for (int i = 0; i < 12; i++) {

            str += car[(int)(62 * Math.random())];
        }
        return str;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        TextView codeWifi = (TextView) findViewById(R.id.codeWifi);
        codeWifi.setText(genCodeWifi());

        Button btRetour = (Button) findViewById(R.id.btRetour);
        Button btReload = (Button) findViewById(R.id.btReload);

        btReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeWifi.setText(genCodeWifi());
            }
        });
        btRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}