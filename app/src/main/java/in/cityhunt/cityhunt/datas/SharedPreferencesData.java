package in.cityhunt.cityhunt.datas;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class SharedPreferencesData {

    private final SharedPreferences user_data;
    private static final String PREF_NAME = "CITYHUNT_DATA";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String ID = "ID";
    public static final String PROFILE_PIC_PATH = "PROFILE_PIC_PATH";
    public static final String STATUS = "STATUS";
    private final SharedPreferences.Editor edit_user_data;

    public SharedPreferencesData(Context context) {
        user_data = context.getSharedPreferences(PREF_NAME, 0);
        edit_user_data = user_data.edit();
        edit_user_data.apply();
    }

    public void insertUserData(String name,int id,String email,String profilePicPath){
        edit_user_data.putBoolean(STATUS,true);
        edit_user_data.putString(NAME,name);
        edit_user_data.putString(EMAIL,email);
        edit_user_data.putInt(ID,id);
        edit_user_data.putString(PROFILE_PIC_PATH,profilePicPath);
        edit_user_data.apply();
    }
    public JSONObject getLoginDetails(){
        JSONObject data = new JSONObject();
        try {
            data.put(ID,user_data.getInt(ID,0));
            data.put(NAME,user_data.getString(NAME,""));
            data.put(EMAIL,user_data.getString(EMAIL,""));
            data.put(PROFILE_PIC_PATH,user_data.getString(PROFILE_PIC_PATH,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
    public boolean getLoginStatus(){
        return user_data.getBoolean(STATUS,false);
    }
    public void logout(){
        edit_user_data.clear();
        edit_user_data.apply();
    }
}
