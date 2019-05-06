package com.ynov.apprecipe.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.ynov.apprecipe.managers.MeasurementManager;
import com.ynov.apprecipe.managers.WeightManager;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {


    // Property
    private static final String baseName = "myFirstAndroidApp";
    private static final int baseVersion = 1;
    private static MySQLiteOpenHelper sInstance;

    public static synchronized MySQLiteOpenHelper getInstance(Context context) {
        if (sInstance == null) { sInstance = new MySQLiteOpenHelper(context); }
        return sInstance;
    }


    public MySQLiteOpenHelper(Context context) {
        super(context, baseName,null,baseVersion);
    }

    /*
    * Si changement de base de donnée
    */
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(WeightManager.CREATE_TABLE_WEIGHT);
            db.execSQL(MeasurementManager.CREATE_TABLE_MEASURE);
        }catch (SQLiteException e){
            e.printStackTrace();
            e.getMessage();
        }


    }

    /*
    * si changement de version
    */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
