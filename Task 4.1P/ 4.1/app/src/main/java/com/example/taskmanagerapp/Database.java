package com.example.taskmanagerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "tasks.db";
    private static final int DB_VER = 2;
    private static final String TABLE_NAME = "tasks";

    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_DESC = "description";
    private static final String COL_DATE = "dueDate";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_DESC + " TEXT, " +
                COL_DATE + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addTask(String n, String d, String date) {
        SQLiteDatabase myDb = this.getWritableDatabase();
        ContentValues box = new ContentValues();
        box.put(COL_NAME, n);
        box.put(COL_DESC, d);
        box.put(COL_DATE, date);
        return myDb.insert(TABLE_NAME, null, box);
    }

    public List<Task> getAllTasks() {
        ArrayList<Task> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.query(
                TABLE_NAME,
                new String[]{COL_ID, COL_NAME, COL_DESC, COL_DATE},
                null, null, null, null,
                COL_DATE + " ASC"
        );

        if (cur.moveToFirst()) {
            do {
                int id = cur.getInt(0);
                String name = cur.getString(1);
                String desc = cur.getString(2);
                String date = cur.getString(3);

                Task t = new Task(id, name, desc, date);
                list.add(t);
            } while (cur.moveToNext());
        }

        cur.close();
        return list;
    }

    public void updateTask(int id, String name, String desc, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues box = new ContentValues();
        box.put(COL_NAME, name);
        box.put(COL_DESC, desc);
        box.put(COL_DATE, date);
        db.update(TABLE_NAME, box, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
