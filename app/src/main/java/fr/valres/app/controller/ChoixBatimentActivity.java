package fr.valres.app.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import fr.valres.app.R;
import fr.valres.app.api.command.Command;
import fr.valres.app.api.command.getBatimentSallesCommand;
import fr.valres.app.model.Batiment;
import fr.valres.app.repository.BatimentRepository;

@SuppressLint({"MissingInflatedId"})
public class ChoixBatimentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_batiment);

        ListView lvBatiment = findViewById(R.id.lvBatiment);
        Button btnRetour = findViewById(R.id.bTcancel);
        Button btnSubmit = findViewById(R.id.bTsubmit);

        // get batiments
        HashMap<Integer, Batiment> batiments = BatimentRepository.getInstance().getBatiments();
        ArrayList<String> batimentNames = new ArrayList<>();

        // get batiment names
        for (int i = 0; i < batiments.size(); i++) {
            batimentNames.add(batiments.get(i).getName());
        }

        lvBatiment.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // Permet de sélectionner un seul item dans le ListView
        lvBatiment.setAdapter(new android.widget.ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, batimentNames));

        // Retour
        btnRetour.setOnClickListener(view -> finish());


        btnSubmit.setOnClickListener(view -> {
            // Submit
            Bundle extras = new Bundle();
            extras.putInt("batiment", lvBatiment.getCheckedItemPosition()+1);

            Intent intent = new Intent(ChoixBatimentActivity.this, ChoixDateSalle.class);
            intent.putExtras(extras);
            startActivity(intent);
        });



    }
}