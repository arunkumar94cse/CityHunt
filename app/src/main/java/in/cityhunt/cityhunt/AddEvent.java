package in.cityhunt.cityhunt;

import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import in.cityhunt.cityhunt.datas.SharedPreferencesData;

public class AddEvent extends AppCompatActivity implements View.OnClickListener {

    private EditText title,venue,desp;
    private SharedPreferencesData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Button submit = (Button)findViewById(R.id.submit);
        data = new SharedPreferencesData(this);
        title = (EditText) findViewById(R.id.title);
        venue = (EditText)findViewById(R.id.venue);
        desp = (EditText)findViewById(R.id.short_desp);
        assert submit != null;
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String titlestr = title.getText().toString();
        String venuestr = venue.getText().toString();
        String despstr = desp.getText().toString();
        if (titlestr.length()==0){
            Toast.makeText(getApplicationContext(),"Enter the title",Toast.LENGTH_SHORT).show();
        }else if (venuestr.length()==0){
            Toast.makeText(getApplicationContext(),"Enter the venue",Toast.LENGTH_SHORT).show();
        }else if (despstr.length()==0){
            Toast.makeText(getApplicationContext(),"Enter the desp",Toast.LENGTH_SHORT).show();
        }else {
            JSONObject object = data.getLoginDetails();
            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Utilities.API_HOME_URL + "events/eventAddMail",
                    new JSONObject().put("email",object.optString(SharedPreferencesData.EMAIL))
                            .put("title",titlestr)
                            .put("venue",venuestr)
                            .put("desp",despstr),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (response.optBoolean("status")) {
                                finish();
                            }
                            Log.e("status",response.toString());
                        }
                    },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.getMessage());
                    }
                });
                Mysingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
