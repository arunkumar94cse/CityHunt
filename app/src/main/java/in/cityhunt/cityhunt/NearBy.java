package in.cityhunt.cityhunt;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NearBy extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EventStorage storage;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by);

        storage = new EventStorage(this);
        cursor = storage.getData();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        cursor.moveToFirst();
        LatLng sydney;
        for (int i=0;i<cursor.getCount();i++){
            sydney = new LatLng(cursor.getDouble(12),cursor.getDouble(13));
            mMap.addMarker(new MarkerOptions().position(sydney).title(cursor.getString(1)).icon(BitmapDescriptorFactory.fromResource(R.drawable.fests)));
            if (i == cursor.getCount()-1)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12));
            cursor.moveToNext();
        }
    }
}
