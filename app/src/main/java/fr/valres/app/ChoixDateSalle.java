package fr.valres.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChoixDateSalle extends AppCompatActivity {

    final MySQLiteHelper db = new MySQLiteHelper(ChoixDateSalle.this);
    static int numSalle = 0;
    static Date date = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_date_salle);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        ListView lvSalle = (ListView) findViewById(R.id.listSalles);

        Button btAffichageDigicode = (Button) findViewById(R.id.btAfficheDigicode);

        // add items in lvSalles
        String[] salles = {"Majorelle", "Gruber", "Lamour", "Longwy"};

        lvSalle.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, salles));
        lvSalle.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lvSalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //String salle = (String) parent.getItemAtPosition(position);
                numSalle = position+1;
                lvSalle.setItemChecked(position, true);

            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                DateFormat dateF = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    date = dateF.parse(dayOfMonth + "-"+ month +"-"+year);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Button btRetour = (Button) findViewById(R.id.btRetour);

        btRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btAffichageDigicode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numSalle==0&&date==null) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Veuillez selectionner une salle ainsi qu'une date",Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    int digicode = db.recupDigicode(numSalle, date);
                    if (digicode==0) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Aucun digicode n'est disponible pour cette salle à cette date",Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else {
                        Intent intent = new Intent(ChoixDateSalle.this, AfficherDigicodeActivity.class);
                        intent.putExtra("digicode", digicode);
                        startActivity(intent);
                    }
                }

                /*// get code from salle
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
                alertDialog.show();*/

            }
        });

    }
}