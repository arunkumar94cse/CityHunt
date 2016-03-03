package in.cityhunt.cityhunt;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EventStorage storage;
    private int[] tabIcons = {
            R.drawable.theater18,
            R.drawable.four45,
            R.drawable.weekly7,
            R.drawable.stickman218
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage = new EventStorage(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Utilities.API_HOME_URL + "events/getEvents", null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response", String.valueOf(response));
                storage.clear();
                try {
                    JSONArray array = response.getJSONArray("response");
                    for (int i=0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        storage.insert(object.getInt("event_id"),object.getString("event_name"),object.getString("event_state"),
                                object.getString("event_city"),object.getString("event_venue"),object.getString("event_type"),
                                object.getString("event_description"),object.getString("event_fb"),object.getString("event_url"),
                                object.getString("event_contact_person"),object.getString("event_cantact_email"),object.getString("event_contact_num"),
                                object.getDouble("event_latitude"),object.getDouble("event_longitude"),object.getString("event_start"),object.getString("event_end"),
                                object.getString("event_poster"),object.getString("created_date"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Mysingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new Popular();
                case 1:
                    return new categories();
                case 2:
                    return new Calendar();
                case 3:
                    return new User_profile();
            }
            return new Popular();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "";
                case 1:
                    return "";
                case 2:
                    return "";
                case 3:
                    return "";
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
