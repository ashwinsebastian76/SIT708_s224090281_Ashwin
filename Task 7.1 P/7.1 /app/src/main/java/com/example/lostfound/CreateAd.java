package com.example.lostfound;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAd extends AppCompatActivity {

    EditText e1, e2, e3, e4, e5;
    Button b;
    Database h;
    SQLiteDatabase db;
    RadioButton r1, r2;

    @Override
    protected void onCreate(Bundle box) {
        super.onCreate(box);
        EdgeToEdge.enable(this);
        setContentView(R.layout.create_ad);

        h = new Database(this);
        db = h.getWritableDatabase();

        e1 = findViewById(R.id.editTextName);
        e2 = findViewById(R.id.editTextDescription);
        e3 = findViewById(R.id.editTextPhone);
        e4 = findViewById(R.id.editTextDate);
        e5 = findViewById(R.id.editTextLocation);
        b = findViewById(R.id.buttonSave);
        r1 = findViewById(R.id.radioButtonLost);
        r2 = findViewById(R.id.radioButtonFound);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDb();
            }
        });
    }

    private void addToDb() {
        String n = e1.getText().toString();
        String d = e2.getText().toString();
        String p = e3.getText().toString();
        String dat = e4.getText().toString();
        String l = e5.getText().toString();
        String t = "";

        if (r1.isChecked()) t = "lost";
        if (r2.isChecked()) t = "found";

        if (n.isEmpty() || d.isEmpty() || p.isEmpty() || dat.isEmpty() || l.isEmpty() || t.isEmpty()) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues val = new ContentValues();
        val.put("name", n);
        val.put("description", d);
        val.put("phone", p);
        val.put("date", dat);
        val.put("type", t);
        val.put("location", l);

        db.insert("my_table", null, val);
        Toast.makeText(this, "Advertisement created successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
