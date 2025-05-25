package com.example.lostfound;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class CreateAd extends AppCompatActivity {

    EditText e1, e2, e3, e4, e5;
    Button b, b2;
    Database h;
    SQLiteDatabase db;
    RadioButton r1, r2;

    double lat = 0.0;
    double lon = 0.0;
    final int AUTOCOMPLETE_REQUEST_CODE = 100;

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
        b2 = findViewById(R.id.buttonLocation);
        r1 = findViewById(R.id.radioButtonLost);
        r2 = findViewById(R.id.radioButtonFound);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyD-KALuQF8Y2ZLAW-gUpnKSarZypohQS1U");
        }

        e5.setFocusable(false);
        e5.setOnClickListener(view -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(CreateAd.this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        });

        b2.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(CreateAd.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CreateAd.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                getCurrentLocation();
            }
        });

        b.setOnClickListener(v -> addToDb());
    }

    private void getCurrentLocation() {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                e5.setText("Current Location: " + lat + ", " + lon);
            } else {
                Toast.makeText(this, "Couldn't get location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted. Please tap again.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                e5.setText(place.getAddress());
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    lat = latLng.latitude;
                    lon = latLng.longitude;
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(this, "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        }
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
        val.put("latitude", lat);
        val.put("longitude", lon);

        db.insert("my_table", null, val);
        Toast.makeText(this, "Advertisement created successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
