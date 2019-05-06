package com.ynov.apprecipe.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ynov.apprecipe.model.Measure;
import com.ynov.apprecipe.model.Weight;
import com.ynov.apprecipe.service.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

public class MeasurementManager {

    public static final String TABLE_NAME = "measure";
    public static final String KEY_MEASUREMENT = "measurement";
    public static final String KEY_TYPE_MEASUREMENT = "type";
    public static final String KEY_ID_MEASUREMENT = "id_measurement";
    public static final String KEY_DATE_MEASUREMENT = "date_measurement";
    /*public static final String CREATE_TABLE_MEASURE = "CREATE TABLE" +TABLE_NAME+
            "(" + " "+KEY_ID_MEASUREMENT+" INTEGER PRIMARY KEY autoincrement," +
            "" + " "+KEY_DATE_MEASUREMENT+",INTEGER NOT NULL" +
            "" + " "+KEY_TYPE_MEASUREMENT+",TEXT NOT NULL" +
            "" + " "+KEY_MEASUREMENT+"INTEGER NOT NULL);";*/

    public static final String CREATE_TABLE_MEASURE = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_MEASUREMENT+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            " "+KEY_DATE_MEASUREMENT+" TEXT NOT NULL," +
            " "+KEY_TYPE_MEASUREMENT+" TEXT NOT NULL," +
            " "+KEY_MEASUREMENT+" REAL NOT NULL" +
            ");";


    private MySQLiteOpenHelper maBaseSQLite;
    private SQLiteDatabase db;

    // Constructeur
    public MeasurementManager(Context context)
    {
        maBaseSQLite = MySQLiteOpenHelper.getInstance(context);
    }

    public void open()
    {
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addMeasure(Measure measure) {

        // Ajout d'un enregistrement dans la table

        Date date = new Date();
        android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
        String dateFound = dateFormat.format("dd/MM/yyyy", date).toString();

        ContentValues values = new ContentValues();
        values.put(KEY_MEASUREMENT, measure.getValue());
        values.put(KEY_DATE_MEASUREMENT,dateFound);
        values.put(KEY_TYPE_MEASUREMENT, measure.getType());
        /*values.put(KEY_DATE_WEIGHT, new Date().getTime());*/
        /*values.put(KEY_DATE_WEIGHT, dateFormat.format(date));*/

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateWeight(Measure measure) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_MEASUREMENT,measure.getValue());

        String where = KEY_MEASUREMENT+" = ?";
        String[] whereArgs = {measure.getId_measure()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    /*public int removeMeasure(Measure measure) {

        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_MEASUREMENT+" = ?";
        String[] whereArgs = {measure.getId_measure()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }*/

    public int removeMeasure(Measure measure) {

        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_MEASUREMENT+" = ?";
        String[] whereArgs = {measure.getId_measure()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }


    public Measure getMeasure(int id) {
        // Retourne la mesure dont l'id est passé en paramètre

        Measure measure=new Measure(0,"18/11/2018",0,"Bras");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_MEASUREMENT+ "="+id, null);
        if (c.moveToFirst()) {

            measure.setId_measure(c.getInt(c.getColumnIndex(KEY_ID_MEASUREMENT)));
            measure.setDate(c.getString(c.getColumnIndex(KEY_DATE_MEASUREMENT)));
            measure.setValue(c.getFloat(c.getColumnIndex(KEY_MEASUREMENT)));

            c.close();
        }

        return measure;
    }


    public Measure getLast(){
        db = maBaseSQLite.getReadableDatabase();
        Measure measure = null;
        String req = "select * from weight";
        Cursor cursor = db.rawQuery(req,null);
        cursor.moveToLast();
        if (!cursor.isAfterLast()){

            int id = cursor.getInt(cursor.getColumnIndex(KEY_ID_MEASUREMENT));
            float measurement = cursor.getFloat(cursor.getColumnIndex(KEY_MEASUREMENT));
            String date = cursor.getString(cursor.getColumnIndex(KEY_DATE_MEASUREMENT));
            String type = cursor.getString(cursor.getColumnIndex(KEY_TYPE_MEASUREMENT));

            measure = new Measure(id,date,measurement,type);
        }
        cursor.close();
        return measure;
    }

    public ArrayList<Measure> getMeasureType(String type){

        ArrayList<Measure> measureArray = new ArrayList<>();
        db = maBaseSQLite.getReadableDatabase();
        String req ="SELECT * FROM " +TABLE_NAME+ " WHERE " +KEY_TYPE_MEASUREMENT+ "='"+type+"'ORDER BY " +KEY_DATE_MEASUREMENT+ " DESC ";
        Cursor cursor = db.rawQuery(req, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Measure measure = new Measure(cursor.getInt(cursor.getColumnIndex(KEY_ID_MEASUREMENT)), cursor.getString(cursor.getColumnIndex(KEY_DATE_MEASUREMENT)), cursor.getFloat(cursor.getColumnIndex(KEY_MEASUREMENT)),cursor.getString(cursor.getColumnIndex(KEY_TYPE_MEASUREMENT)));
            measureArray.add(measure);
            cursor.moveToNext();
        }

        return measureArray;
    }

    public ArrayList<Measure> getAllMeasure() {
        ArrayList<Measure> measureArray = new ArrayList<Measure>();
        db = maBaseSQLite.getReadableDatabase();
        String strSql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_ID_MEASUREMENT + " DESC ";
        Cursor cursor = db.rawQuery(strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Measure measure = new Measure(cursor.getInt(cursor.getColumnIndex(KEY_ID_MEASUREMENT)), cursor.getString(cursor.getColumnIndex(KEY_DATE_MEASUREMENT)), cursor.getFloat(cursor.getColumnIndex(KEY_MEASUREMENT)),cursor.getString(cursor.getColumnIndex(KEY_TYPE_MEASUREMENT)));
            measureArray.add(measure);
            cursor.moveToNext();
        }

        return measureArray;
    }
}
