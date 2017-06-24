package com.example.pk.easyshopping;

import com.example.pk.easyshopping.Models.ShoppingList;
import com.example.pk.easyshopping.Models.ShoppingListItem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShoppingListTest {

    @Test
    public void ShouldAddAndGetElementFromList() throws Exception {
        ShoppingList shoppingList = new ShoppingList("Lista1");
        ShoppingListItem shoppingListItemExcepted = new ShoppingListItem();
        shoppingList.add(shoppingListItemExcepted);

        assertEquals(shoppingListItemExcepted, shoppingList.get(0));
        assertEquals(1, shoppingList.getNumberOfAllElements());
    }


}