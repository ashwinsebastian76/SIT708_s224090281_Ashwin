package com.example.lostfound;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        findViewById(R.id.newAd).setOnClickListener(view -> {
            Intent goToNew = new Intent(MainActivity.this, CreateAd.class);
            startActivity(goToNew);
        });

        findViewById(R.id.listAds).setOnClickListener(view -> {
            Intent goToList = new Intent(MainActivity.this, ListAd.class);
            startActivity(goToList);
        });

        findViewById(R.id.mapButton).setOnClickListener(view -> {
            Intent goToMap = new Intent(MainActivity.this, MapActivity.class);
            startActivity(goToMap);
        });
    }
}
