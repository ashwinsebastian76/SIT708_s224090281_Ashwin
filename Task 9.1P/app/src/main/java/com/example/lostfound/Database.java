package com.example.lostfound;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    static final String DB_NAME = "database.db";
    static final int DB_VER = 2; // ⬅️ Bump the version to trigger upgrade

    public Database(Context c) {
        super(c, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = "CREATE TABLE my_table (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "phone TEXT, " +
                "description TEXT, " +
                "location TEXT, " +
                "type TEXT, " +
                "date TEXT, " +
                "latitude REAL, " +
                "longitude REAL)";
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS my_table");
        onCreate(db);
    }
}
