package com.ynov.apprecipe.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ynov.apprecipe.model.Weight;
import com.ynov.apprecipe.service.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

public class WeightManager {

    private static final String TABLE_NAME = "weight";
    public static final String KEY_WEIGHT = "poids";
    public static final String KEY_DATE_WEIGHT = "date_weight";
    public static final String KEY_ID_WEIGHT = "id_weight";
    public static final String CREATE_TABLE_WEIGHT = "CREATE TABLE " + TABLE_NAME +
            " (" +
            " " + KEY_ID_WEIGHT + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            " " + KEY_DATE_WEIGHT + " TEXT NOT NULL," +
            " " + KEY_WEIGHT + " REAL NOT NULL" +
            ");";

    private MySQLiteOpenHelper maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public WeightManager(Context context) {
        maBaseSQLite = MySQLiteOpenHelper.getInstance(context);
    }

    public void open() {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addWeight(Weight weight) {
        // Ajout d'un enregistrement dans la table

            /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();*/
        Date date = new Date();
        android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
        String dateFound = dateFormat.format("dd/MM/yyyy", date).toString();

        ContentValues values = new ContentValues();
        values.put(KEY_WEIGHT, weight.getValue());
        values.put(KEY_DATE_WEIGHT, dateFound);

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME, null, values);
    }

    public int updateWeight(Weight weight) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_WEIGHT, weight.getValue());

        String where = KEY_WEIGHT + " = ?";
        String[] whereArgs = {weight.getId_weight() + ""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int removeWeight(Weight weight) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_WEIGHT + " = ?";
        String[] whereArgs = {weight.getId_weight() + ""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Weight getWeight(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        Weight weight = new Weight(0, "18/11/2018", 0);

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID_WEIGHT + "=" + id, null);
        if (c.moveToFirst()) {

            /*Date date = new Date();*/
                /*SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String strDate = formatter.format(date);*/
            /*String pattern = "MM/dd/yyyy HH:mm:ss";*/

            // Create an instance of SimpleDateFormat used for formatting
            // the string representation of date according to the chosen pattern
            /* DateFormat df = new SimpleDateFormat(pattern);*/

            // Get the today date using Calendar object.
            /* Date today = Calendar.getInstance().getTime();*/
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            /* String todayAsString = df.format(today);*/

            weight.setDate(c.getString(c.getColumnIndex(KEY_DATE_WEIGHT)));
            weight.setId_weight(c.getInt(c.getColumnIndex(KEY_ID_WEIGHT)));
            weight.setValue(c.getInt(c.getColumnIndex(KEY_WEIGHT)));
            c.close();
        }

        return weight;
    }

    public Weight getLast() {
        db = maBaseSQLite.getReadableDatabase();
        Weight weight = null;
        String req = "select * from weight";
        Cursor cursor = db.rawQuery(req, null);
        cursor.moveToLast();
        if (!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex(KEY_ID_WEIGHT));
            float poids = cursor.getFloat(cursor.getColumnIndex(KEY_WEIGHT));
            String date = cursor.getString(cursor.getColumnIndex(KEY_DATE_WEIGHT));

            weight = new Weight(id, date, poids);
        }
        cursor.close();
        return weight;
    }

    public ArrayList<Weight> getAllWeight() {
        ArrayList<Weight> weightArray = new ArrayList<Weight>();
        db = maBaseSQLite.getReadableDatabase();
        String strSql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " +KEY_ID_WEIGHT + " DESC ";
        Cursor cursor = db.rawQuery(strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Weight weight = new Weight(cursor.getInt(cursor.getColumnIndex(KEY_ID_WEIGHT)), cursor.getString(cursor.getColumnIndex(KEY_DATE_WEIGHT)), cursor.getFloat(cursor.getColumnIndex(KEY_WEIGHT)));
            weightArray.add(weight);
            cursor.moveToNext();
        }

        return weightArray;

    /*public Cursor getWeight() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }*/

    }
}
