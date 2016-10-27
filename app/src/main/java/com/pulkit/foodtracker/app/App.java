package com.pulkit.foodtracker.app;

import android.app.Application;
import android.app.ApplicationErrorReport;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pulkit.foodtracker.data.DbClass;

/**
 * Created by pulkitmital on 27/10/16.
 */

public class App extends Application {

    private static RequestQueue volleyQueue;
    private static Context context;
    private static DbClass db;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static RequestQueue getVolleyQueue() {
        if (volleyQueue == null) {
            volleyQueue = Volley.newRequestQueue(context);
        }
        return volleyQueue;
    }

    public static DbClass getDb() {
        if (db == null) {
            db = new DbClass(context);
            db.initialize();
        }
        return db;
    }
}
