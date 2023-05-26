package fr.valres.app.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import fr.valres.app.CONFIG;
import fr.valres.app.MySQLiteHelper;
import fr.valres.app.R;
import fr.valres.app.api.ValresAPI;
import fr.valres.app.api.ValresAPIToken;
import fr.valres.app.api.command.Command;
import fr.valres.app.api.command.getCategoriesCommand;
import fr.valres.app.api.command.getSallesCommand;

public class LoginActivity extends AppCompatActivity {


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

                    try { // TODO: ADD CONFIG FILE TO GET URL
                        AsyncTask task = new ValresAPIToken(context).execute(CONFIG.API_URL, login, password);
                        task.get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(ValresAPI.getInstance() != null){
                        Toast.makeText(LoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, AccueilActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Connexion échouée", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }
}