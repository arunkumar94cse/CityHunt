package in.cityhunt.cityhunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;

import in.cityhunt.cityhunt.datas.SharedPreferencesData;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private CallbackManager callbackManager;
    private EditText email_address,password;
    private SharedPreferencesData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        data = new SharedPreferencesData(this);
        Button fb_login = (Button) findViewById(R.id.fb_login);
        assert fb_login != null;

        email_address = (EditText) findViewById(R.id.email_address);
        password = (EditText)findViewById(R.id.password);

        Button login = (Button) findViewById(R.id.login);

        assert login != null;
        login.setOnClickListener(this);

        fb_login.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Utilities.API_HOME_URL + "user/cityhuntSMRegister",
                                                    new JSONObject()
                                                            .put("emailAddress",object.getString("email"))
                                                            .put("name",object.getString("name"))
                                                            .put("picture",object.getJSONObject("picture").getJSONObject("data").getString("url")),
                                                    new Response.Listener<JSONObject>() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            Log.e("response", String.valueOf(response));
                                                            try {
                                                                if (response.getInt("status")==0){
                                                                    Toast.makeText(getApplicationContext(),"Error occured, Try again",Toast.LENGTH_LONG).show();
                                                                }else if (response.getInt("status")==1){
                                                                    JSONObject jsonObject = response.getJSONObject("result");
                                                                    data.insertUserData(jsonObject.getString("user_name"),
                                                                            jsonObject.getInt("user_id"),jsonObject.getString("user_email"),jsonObject.getString("profile_path"));
                                                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
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
                                            Mysingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // login cancelled
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // login error
                    }
                });

        TextView signup = (TextView)findViewById(R.id.signup);

        if (signup != null) {
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.fb_login){
            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile","email"));
        }else if (v.getId()==R.id.login){
            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Utilities.API_HOME_URL + "user/cityhuntAuth",
                        new JSONObject()
                        .put("emailAddress",email_address.getText().toString())
                        .put("cityhuntPassword",password.getText().toString()),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("response", String.valueOf(response));
                                try {
                                    if (response.getInt("status")==0){
                                        Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_LONG).show();
                                    }else if (response.getInt("status")==1){
                                        JSONObject jsonObject = response.getJSONObject("result");
                                        data.insertUserData(jsonObject.getString("user_name"),
                                                jsonObject.getInt("user_id"),jsonObject.getString("user_email"),jsonObject.getString("profile_path"));
                                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }


}


