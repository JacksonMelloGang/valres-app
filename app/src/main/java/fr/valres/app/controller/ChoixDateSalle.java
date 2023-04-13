package fr.valres.app.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Date;

import fr.valres.app.MySQLiteHelper;
import fr.valres.app.R;
import fr.valres.app.utils.ValresWebsiteGet;

public class ChoixDateSalle extends AppCompatActivity {

    final MySQLiteHelper db = new MySQLiteHelper(ChoixDateSalle.this);
    public static final String HTTP_SALLE = "http://172.16.225.170:8080/api/salles";
    private String[] salles = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_date_salle);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        ListView lvSalle = (ListView) findViewById(R.id.listSalles);

        TextView txMois = (TextView) findViewById(R.id.txMois);
        TextView txSalle = (TextView) findViewById(R.id.txSalle);

        Button button = (Button) findViewById(R.id.button);

        // check if android device is connected to internet
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() == null){
            Toast.makeText(ChoixDateSalle.this, "Pas de connexion internet", Toast.LENGTH_SHORT).show();
            return;
        }


        new ValresWebsiteGet(ChoixDateSalle.this).execute(HTTP_SALLE);

        // add items in lvSalles
        // String[] salles = {"Majorelle", "Gruber", "Lamour", "Longwy"};

        lvSalle.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, salles));

        lvSalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String salle = (String) parent.getItemAtPosition(position);
                int numSalle = 0;
                numSalle = position+1;
                lvSalle.setItemChecked(position, true);

                txSalle.setText(String.format("Salle n°%s : %s", numSalle, salle));

            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month + "/" + year;
                txMois.setText(date);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get code from salle
                int numSalle = lvSalle.getCheckedItemPosition()+1;
                Date date = new Date(calendarView.getDate());

                // if no date selected or no salle selected
                if(txMois.getText().toString().length() == 0 || txSalle.getText().toString().length() == 0){
                    AlertDialog alertDialog = new AlertDialog.Builder(ChoixDateSalle.this).create();
                    alertDialog.setTitle("Code");
                    alertDialog.setMessage("Aucune salle ou date sélectionnée");
                    alertDialog.show();

                    return;
                }

                int code = db.recupDigicode(numSalle, date);

                if(code == 0){
                    AlertDialog alertDialog = new AlertDialog.Builder(ChoixDateSalle.this).create();
                    alertDialog.setTitle("Code");
                    alertDialog.setMessage("Aucun code trouvé");
                    alertDialog.show();

                    return;
                }

                Toast.makeText(ChoixDateSalle.this, String.format("Code : %s", code), Toast.LENGTH_LONG).show();
                AlertDialog alertDialog = new AlertDialog.Builder(ChoixDateSalle.this).create();
                alertDialog.setTitle("Code");
                alertDialog.setMessage(String.format("Code : %s", code));
                alertDialog.show();

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