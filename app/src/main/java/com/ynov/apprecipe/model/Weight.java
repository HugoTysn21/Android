package com.ynov.apprecipe.model;

import java.util.Date;

public class Weight {

    private int id_weight;
    private String date;
    private float value;


    public Weight(int id, String date, float value) {
        this.id_weight = id;
        this.date = date;
        this.value = value;
    }

    public int getId_weight() {
        return id_weight;
    }

    public void setId_weight(int id_weight) {
        this.id_weight = id_weight;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
