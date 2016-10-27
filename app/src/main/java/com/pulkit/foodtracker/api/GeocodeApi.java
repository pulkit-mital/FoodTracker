package com.pulkit.foodtracker.api;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.pulkit.foodtracker.Utils.Constants;
import com.pulkit.foodtracker.app.App;
import com.pulkit.foodtracker.data.DbClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pulkitmital on 27/10/16.
 */

public class GeocodeApi extends Request<JSONObject> {

    private Response.Listener<JSONObject> listener;

    public GeocodeApi(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, String url) {

        super(Request.Method.GET, url, errorListener);
        this.listener = listener;

    }

    static String getApiUrl() {
        String URL = Constants.BASE_URL + Constants.GEOCODE_API;
        String dummy = URL + "?version=5&data=";
        try {
            JSONObject o = new JSONObject();

            String encoded_params = URLEncoder.encode(o.toString(), "UTF-8");
            String url = dummy + encoded_params;
            Log.d("URL", url);
            return url;
        } catch (Exception e) {
        }
        return dummy;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject jsonObject = new JSONObject(jsonString);


            try {
                App.getDb().deleteTable(DbClass.TABLE_RESTAURANT);
                // JSONArray array = obj.getJSONArray("data");
                JSONArray jsonArray = jsonObject.getJSONArray("nearby_restaurants");

                ArrayList<ContentValues> cons = new ArrayList<ContentValues>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    ContentValues appCon = new ContentValues();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    JSONObject restrauntJson = jsonObject1.getJSONObject("restaurant");

                    String id = restrauntJson.getString("id");
                    String name = restrauntJson.getString("name");

                    JSONObject locationObject = restrauntJson.getJSONObject("location");
                    String address = locationObject.getString("address");
                    String locality = locationObject.getString("locality");
                    String city = locationObject.getString("city");
                    double latitude = locationObject.getDouble("latitude");
                    double longitude = locationObject.getDouble("longitude");
                    String cuisines = restrauntJson.getString("cuisines");
                    String cost = restrauntJson.getString("average_cost_for_two");
                    String thumb = restrauntJson.getString("thumb");
                    String image = restrauntJson.getString("featured_image");

                    JSONObject ratingObject = restrauntJson.getJSONObject("user_rating");
                    String rating = ratingObject.getString("aggregate_rating");


                    appCon.put(DbClass.RESTRAUNT_ID, id);
                    appCon.put(DbClass.RESTRAUNT_NAME, name);
                    appCon.put(DbClass.RESTRAUNT_ADDRESS, address);
                    appCon.put(DbClass.RESTRAUNT_LOCALITY, locality);
                    appCon.put(DbClass.RESTRAUNT_CITY, city);
                    appCon.put(DbClass.RESTRAUNT_LATITUDE, latitude);
                    appCon.put(DbClass.RESTRAUNT_LONGITUDE, longitude);
                    appCon.put(DbClass.RESTRAUNT_CUISINES, cuisines);
                    appCon.put(DbClass.RESTRAUNT_COST, cost);
                    appCon.put(DbClass.RESTRAUNT_THUMB, thumb);
                    appCon.put(DbClass.RESTRAUNT_FEATURED_IMAGE, image);
                    appCon.put(DbClass.RESTRAUNT_RATING, rating);

                    cons.add(appCon);
                }


                //INSERTING CITIES
                App.getDb().insertRestraunt(cons, SQLiteDatabase.CONFLICT_REPLACE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Response.success(jsonObject,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.e("", "city api exception : " + e);
        } catch (Exception e) {
            Log.e("", "city api exception : " + e);
        }
        return null;
    }

    @Override
    protected void deliverResponse(JSONObject jsonObject) {
        listener.onResponse(jsonObject);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        try {
            Log.e("", "Volley Error          : " + volleyError.getMessage());
            Log.e("", "Volley error response : " + volleyError.networkResponse.statusCode);
            return volleyError;
        } catch (Exception e) {
            return volleyError;
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("user-key", "4a13e5d2e3e7b0450a0e960200eb1873");
        return params;
    }
}