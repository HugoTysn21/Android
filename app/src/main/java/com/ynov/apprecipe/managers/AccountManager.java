package com.ynov.apprecipe.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ynov.apprecipe.model.Account;
import com.ynov.apprecipe.service.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

public class AccountManager {

    public static final String TABLE_NAME = "accounting";
    public static final String KEY_HEIGHT = "taille";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_ID_ACCOUNT = "id_account";
    public static final String KEY_START_WEIGHT = "start_weight";
    public static final String KEY_DATE_OF_BIRTH = "date_of_birth";
    /*public static final String CREATE_TABLE_MEASURE = "CREATE TABLE" +TABLE_NAME+
            "(" + " "+KEY_ID_MEASUREMENT+" INTEGER PRIMARY KEY autoincrement," +
            "" + " "+KEY_DATE_MEASUREMENT+",INTEGER NOT NULL" +
            "" + " "+KEY_TYPE_MEASUREMENT+",TEXT NOT NULL" +
            "" + " "+KEY_MEASUREMENT+"INTEGER NOT NULL);";*/

    public static final String CREATE_TABLE_MEASURE = "CREATE TABLE " + TABLE_NAME +
            " (" +
            " " + KEY_ID_ACCOUNT + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            " " + KEY_GENDER + " TEXT NOT NULL," +
            " " + KEY_DATE_OF_BIRTH + " TEXT NOT NULL," +
            " " + KEY_START_WEIGHT + " REAL NOT NULL," +
            " " + KEY_HEIGHT + " REAL NOT NULL" +
            ");";


    private MySQLiteOpenHelper maBaseSQLite;
    private SQLiteDatabase db;

    // Constructeur
    public AccountManager(Context context) {
        maBaseSQLite = MySQLiteOpenHelper.getInstance(context);
    }

    public void open() {
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addAccount(Account account) {

        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_HEIGHT, account.getHeight());
        values.put(KEY_GENDER, account.getGender());
        values.put(KEY_DATE_OF_BIRTH, account.getDate_of_birth());
        values.put(KEY_START_WEIGHT, account.getStart_weight());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME, null, values);
    }

}
