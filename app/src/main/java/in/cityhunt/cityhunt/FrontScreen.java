package in.cityhunt.cityhunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.cityhunt.cityhunt.datas.EventStorage;
import in.cityhunt.cityhunt.datas.SharedPreferencesData;

public class FrontScreen extends AppCompatActivity {

    private EventStorage storage;
    private SharedPreferencesData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_screen);
        data = new SharedPreferencesData(this);
        storage = new EventStorage(getApplicationContext());

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
                                storage.insert(object.getInt("event_id"), object.getString("event_name"), object.getString("event_state"),
                                        object.getString("event_city"), object.getString("event_venue"), object.getString("event_type"),
                                        object.getString("event_description"), object.getString("event_fb"), object.getString("event_url"),
                                        object.getString("event_contact_person"), object.getString("event_cantact_email"), object.getString("event_contact_num"),
                                        object.getDouble("event_latitude"), object.getDouble("event_longitude"), object.getString("event_start"), object.getString("event_end"),
                                        object.getString("event_poster"), object.getString("created_date"));
                            }
                            if (data.getLoginStatus()) {
                                startActivity(new Intent(FrontScreen.this, MainActivity.class));
                            }else {
                                startActivity(new Intent(FrontScreen.this,LoginActivity.class));
                            }
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.getMessage());
            }
        });
        Mysingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
