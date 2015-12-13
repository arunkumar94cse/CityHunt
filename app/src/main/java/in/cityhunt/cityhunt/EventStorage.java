package in.cityhunt.cityhunt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventStorage extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CityHunt.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "cityhunt_event_details";
    public static final String EVENT_ID = "event_id";
    public static final String EVENT_NAME = "event_name";
    public static final String EVENT_STATE = "event_state";
    public static final String EVENT_CITY = "event_city";
    public static final String EVENT_VENUE = "event_venue";
    public static final String EVENT_TYPE = "event_type";
    public static final String EVENT_DESCRIPTION = "event_description";
    public static final String EVENT_FB = "event_fb";
    public static final String EVENT_URL = "event_url";
    public static final String EVENT_CONTACT_PERSON = "event_contact_person";
    public static final String EVENT_CONTACT_EMAIL = "event_contact_email";
    public static final String EVENT_CONTACT_NUM = "event_contact_num";
    public static final String EVENT_LATITUDE = "event_lat";
    public static final String EVENT_LONGITUDE = "event_lon";
    public static final String EVENT_START = "event_start";
    public static final String EVENT_END = "event_end";
    public static final String EVENT_POSTER = "event_poster";
    public static final String EVENT_ORG = "event_organizer";
    public static final String CREATED_DATE = "created_date";
    private static final String TABLE_MESSAGE_CREATE = "CREATE TABLE " + TABLE_NAME +  " ("
            + EVENT_ID + " INTEGER, "
            + EVENT_NAME + " VARCHAR(255), "
            + EVENT_STATE + " VARCHAR(255), "
            + EVENT_CITY + " VARCHAR(255), "
            + EVENT_VENUE + " VARCHAR(255), "
            + EVENT_TYPE + " INTEGER, "
            + EVENT_DESCRIPTION + " VARCHAR(255), "
            + EVENT_FB + " VARCHAR(255), "
            + EVENT_URL + " VARCHAR(255), "
            + EVENT_CONTACT_PERSON + " VARCHAR(255), "
            + EVENT_CONTACT_EMAIL + " VARCHAR(255), "
            + EVENT_CONTACT_NUM + " VARCHAR(255), "
            + EVENT_LATITUDE + " DOUBLE, "
            + EVENT_LONGITUDE + " DOUBLE, "
            + EVENT_START + " DATETIME, "
            + EVENT_END + " DATETIME, "
            + EVENT_POSTER + " VARCHAR(255), "
            + EVENT_ORG + " VARCHAR(255), "
            + CREATED_DATE + " DATETIME);";

    private static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public EventStorage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_MESSAGE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP);
        onCreate(db);
    }

    public void insert(int id,String name,String state,String city,String venue,int type,
                       String description,String fb,String url,String person, String email,String num,
                       double latitude,double longitude,String start,String end,String poster,String org,String created_date){
        long rowId = -1;
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(EVENT_ID, id);
            values.put(EVENT_NAME, name);
            values.put(EVENT_STATE, state);
            values.put(EVENT_CITY, city);
            values.put(EVENT_VENUE, venue);
            values.put(EVENT_TYPE, type);
            values.put(EVENT_DESCRIPTION, description);
            values.put(EVENT_FB, fb);
            values.put(EVENT_URL, url);
            values.put(EVENT_CONTACT_PERSON, person);
            values.put(EVENT_CONTACT_EMAIL, email);
            values.put(EVENT_CONTACT_NUM, num);
            values.put(EVENT_LATITUDE, latitude);
            values.put(EVENT_LONGITUDE, longitude);
            values.put(EVENT_START, start);
            values.put(EVENT_END, end);
            values.put(EVENT_POSTER, poster);
            values.put(EVENT_ORG, org);
            values.put(CREATED_DATE, created_date);
            rowId = db.insert(TABLE_NAME, null, values);

        } catch (SQLiteException e){
            Log.e("event_table", "insert()", e);
        } finally {
            Log.d("event_table", "insert(): rowId=" + rowId);
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = getWritableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME+";";
        return db.rawQuery(SELECT_QUERY, null);
    }
    public void clear(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }
}
