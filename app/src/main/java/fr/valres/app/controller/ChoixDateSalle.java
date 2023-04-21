package fr.valres.app.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

import fr.valres.app.R;
import fr.valres.app.api.ValresAPI;
import fr.valres.app.api.command.Command;
import fr.valres.app.api.command.CommandCallback;
import fr.valres.app.api.command.getCodeSalleCommand;
import fr.valres.app.model.Salle;
import fr.valres.app.repository.SalleRepository;

public class ChoixDateSalle extends AppCompatActivity {

    public static final String HTTP_SALLE = ValresAPI.getInstance().getUrlApi() + "/salles";
    private String[] salles = {};
    private long[] dateArray = new long[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_date_salle);
        dateArray[0] = new Date().getTime();

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        ListView lvSalle = (ListView) findViewById(R.id.listSalles);

        Button button = (Button) findViewById(R.id.btAfficheDigicode);

        // add items in lvSalles
        HashMap<Integer, Salle> hashSalles = SalleRepository.getInstance().getSalles();
        salles = new String[hashSalles.size()];
        int i = 0;
        for (Salle salle : hashSalles.values()) {
            salles[i] = salle.getNom();
            i++;
        }


        lvSalle.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvSalle.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, salles));

        lvSalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String salle = (String) parent.getItemAtPosition(position);
                int numSalle = 0;
                numSalle = position+1;
                lvSalle.setItemChecked(position, true);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dateArray[0] = new Date(year, month, dayOfMonth).getTime();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get code from salle
                int numSalle = lvSalle.getCheckedItemPosition()+1;
                Date date = new Date(dateArray[0]);

                HashMap<String, String> params = new HashMap<>();
                params.put("salle_id", String.valueOf(numSalle));
                params.put("year", String.valueOf(date.getYear()));
                params.put("month", String.valueOf(date.getMonth()+1));

                getCodeSalleCommand command = new getCodeSalleCommand("code", "GET", params);
                command.execute(new CommandCallback() {
                    @Override
                    public void onSuccess() {
                        Bundle b = new Bundle();
                        b.putString("digicode", command.getCode());

                        Intent intent = new Intent(ChoixDateSalle.this, AfficherDigicodeActivity.class);
                        intent.putExtras(b);
                        startActivity(intent);
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ChoixDateSalle.this);
//                        builder.setTitle("Code");
//                        builder.setMessage(command.getCode());
//                        builder.setPositiveButton("OK", null);
//                        builder.show();
                    }

                    @Override
                    public void onError() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChoixDateSalle.this);
                        builder.setTitle("Erreur");
                        builder.setMessage("Erreur lors de la récupération du code");
                        builder.setPositiveButton("OK", null);
                        builder.show();
                    }
                });
            }
        });

    }

    public String[] getSalles() {
        return salles;
    }

    public void setSalles(String[] salles) {
        this.salles = salles;
    }
}