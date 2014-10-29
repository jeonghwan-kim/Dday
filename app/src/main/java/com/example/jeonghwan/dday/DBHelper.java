package com.example.jeonghwan.dday;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jeonghwan on 14. 10. 29..
 */
public class DBHelper extends SQLiteOpenHelper {

    String tag = "dDays";

    public DBHelper(Context context) {
        super(context, "db.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(tag, "onCreate() DBHelper.java");

        String q = "CREATE TABLE d_days " +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "  datetime DATETIME NOT NULL, " +
                "  text TEXT NOT NULL)";
         Log.d(tag, q);

        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        Log.d(tag, "onUpdrade() DBHelper.java");
    }
}
