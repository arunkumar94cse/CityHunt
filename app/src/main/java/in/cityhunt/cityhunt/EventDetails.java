package in.cityhunt.cityhunt;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

import in.cityhunt.cityhunt.datas.EventStorage;

public class EventDetails extends AppCompatActivity implements View.OnClickListener {

    public static final String bitmapString = "poster_bitmap";
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        TextView title = (TextView) findViewById(R.id.title);
        TextView location = (TextView) findViewById(R.id.venue);
        TextView date = (TextView) findViewById(R.id.date);
        final ImageView poster = (ImageView) findViewById(R.id.bgheader);
        assert poster != null;
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable)poster.getDrawable()).getBitmap();
                startActivity(new Intent(EventDetails.this, Poster.class).putExtra(bitmapString, bitmap));
            }
        });
        EventStorage storage = new EventStorage(this);
        Intent intent = getIntent();
        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.calendar).setOnClickListener(this);
        String id = intent.getStringExtra(CustomListAdapter.ID);
        cursor = storage.getEvent(id);
        cursor.moveToFirst();
        getSupportActionBar().setTitle(cursor.getString(1));
        title.setText(cursor.getString(1));
        ImageLoader mImageLoader = Mysingleton.getInstance(this).getImageLoader();
        mImageLoader.get(Utilities.HOME_URL + cursor.getString(16),
                ImageLoader.getImageListener(poster, R.drawable.background, R.drawable.background));
        location.setText(cursor.getString(4) + ", " + cursor.getString(3));
        TextView description = (TextView)findViewById(R.id.description);
        description.setText(cursor.getString(6));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat newformat = new SimpleDateFormat("MMM''dd", Locale.ENGLISH);
        try {
            Date dateobj = format.parse(cursor.getString(14));
            Date dateobjend = format.parse(cursor.getString(15));
            if (newformat.format(dateobj).equalsIgnoreCase(newformat.format(dateobjend)))
                date.setText(newformat.format(dateobj).toUpperCase());
            else
                date.setText(newformat.format(dateobj).toUpperCase() + " - " + newformat.format(dateobjend).toUpperCase());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT,cursor.getString(1)+"\n"+cursor.getString(6));
            startActivity(sendIntent);
        }else if (v.getId()==R.id.calendar){
            Log.e("date",cursor.getString(14));
            Date date1 = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                date1 = format.parse(cursor.getString(14));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            java.util.Calendar beginTime = Calendar.getInstance();
            beginTime.setTime(date1);
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("title", cursor.getString(1));
            intent.putExtra("description", cursor.getString(6));
            intent.putExtra("eventLocation",cursor.getString(4) + ", " + cursor.getString(3));
            intent.putExtra("beginTime", beginTime.getTimeInMillis());
            startActivity(intent);
        }
    }
}
