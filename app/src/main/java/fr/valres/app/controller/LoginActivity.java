package fr.valres.app.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fr.valres.app.MySQLiteHelper;
import fr.valres.app.R;
import fr.valres.app.api.ValresAPI;
import fr.valres.app.controller.api.ValresAPIToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    final MySQLiteHelper db = new MySQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText ztLogin = (EditText) findViewById(R.id.ztLogin);
        EditText ztPassword = (EditText) findViewById(R.id.ztPassword);

        Button btnLogin = (Button) findViewById(R.id.btConnect);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = ztLogin.getText().toString();
                String password = ztPassword.getText().toString();

                if (login.length() == 0 || password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Login ou mot de passe vide", Toast.LENGTH_SHORT).show();
                } else {
                    Context context = getApplicationContext();

                    new ValresAPIToken(context).execute("http://172.16.225.170:8080/api/v1/token", login, password);
                }
            }
        });

    }
}