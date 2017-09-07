package com.example.pk.easyshopping.Models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

public class ShoppingList implements Comparable<ShoppingList> {
    private String ListName;
    private int NumberOfAllElements;
    private int NumberOfBoughtElements;
    private ArrayList<ShoppingListItem> shoppingListItems = new ArrayList<>();
    private Date dateTime;

    public ShoppingList(String listName, ArrayList<ShoppingListItem> shoppingListItems)
    {
        this.shoppingListItems = new ArrayList<>();
        this.shoppingListItems.addAll(shoppingListItems);
        this.ListName = listName;
        updateNumberOfBoughtElements();
        this.NumberOfAllElements = shoppingListItems.size();
        dateTime = new Date();
    }

    public ShoppingList(String listName)
    {
        this.ListName = listName;
        this.NumberOfBoughtElements = 0;
        this.NumberOfAllElements = 0;
        dateTime = new Date();
    }

    public ShoppingListItem get(int index){
        if(index < 0 || index >= this.shoppingListItems.size())
            throw new IndexOutOfBoundsException();
        else
            return this.shoppingListItems.get(index);
    }

    private Date getDateTime() {
        return dateTime;
    }


    public int size(){ return this.shoppingListItems.size(); }

    public boolean add(ShoppingListItem o) {
        this.shoppingListItems.add(o);
        this.NumberOfAllElements = shoppingListItems.size();
        return true;
    }

    public ArrayList<ShoppingListItem> getShoppingListItemsList(){
        return this.shoppingListItems;
    }

    public boolean ToggleIsBought(int position) {
        if(position < this.NumberOfAllElements) {
            this.shoppingListItems.get(position).isChecked = !this.shoppingListItems.get(position).isChecked;
            this.updateNumberOfBoughtElements();
            return true;
        } else {
            return false;
        }
    }

    public void updateNumberOfBoughtElements() {
        this.NumberOfBoughtElements = 0;
        for(int i = 0; i < NumberOfAllElements; i++){
            if(this.shoppingListItems.get(i).isChecked )this.NumberOfBoughtElements++;
        }
    }

    public String getListName() {
        return ListName;
    }

    public int getNumberOfAllElements() {
        return NumberOfAllElements;
    }

    public int getNumberOfBoughtElements() {
        return NumberOfBoughtElements;
    }


    public ShoppingList() {
        dateTime = new Date();
    }

    @Override
    public int compareTo(@NonNull ShoppingList o) {
        return getDateTime().compareTo(o.getDateTime());
    }
}
