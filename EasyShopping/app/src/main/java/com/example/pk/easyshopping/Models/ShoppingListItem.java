package com.example.pk.easyshopping.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class ShoppingListItem implements Parcelable{
    public String name;
    public boolean isChecked = false;

    public ShoppingListItem()
    {
        this.name = "TEST";
    }

    public ShoppingListItem(String n)
    {
        this.name = n;
    }

    private ShoppingListItem(Parcel in){
        this.name = in.readString();
        this.isChecked = Boolean.parseBoolean(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if(isChecked){
            dest.writeString("true");
        } else {
            dest.writeString("false");
        }
    }

    public static final Parcelable.Creator<ShoppingListItem> CREATOR = new Parcelable.Creator<ShoppingListItem>() {
        public ShoppingListItem createFromParcel(Parcel in) {
            return new ShoppingListItem(in);
        }

        public ShoppingListItem[] newArray(int size) {
            return new ShoppingListItem[size];
        }
    };
}
