package fr.valres.app.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import fr.valres.app.R;
import fr.valres.app.api.ValresAPI;
import fr.valres.app.api.command.Command;
import fr.valres.app.api.command.getBatimentSallesCommand;
import fr.valres.app.api.command.getBatimentsCommand;
import fr.valres.app.api.command.getCategoriesCommand;
import fr.valres.app.api.command.getSallesCommand;
import fr.valres.app.repository.BatimentRepository;
import fr.valres.app.repository.CategoryRepository;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        CategoryRepository.getInstance().getCategories().clear();
        BatimentRepository.getInstance().getBatiments().clear();


        Command categoriesCommand = new getCategoriesCommand("categories", "GET", null);
        Log.i("LoginActivity", "executing categoriesCommand");
        categoriesCommand.execute();

        Command sallesCommand = new getSallesCommand("salles", "GET", null, this);
        Log.i("LoginActivity", "executing sallesCommand");
        sallesCommand.execute();

        Command batimentCommand = new getBatimentsCommand("batiments", "GET", null);
        Log.i("LoginActivity", "executing batimentCommand");
        batimentCommand.execute();



        Button btnSalle = (Button) findViewById(R.id.btSalle);
        Button btnWifi = (Button) findViewById(R.id.btWifi);
        Button btnDeco = (Button) findViewById(R.id.btDeco);

        btnSalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccueilActivity.this, ChoixBatimentActivity.class);
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
                ValresAPI.destroyInstance();
                finish();
            }
        });
    }


}