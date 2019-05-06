package com.ynov.apprecipe.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NutritionRecipe implements Parcelable {

    private String calorie;
    private String protein;
    private String fat;
    private String carbohydrate;
    private String sugar;
    private String sat_fat;
    private String fiber;
    private String sodium;

    public NutritionRecipe() {
    }

    protected NutritionRecipe(Parcel in) {
        calorie = in.readString();
        protein = in.readString();
        fat = in.readString();
        carbohydrate = in.readString();
        sugar = in.readString();
        sat_fat = in.readString();
        fiber = in.readString();
        sodium = in.readString();
    }

    public static final Creator<NutritionRecipe> CREATOR = new Creator<NutritionRecipe>() {
        @Override
        public NutritionRecipe createFromParcel(Parcel in) {
            return new NutritionRecipe(in);
        }

        @Override
        public NutritionRecipe[] newArray(int size) {
            return new NutritionRecipe[size];
        }
    };

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(String carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getSat_fat() {
        return sat_fat;
    }

    public void setSat_fat(String sat_fat) {
        this.sat_fat = sat_fat;
    }

    public String getFiber() {
        return fiber;
    }

    public void setFiber(String fiber) {
        this.fiber = fiber;
    }

    public String getSodium() {
        return sodium;
    }

    public void setSodium(String sodium) {
        this.sodium = sodium;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(calorie);
        dest.writeString(protein);
        dest.writeString(fat);
        dest.writeString(carbohydrate);
        dest.writeString(sugar);
        dest.writeString(sat_fat);
        dest.writeString(fiber);
        dest.writeString(sodium);
    }
}
