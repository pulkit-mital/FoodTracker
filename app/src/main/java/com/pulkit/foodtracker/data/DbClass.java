package com.pulkit.foodtracker.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.pulkit.foodtracker.pojo.Restraunts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pulkitmital on 27/10/16.
 */


public class DbClass extends SQLiteOpenHelper {


    /****************************************/
    /*
     *  TABLES
     */
    private static final int DB_VERSION = 1;
    public static final String TABLE_RESTAURANT = "table_restaurant";

    private static final String DB_NAME = "food_tracker_db.db";




    /*
     *USER TABLE COLUMNS
     */


    public static final String RESTRAUNT_ID = "restraunt_id";
    public static final String RESTRAUNT_NAME = "restraunt_name";
    public static final String RESTRAUNT_CUISINES = "restraunt_cuisines";
    public static final String RESTRAUNT_COST = "restraunt_cost";
    public static final String RESTRAUNT_THUMB = "restraunt_thumb";
    public static final String RESTRAUNT_FEATURED_IMAGE = "restraunt_featured_iamge";
    public static final String RESTRAUNT_RATING = "restraunt_rating";
    public static final String RESTRAUNT_ADDRESS = "restraunt_address";
    public static final String RESTRAUNT_LOCALITY = "locality";
    public static final String RESTRAUNT_CITY = "city";
    public static final String RESTRAUNT_LATITUDE = "restraunt_latitude";
    public static final String RESTRAUNT_LONGITUDE = "restraunt_longitude";

    /************************************/

    /*************************************/
    /**
     * CREATE TABLE QUERYS
     */
    private static final String CREATE_TABLE_RESTRAUNT = "CREATE TABLE "
            + TABLE_RESTAURANT + " ( "
            + RESTRAUNT_ID + " text PRIMARY KEY, "
            + RESTRAUNT_NAME + " integer, "
            + RESTRAUNT_CUISINES + " text, "
            + RESTRAUNT_COST + " text, "
            + RESTRAUNT_THUMB + " text, "
            + RESTRAUNT_FEATURED_IMAGE + " text, "
            + RESTRAUNT_ADDRESS + " text, "
            + RESTRAUNT_LOCALITY + " text, "
            + RESTRAUNT_CITY + " text, "
            + RESTRAUNT_LONGITUDE + " DOUBLE, "
            + RESTRAUNT_LATITUDE + " DOUBLE, "
            + RESTRAUNT_RATING + " text ); ";


    // String query = "SELECT * FROM " + DbClass.TABLE_ADDRESS + " WHERE " + DbClass.ADDRESS_IS_DELETED + " = 0 ;";
    private SQLiteDatabase db;


    public DbClass(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //context.getExternalFilesDir(null).getAbsolutePath() + "/" +
    public void initialize() {
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_RESTRAUNT);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        sqLiteDatabase.execSQL(CREATE_TABLE_RESTRAUNT);


    }


    public void insertRestraunt(ArrayList<ContentValues> cons, int flag) {
        try {
            db.beginTransaction();
            for (ContentValues entry : cons) {
                db.insertWithOnConflict(TABLE_RESTAURANT, null, entry, flag);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
        }
    }

    public void deleteTable(String tableName) {
        db.execSQL("delete from " + tableName);
    }

    public ArrayList<Restraunts> getRestraunts() {

        ArrayList<Restraunts> restraunts = new ArrayList<>();
        try {

            String selectQuery = "SELECT * from " + TABLE_RESTAURANT + " ;";
            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                Restraunts restraunt = new Restraunts();
                restraunt.setId(cursor.getString(cursor.getColumnIndex(DbClass.RESTRAUNT_ID)));
                restraunt.setName(cursor.getString(cursor.getColumnIndex(DbClass.RESTRAUNT_NAME)));
                restraunt.setAddress(cursor.getString(cursor.getColumnIndex(DbClass.RESTRAUNT_ADDRESS)));
                restraunt.setLocality(cursor.getString(cursor.getColumnIndex(DbClass.RESTRAUNT_LOCALITY)));
                restraunt.setCity(cursor.getString(cursor.getColumnIndex(DbClass.RESTRAUNT_CITY)));
                restraunt.setLatitude(cursor.getDouble(cursor.getColumnIndex(DbClass.RESTRAUNT_LATITUDE)));
                restraunt.setLongitude(cursor.getDouble(cursor.getColumnIndex(DbClass.RESTRAUNT_LONGITUDE)));
                restraunt.setCost(cursor.getString(cursor.getColumnIndex(DbClass.RESTRAUNT_COST)));
                restraunt.setThumb(cursor.getString(cursor.getColumnIndex(DbClass.RESTRAUNT_THUMB)));
                restraunt.setCuisines(cursor.getString(cursor.getColumnIndex(DbClass.RESTRAUNT_CUISINES)));
                restraunt.setImage(cursor.getString(cursor.getColumnIndex(DbClass.RESTRAUNT_FEATURED_IMAGE)));
                restraunt.setRating(cursor.getString(cursor.getColumnIndex(DbClass.RESTRAUNT_RATING)));
                restraunts.add(restraunt);
            }
            return restraunts;
        } catch (Exception ex) {
            ex.printStackTrace();
            return restraunts;
        }
    }
}
