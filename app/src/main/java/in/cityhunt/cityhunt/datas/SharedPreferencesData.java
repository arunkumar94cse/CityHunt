package in.cityhunt.cityhunt.datas;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesData {

    private final SharedPreferences user_data;
    private static final String PREF_NAME = "CITYHUNT_DATA";
    private static final String NAME = "NAME";
    private static final String EMAIL = "EMAIL";
    private static final String ID = "ID";
    private static final String PROFILE_PIC_PATH = "PROFILE_PIC_PATH";
    private static final String STATUS = "STATUS";
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
    public boolean getLoginStatus(){
        return user_data.getBoolean(STATUS,false);
    }
    public void logout(){
        edit_user_data.clear();
        edit_user_data.apply();
    }
}
