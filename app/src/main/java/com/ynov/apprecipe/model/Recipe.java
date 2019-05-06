package com.ynov.apprecipe.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.UUID;

public class Recipe extends ArrayList<Parcelable> implements Parcelable {

    // MARK: FIELDS

    private String id;
    private String title;
    private String portions;
    private String picture_url;
    private ArrayList<StepRecipe> steps;
    private ArrayList<IngredientRecipe> ingredientRecipes;
    private ArrayList<TimeRecipe> timeRecipes;
    private ArrayList<NutritionRecipe> nutritionRecipes;


    public Recipe() {
        id = UUID.randomUUID().toString();
        steps = new ArrayList<>();
        ingredientRecipes = new ArrayList<>();
        nutritionRecipes = new ArrayList<>();
        timeRecipes = new ArrayList<>();
    }

    protected Recipe(Parcel in) {
        id = UUID.randomUUID().toString();
        title = in.readString();
        portions = in.readString();
        picture_url = in.readString();
        /*ingredientRecipes = in.createTypedArrayList(IngredientRecipe.CREATOR);*/

    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public ArrayList<TimeRecipe> getTimeRecipes() {
        return timeRecipes;
    }

    public void setTimeRecipes(ArrayList<TimeRecipe> timeRecipes) {
        this.timeRecipes = timeRecipes;
    }

    public ArrayList<NutritionRecipe> getNutritionRecipes() {
        return nutritionRecipes;
    }

    public void setNutritionRecipes(ArrayList<NutritionRecipe> nutritionRecipes) {
        this.nutritionRecipes = nutritionRecipes;
    }

    public ArrayList<IngredientRecipe> getIngredientRecipes() {
        return ingredientRecipes;
    }

    public void setIngredientRecipes(ArrayList<IngredientRecipe> ingredientRecipes) {
        this.ingredientRecipes = ingredientRecipes;
    }

    public ArrayList<StepRecipe> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepRecipe> steps) {
        this.steps = steps;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPortions() {
        return portions;
    }

    public void setPortions(String portions) {
        this.portions = portions;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(portions);
        dest.writeString(picture_url);
    }

}
