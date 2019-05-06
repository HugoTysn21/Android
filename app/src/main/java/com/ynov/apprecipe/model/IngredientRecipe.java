package com.ynov.apprecipe.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class IngredientRecipe implements Parcelable {

    private String quantity;
    private String unit;
    private String name;
   /* public ArrayList<IngredientRecipe> ingredientRecipes;*/

    public IngredientRecipe() {

    }

    /*public ArrayList<IngredientRecipe> getIngredientRecipes() {
        return ingredientRecipes;
    }

    public void setIngredientRecipes(ArrayList<IngredientRecipe> ingredientRecipes) {
        this.ingredientRecipes = ingredientRecipes;
    }*/

    protected IngredientRecipe(Parcel in) {
        quantity = in.readString();
        unit = in.readString();
        name = in.readString();
    }

    public static final Creator<IngredientRecipe> CREATOR = new Creator<IngredientRecipe>() {
        @Override
        public IngredientRecipe createFromParcel(Parcel in) {
            return new IngredientRecipe(in);
        }

        @Override
        public IngredientRecipe[] newArray(int size) {
            return new IngredientRecipe[size];
        }
    };

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(unit);
        dest.writeString(name);
    }
}
