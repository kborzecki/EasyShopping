package com.ciezczak.mateusz.easyshopping;

import java.util.ArrayList;

public class ShoppingList extends ArrayList<ShoppingListItem> {
    private String ListName;
    private int NumberOfAllElements;
    private int NumberOfBoughtElements;

    @Override
    public boolean add(ShoppingListItem o) {
        NumberOfAllElements++;
        return super.add(o);
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

    public ShoppingList(String listName, ArrayList<ShoppingListItem> shoppingListItems)
    {
        this.addAll(shoppingListItems);
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

}
