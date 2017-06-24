package com.ciezczak.mateusz.easyshopping;

import java.util.ArrayList;

public class ShoppingList{
    private String ListName;
    private int NumberOfAllElements;
    private int NumberOfBoughtElements;
    private ArrayList<ShoppingListItem> shoppingListItems = new ArrayList<>();

    //TODO: add timestamps for sorting

    public ShoppingList(String listName, ArrayList<ShoppingListItem> shoppingListItems)
    {
        this.shoppingListItems = new ArrayList<>();
        this.shoppingListItems.addAll(shoppingListItems);
        this.ListName = listName;
        this.NumberOfBoughtElements = 0;
        this.NumberOfAllElements = shoppingListItems.size();
    }

    public ShoppingList(String listName)
    {
        this.ListName = listName;
        this.NumberOfBoughtElements = 0;
        this.NumberOfAllElements = 0;

    }

    public ShoppingListItem get(int index){
        if(index < 0 || index >= this.shoppingListItems.size())
            throw new IndexOutOfBoundsException();
        else
            return this.shoppingListItems.get(index);
    }

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

    private void updateNumberOfBoughtElements() {
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

    public void IncreaseBoughtValue()
    {
        NumberOfBoughtElements++;
    }



    public ShoppingList(){}

}
