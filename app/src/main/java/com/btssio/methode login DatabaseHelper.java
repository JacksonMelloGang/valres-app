public boolean login(String login, String password) {
    Log.i("test", "debut");
    try {
        createDataBase();
    } catch (IOException e) {
    }
    openDataBase();
    Log.i("test", "ouvert");
    SQLiteDatabase db = this.getWritableDatabase();
    Log.i("test","centre");
    Cursor mCursor = db.rawQuery("SELECT * FROM USERS WHERE login=? AND password=?", new String[]{login,password});
    Log.i("test","fin");

    if (mCursor != null) {
        // S'il y a un resultat...
        if(mCursor.getCount() > 0)
        {
            return true;
        }
    }

    return false;
}