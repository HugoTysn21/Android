package com.ynov.apprecipe.model;

public class Measure {

    private int id_measure;
    private String date;
    private float value;
    private String type;

    public Measure(int id_measure, String date, float value, String type) {
        this.id_measure = id_measure;
        this.date = date;
        this.value = value;
        this.type = type;
    }

    public int getId_measure() {
        return id_measure;
    }

    public void setId_measure(int id_measure) {
        this.id_measure = id_measure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
