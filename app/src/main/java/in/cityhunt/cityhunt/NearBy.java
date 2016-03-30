package in.cityhunt.cityhunt;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import in.cityhunt.cityhunt.datas.EventStorage;

public class NearBy extends AppCompatActivity implements OnMapReadyCallback, CompoundButton.OnCheckedChangeListener {

    private GoogleMap mMap;
    private EventStorage storage;
    private RadioButton all,fest,sports,socials,entertainment;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        all = (RadioButton)findViewById(R.id.all);
        fest = (RadioButton)findViewById(R.id.fest);
        sports = (RadioButton)findViewById(R.id.sports);
        socials = (RadioButton)findViewById(R.id.socials);
        entertainment = (RadioButton)findViewById(R.id.entertainment);
        all.setOnCheckedChangeListener(this);
        fest.setOnCheckedChangeListener(this);
        sports.setOnCheckedChangeListener(this);
        socials.setOnCheckedChangeListener(this);
        entertainment.setOnCheckedChangeListener(this);
        storage = new EventStorage(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            buttonView.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
            buttonView.setTextColor(Color.parseColor("#888888"));
        }
    }



    public void showMarker(Cursor cursor){
        cursor.moveToFirst();
        LatLng latLng;
        for (int i=0;i<cursor.getCount();i++){
            latLng = new LatLng(cursor.getDouble(12),cursor.getDouble(13));

            mMap.addMarker(new MarkerOptions().position(latLng).title(cursor.getString(1)).icon(BitmapDescriptorFactory.fromResource(R.drawable.fests)));
            if (i == cursor.getCount()-1)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));
            cursor.moveToNext();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        cursor = storage.getDataRecent();
        showMarker(cursor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}