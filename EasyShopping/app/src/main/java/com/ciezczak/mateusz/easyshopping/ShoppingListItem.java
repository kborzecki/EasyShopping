package com.ciezczak.mateusz.easyshopping;


public class ShoppingListItem {
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getQuantityType() {
        return quantityType;
    }

    private String name;
    private String quantity;
    private String quantityType;

    public void ToggleIsBought()
    {
        isChecked = !isChecked;
    }

    public ShoppingListItem()
    {
        this.name = "TEST";
        this.quantity = "2";
        this.quantityType = "litry";
    }

    public ShoppingListItem(String n, String q, String qT)
    {
        this.name = n;
        this.quantity = q;
        this.quantityType = qT;
    }
}
