package in.cityhunt.cityhunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import in.cityhunt.cityhunt.datas.SharedPreferencesData;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email_address,password,name;
    private SharedPreferencesData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        data = new SharedPreferencesData(this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button submit = (Button)findViewById(R.id.signup_submit);
        email_address =(EditText)findViewById(R.id.email);
        password =(EditText)findViewById(R.id.password);
        name =(EditText)findViewById(R.id.name);

        if (getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assert submit != null;
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signup_submit) {
            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Utilities.API_HOME_URL + "user/cityhuntRegister",
                        new JSONObject()
                                .put("emailAddress",email_address.getText().toString())
                                .put("cityhuntPassword",password.getText().toString())
                                .put("name",name.getText().toString()),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("response", String.valueOf(response));
                                try {
                                    if (response.getInt("status")==0){
                                        Toast.makeText(getApplicationContext(),"Emails already exists",Toast.LENGTH_LONG).show();
                                    }else if (response.getInt("status")==1){
                                        JSONObject jsonObject = response.getJSONObject("result");
                                        data.insertUserData(jsonObject.getString("user_name"),
                                                jsonObject.getInt("user_id"),jsonObject.getString("user_email"),jsonObject.getString("profile_path"));
                                        startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                        finish();
                                    }
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }
}
