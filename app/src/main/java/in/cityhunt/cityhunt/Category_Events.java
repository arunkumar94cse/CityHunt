package in.cityhunt.cityhunt;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import in.cityhunt.cityhunt.datas.EventStorage;

public class Category_Events extends AppCompatActivity {

    EventStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Data> datas = new ArrayList<>();

        storage = new EventStorage(this);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        Cursor cursor = storage.getCategoryEvents(type);
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            Data data = new Data();
            data.setId(cursor.getInt(0));
            data.setName(cursor.getString(1));
            data.setCity(cursor.getString(3));
            data.setStart_date(cursor.getString(14));
            data.setPoster_url(cursor.getString(16));
            datas.add(data);
            cursor.moveToNext();
        }

        RecyclerView popular_list = (RecyclerView) findViewById(R.id.category_view);
        popular_list.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        popular_list.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new CustomListAdapter(datas,this);
        popular_list.setAdapter(mAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
