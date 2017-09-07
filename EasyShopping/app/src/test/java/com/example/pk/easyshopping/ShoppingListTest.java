package com.example.pk.easyshopping;

import com.example.pk.easyshopping.Models.ShoppingList;
import com.example.pk.easyshopping.Models.ShoppingListItem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShoppingListTest {

    @Test
    public void ShouldAddAndGetElementFromShoppingList() throws Exception {
        ShoppingList shoppingList = new ShoppingList("Lista1");
        ShoppingListItem shoppingListItemExcepted = new ShoppingListItem(false);
        shoppingList.add(shoppingListItemExcepted);

        assertEquals(shoppingListItemExcepted, shoppingList.get(0));
        assertEquals(1, shoppingList.getNumberOfAllElements());
    }

    @Test
    public void ShouldUpdateNumberOfBoughtElements() throws Exception{
        ShoppingList shoppingList = new ShoppingList("Lista321");
        ShoppingListItem shoppingListItem = new ShoppingListItem(true);

        shoppingList.add(shoppingListItem);
        shoppingList.updateNumberOfBoughtElements();

        assertEquals(1, shoppingList.getNumberOfBoughtElements());
    }

    @Test
    public void ShouldSortListsByDate() throws Exception{
        ShoppingList shoppingList = new ShoppingList("Lista321");
        Thread.sleep(1000);
        ShoppingList shoppingList2 = new ShoppingList("Lista3221");
        assertEquals(-1, shoppingList.compareTo(shoppingList2));
    }




}