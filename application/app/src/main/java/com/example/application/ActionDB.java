package com.example.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ActionDB {
    private SQLiteDatabase db;
    public ActionDB(Context context) {
        DBHelper dbh = new DBHelper(context);
        this.db = dbh.getWritableDatabase();
    }

    void insert(String table, String city, double temperature) {
        ContentValues cv = new ContentValues();
        cv.put("city", city);
        cv.put("temperature", temperature);
        db.insert(table, null, cv);
    }

    ContentValues read(String table) {
        ContentValues cv = new ContentValues();
        Cursor c = db.query(table, null, null, null, null, null, null);
        if (c != null) {
            c.moveToLast();
            cv.put("city",c.getString(c.getColumnIndex("city")));
            cv.put("temperature",c.getString(c.getColumnIndex("temperature")));
            c.close();
            return cv;
        }
        return null;
    }

    void delete(String table) {
        db.delete(table, null, null);
    }
}
