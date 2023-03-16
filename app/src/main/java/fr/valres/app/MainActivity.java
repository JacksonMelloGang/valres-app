package fr.valres.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    public static Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MainActivity.connection = DriverManager.getConnection("jdbc:mysql://localhost/valres", "root", "");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        });

        thread.start();

    }
}