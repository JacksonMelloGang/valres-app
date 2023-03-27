package com.btssio.projetandroidbd.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.btssio.projetandroidbd.MySQLiteHelper;
import com.btssio.projetandroidbd.R;

import java.util.Calendar;

public class ChoixDateSalle extends AppCompatActivity {

    final MySQLiteHelper db = new MySQLiteHelper(ChoixDateSalle.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_date_salle);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        ListView lvSalle = (ListView) findViewById(R.id.listSalles);

        TextView txMois = (TextView) findViewById(R.id.txMois);
        TextView txSalle = (TextView) findViewById(R.id.txSalle);

        Button button = (Button) findViewById(R.id.button);

        // add items in lvSalles
        String[] salles = db.recupSalle();

        lvSalle.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvSalle.setClickable(true);
        lvSalle.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, salles));

        final Long[] time = new Long[1];

//        lvSalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String salle = (String) lvSalle.getItemAtPosition(position);
//                int numSalle = 0;
//                numSalle = position+1;
//
//                txSalle.setText(String.format("Salle n°%s : %s", numSalle, salle));
//            }
//        });
//
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "-" + month + "-" + year;

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                view.setDate(calendar.getTimeInMillis(), true, true);

                time[0] = calendar.getTimeInMillis();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get code from salle
                int numSalle = lvSalle.getCheckedItemPosition()+1;
                long date = time[0];

                if(date == 0){
                    AlertDialog alertDialog = new AlertDialog.Builder(ChoixDateSalle.this).create();
                    alertDialog.setTitle("Code");
                    alertDialog.setMessage("Aucune date sélectionnée");
                    alertDialog.show();

                    return;
                }

                // get date
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(time[0]);

                int month = calendar.get(Calendar.MONTH)+1;

                if(numSalle == 0){
                    AlertDialog alertDialog = new AlertDialog.Builder(ChoixDateSalle.this).create();
                    alertDialog.setTitle("Code");
                    alertDialog.setMessage("Aucune salle sélectionnée");
                    alertDialog.show();

                    return;
                }

                int code = db.recupDigicode(numSalle, month);

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
}