package com.example.lostfound;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    Database helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        helper = new Database(this);
        db = helper.getReadableDatabase();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        showMarkers();
    }

    private void showMarkers() {
        Cursor c = db.rawQuery("SELECT name, type, latitude, longitude FROM my_table", null);
        if (c != null && c.moveToFirst()) {
            boolean isFirst = true;

            do {
                String name = c.getString(0);
                String type = c.getString(1);
                double lat = c.getDouble(2);
                double lon = c.getDouble(3);

                LatLng point = new LatLng(lat, lon);
                map.addMarker(new MarkerOptions()
                        .position(point)
                        .title(type.toUpperCase() + ": " + name));

                if (isFirst) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 10f));
                    isFirst = false;
                }

            } while (c.moveToNext());

            c.close();
        }
    }
}
