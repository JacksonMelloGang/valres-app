package fr.valres.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                    if(db.login(login, password)){
                        Toast.makeText(LoginActivity.this, "Login et mot de passe OK", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, ChoixSalleActivity.class);
                        startActivity(intent);
                        
                    }
                }
            }
        });

    }
}