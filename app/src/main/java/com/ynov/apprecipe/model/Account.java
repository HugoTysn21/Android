package com.ynov.apprecipe.model;

public class Account {

    private String date_of_birth;
    private String start_weight;
    private String height;
    private String gender;

    public Account(String date_of_birth, String start_weight, String height, String gender) {
        this.date_of_birth = date_of_birth;
        this.start_weight = start_weight;
        this.height = height;
        this.gender = gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getStart_weight() {
        return start_weight;
    }

    public void setStart_weight(String start_weight) {
        this.start_weight = start_weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
