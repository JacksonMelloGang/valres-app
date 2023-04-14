package fr.valres.app;

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
                "password TEXT)";

        String CREATE_CODE_TABLE = "CREATE TABLE code (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "numero INTEGER," +
                "salle INTEGER," +
                "date DATE)" ;

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CODE_TABLE);

        // Creation d'un jeu d'essai
        
        db.execSQL("INSERT INTO users VALUES(1,'a@gmail.com',123)");
        db.execSQL("INSERT INTO users VALUES(2,'b@gmail.com',456)");



        //Création de la table digicode
        String CREATE_SALLE_TABLE = "CREATE TABLE salles ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "name TEXT)";

        db.execSQL(CREATE_SALLE_TABLE);
        // Creation d'un jeu d'essai

        db.execSQL("INSERT INTO salles VALUES(1,'Majorelle')");
        db.execSQL("INSERT INTO salles VALUES(2,'Gruber')");
        db.execSQL("INSERT INTO salles VALUES(3,'Lamour')");
        db.execSQL("INSERT INTO salles VALUES(4,'Longwy')");

        db.execSQL("INSERT INTO code VALUES(1,1234,1,'2023-04-01')");
            }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");

    }
    
    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */
 
    private static final String TABLE_USERS = "users";
    private static final String TABLE_DIGICODE = "code";
 
    private static final String USERS_KEY_ID = "id";
    private static final String USERS_KEY_LOGIN = "login";
    private static final String USERS_KEY_PASSWORD = "password";

    private static final String DIGICODE_KEY_ID = "id";
    private static final String DIGICODE_KEY_NUMERO = "numero";


    
    private static final String[] USERS_COLUMNS = {USERS_KEY_ID,USERS_KEY_LOGIN,USERS_KEY_PASSWORD};
    private static final String[] DIGICODE_COLUMNS = {DIGICODE_KEY_ID,DIGICODE_KEY_NUMERO};

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

    public int recupDigicode(int salle, Date date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_DIGICODE + " WHERE salle=? AND date=?", new String[]{String.valueOf(salle), date.toString()});
        int code = 0;
        if (mCursor != null) {
            // if result
            if(mCursor.getCount() > 0)
            {
                code = mCursor.getInt(1);
            }
        }
        return code;
    }

}
