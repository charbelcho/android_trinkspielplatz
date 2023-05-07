package com.charbelchougourou.trinkspielplatz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new com.charbelchougourou.trinkspielplatz.DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all nochnie from the database.
     *
     * @return a List of nochnie
     */
    public List<String> getNochnieList() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT text FROM trinkspiele_nochnie", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getEherList() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT text FROM trinkspiele_eher", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getWahrheitList() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT text FROM trinkspiele_wahrheit", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getPflichtList() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT text FROM trinkspiele_pflicht", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getNochniesLikeCharSequence(CharSequence sequence) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT (text) FROM trinkspiele_nochnie WHERE text LIKE \"%"+sequence+"%\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public void insertNochnie(String text) {
        ContentValues values = new ContentValues();
        values.put("text", text);
        long rowId = database.insert("trinkspiele_nochnie", null, values);
    }

    public void insertEher(String text) {
        ContentValues values = new ContentValues();
        values.put("text", text);
        long rowId = database.insert("trinkspiel_eher", null, values);
    }

    public void insertWahrheit(String text) {
        ContentValues values = new ContentValues();
        values.put("text", text);
        long rowId = database.insert("trinkspiele_wahrheit", null, values);
    }

    public void insertPflicht(String text) {
        ContentValues values = new ContentValues();
        values.put("text", text);
        long rowId = database.insert("trinkspiele_pflicht", null, values);
    }

}
