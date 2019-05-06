package com.ynov.apprecipe.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class StepRecipe implements Parcelable {

    // MARK: FIELDS
    private String step;
    private String order;

    public StepRecipe() {

    }

    public StepRecipe(String step, String order, ArrayList<StepRecipe> stepRecipeArrayList) {
        this.step = step;
        //this.stepRecipeArrayList = stepRecipeArrayList;
        this.order = order;
    }

    protected StepRecipe(Parcel in) {
        step = in.readString();
        order = in.readString();
    }

    public static final Creator<StepRecipe> CREATOR = new Creator<StepRecipe>() {
        @Override
        public StepRecipe createFromParcel(Parcel in) {
            return new StepRecipe(in);
        }

        @Override
        public StepRecipe[] newArray(int size) {
            return new StepRecipe[size];
        }
    };

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(step);
        dest.writeString(order);
    }
}
