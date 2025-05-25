package com.example.lostfound;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ListAd extends AppCompatActivity {

    RecyclerView r;
    Item a;
    ArrayList<String[]> d;
    Database h;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        EdgeToEdge.enable(this);
        setContentView(R.layout.list_ad);

        h = new Database(this);
        db = h.getWritableDatabase();

        r = findViewById(R.id.rv_list_advert);
        r.setLayoutManager(new LinearLayoutManager(this));

        d = new ArrayList<>();
        a = new Item(this, d);
        r.setAdapter(a);

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        d.clear();

        Cursor c = db.query("my_table", null, null, null, null, null, "date DESC");

        if (c != null && c.moveToFirst()) {
            do {
                @SuppressLint("Range") String i = String.valueOf(c.getLong(c.getColumnIndex("id")));
                @SuppressLint("Range") String n = c.getString(c.getColumnIndex("name"));
                @SuppressLint("Range") String des = c.getString(c.getColumnIndex("description"));
                @SuppressLint("Range") String ph = c.getString(c.getColumnIndex("phone"));
                @SuppressLint("Range") String loc = c.getString(c.getColumnIndex("location"));
                @SuppressLint("Range") String dat = c.getString(c.getColumnIndex("date"));
                @SuppressLint("Range") String ty = c.getString(c.getColumnIndex("type"));

                d.add(new String[]{i, n, des, ph, loc, dat, ty});
            } while (c.moveToNext());
            c.close();
        }

        a.notifyDataSetChanged();
    }
}
