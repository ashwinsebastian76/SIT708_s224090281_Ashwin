package com.example.lostfound;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Viewlist extends AppCompatActivity {

    TextView t1, t2, t3, t4, t5, t6;
    SQLiteDatabase db;
    Database help;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        EdgeToEdge.enable(this);
        setContentView(R.layout.view_list);

        help = new Database(this);
        db = help.getWritableDatabase();

        t1 = findViewById(R.id.textViewName);
        t2 = findViewById(R.id.textViewDescription);
        t3 = findViewById(R.id.textViewPhone);
        t4 = findViewById(R.id.textViewLocation);
        t5 = findViewById(R.id.textViewDate);
        t6 = findViewById(R.id.textViewLostFound);

        Intent in = getIntent();
        String[] d = in.getStringArrayExtra("data");

        t1.setText("Name: " + d[1]);
        t2.setText("Description: " + d[2]);
        t3.setText("Phone: " + d[3]);
        t4.setText("Location: " + d[4]);
        t5.setText("Date: " + d[5]);
        t6.setText("Type: " + d[6].toUpperCase());

        findViewById(R.id.btn_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.delete("my_table", "id = ?", new String[]{String.valueOf(d[0])});
                Toast.makeText(Viewlist.this, "Item Deleted Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
