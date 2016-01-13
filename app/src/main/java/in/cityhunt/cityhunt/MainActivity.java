package in.cityhunt.cityhunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EventStorage storage;
    private Fragment fragment;
    private String title = "City Hunt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage = new EventStorage(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.events_calender).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Calender.class));
            }
        });
        findViewById(R.id.nearby).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NearBy.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,Utilities.HOME_URL+"events/getEventsList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                storage.clear();
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i=0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        storage.insert(object.getInt("event_id"),object.getString("event_name"),object.getString("event_state"),
                                object.getString("event_city"),object.getString("event_venue"),object.getInt("event_type"),
                                object.getString("event_description"),object.getString("event_fb"),object.getString("event_url"),
                                object.getString("event_contact_person"),object.getString("event_cantact_email"),object.getString("event_contact_num"),
                                object.getDouble("event_latitude"),object.getDouble("event_longitude"),object.getString("event_start"),object.getString("event_end"),
                                object.getString("event_poster"),object.getString("event_organizer"),object.getString("created_date"));
                    }
                    fragment = new Popular();
                    showFragment();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                findViewById(R.id.loader).setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("resp",error.getMessage());
                fragment = new Popular();
                showFragment();
                findViewById(R.id.loader).setVisibility(View.INVISIBLE);
            }
        });
        Mysingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.home) {
            fragment = new Popular();
            title = "City Hunt";
        } else if (id == R.id.profile) {
            fragment = new Profile();
            title = "Profile";
        } else if (id == R.id.favourite) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        showFragment();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
        if (getSupportActionBar()!=null)
            getSupportActionBar().setTitle(title);
    }
}
