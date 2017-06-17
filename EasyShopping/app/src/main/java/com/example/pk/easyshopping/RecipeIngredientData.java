package com.example.pk.easyshopping;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lagger on 2017-06-16.
 */

public class RecipeIngredientData implements Parcelable{
    public String ingredientQuantity;
    public String ingredientQuantityType;
    public String ingredientQuantityDisplay;
    public String ingredientName;
    public String ingredientType;
    public boolean isSelected = true;

    public RecipeIngredientData(){}

    private RecipeIngredientData(Parcel in) {
        ingredientQuantity = in.readString();
        ingredientQuantityType = in.readString();
        ingredientQuantityDisplay = in.readString();
        ingredientName = in.readString();
        ingredientType = in.readString();
        isSelected = Boolean.parseBoolean(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredientQuantity);
        dest.writeString(ingredientQuantityType);
        dest.writeString(ingredientQuantityDisplay);
        dest.writeString(ingredientName);
        dest.writeString(ingredientType);
        if(isSelected){
            dest.writeString("true");
        } else {
            dest.writeString("false");
        }
    }

    public static final Parcelable.Creator<RecipeIngredientData> CREATOR = new Parcelable.Creator<RecipeIngredientData>() {
        public RecipeIngredientData createFromParcel(Parcel in) {
            return new RecipeIngredientData(in);
        }

        public RecipeIngredientData[] newArray(int size) {
            return new RecipeIngredientData[size];
        }
    };
}
