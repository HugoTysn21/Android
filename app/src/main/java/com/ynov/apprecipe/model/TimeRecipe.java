package com.ynov.apprecipe.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class TimeRecipe implements Parcelable {

    private String total;
    private String baking;
    private String prep;


    public TimeRecipe() {
    }


    protected TimeRecipe(Parcel in) {
        total = in.readString();
        baking = in.readString();
        prep = in.readString();
    }

    public static final Creator<TimeRecipe> CREATOR = new Creator<TimeRecipe>() {
        @Override
        public TimeRecipe createFromParcel(Parcel in) {
            return new TimeRecipe(in);
        }

        @Override
        public TimeRecipe[] newArray(int size) {
            return new TimeRecipe[size];
        }
    };

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBaking() {
        return baking;
    }

    public void setBaking(String baking) {
        this.baking = baking;
    }

    public String getPrep() {
        return prep;
    }

    public void setPrep(String prep) {
        this.prep = prep;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(total);
        dest.writeString(baking);
        dest.writeString(prep);
    }
}
