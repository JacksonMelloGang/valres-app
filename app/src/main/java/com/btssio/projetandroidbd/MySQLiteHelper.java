package com.btssio.projetandroidbd;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bdDigicode";
 
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); 
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE users ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "login TEXT, "+
                "password TEXT )";

        String CREATE_CODE_TABLE = "CREATE TABLE code (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "numero INTEGER," +
                "salle INTEGER," +
                "month MONTH)" ;

        String CREATE_SALLE_TABLE = "CREATE TABLE salle (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT)";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CODE_TABLE);
        db.execSQL(CREATE_SALLE_TABLE);

        // Creation d'un jeu d'essai
        db.execSQL("INSERT INTO users VALUES(1,'a@gmail.com',123)");
        db.execSQL("INSERT INTO users VALUES(2,'b@gmail.com',456)");

        // codeId, numero, salle, mois
        db.execSQL("INSERT INTO code VALUES(1,123,1, 12)");
        db.execSQL("INSERT INTO code VALUES(2,456,2, 3)");

        db.execSQL("INSERT INTO salle VALUES(1,'Majorelle')");
        db.execSQL("INSERT INTO salle VALUES(2,'Gruber')");
        db.execSQL("INSERT INTO salle VALUES(3,'Lamour')");
        db.execSQL("INSERT INTO salle VALUES(4,'Longwy')");
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS code");
        db.execSQL("DROP TABLE IF EXISTS salle");
    }
    
    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */
 
    private static final String TABLE_USERS = "users";
    private static final String TABLE_DIGICODE = "code";
    private static final String TABLE_SALLE = "salle";

    private static final String USERS_KEY_ID = "id";
    private static final String USERS_KEY_LOGIN = "login";
    private static final String USERS_KEY_PASSWORD = "password";

    private static final String DIGICODE_KEY_ID = "id";
    private static final String DIGICODE_KEY_NUMERO = "numero";

    private static final String SALLE_ID = "id";
    private static final String SALLE_LIBELLE = "libelle";


    
    private static final String[] USERS_COLUMNS = {USERS_KEY_ID,USERS_KEY_LOGIN,USERS_KEY_PASSWORD};
    private static final String[] DIGICODE_COLUMNS = {DIGICODE_KEY_ID,DIGICODE_KEY_NUMERO};
    private static final String[] SALLE_COLUMNS = {SALLE_ID,SALLE_LIBELLE};

    public boolean login(String login, String password) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE login=? AND password=?", new String[]{login,password});
    	
    	if (mCursor != null) {
    		// S'il y a un resultat...
	    	if(mCursor.getCount() > 0)
	    	{
	    		return true;
	    	}
	    }
    	
	    return false;
	}

    public boolean registerCode(int code, int salle, Date date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("numero", code);
        values.put("salle", salle);
        values.put("date", date.toString());

        db.insert("digicode", null, values);
        db.close();

        return true;
    }

    public int recupDigicode(int salle, int month){

        Log.i("recupDigicode", "salle: " + salle + " month: " + month);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_DIGICODE + " WHERE salle=? AND month=?", new String[]{String.valueOf(salle), String.valueOf(month)});
        int code = 0;
        if (mCursor != null) {
            // if result
            if(mCursor.getCount() > 0)
            {
                mCursor.moveToFirst();
                code = mCursor.getInt(1);
            }
        }
        return code;
    }

    public String[] recupSalle(){

        String[] salles = new String[0];

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_SALLE, null);

        if (mCursor != null) {
            // if result
            if(mCursor.getCount() > 0)
            {
                while(mCursor.moveToNext()){
                    String[] temp = new String[salles.length + 1];
                    for(int i = 0; i < salles.length; i++){
                        temp[i] = salles[i];
                    }
                    temp[temp.length - 1] = mCursor.getString(1);
                    salles = temp;
                }
            }
        }
        
        return salles;
    }
}
