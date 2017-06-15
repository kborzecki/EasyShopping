package com.example.pk.easyshopping;

import com.ciezczak.mateusz.easyshopping.ShoppingList;
import com.ciezczak.mateusz.easyshopping.ShoppingListItem;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void ShouldAddAndGetElementFromList() throws Exception {
        ShoppingList shoppingList = new ShoppingList("Lista1");
        ShoppingListItem shoppingListItemExcepted = new ShoppingListItem();
        shoppingList.add(shoppingListItemExcepted);

        assertEquals(shoppingListItemExcepted, shoppingList.get(0));
        assertEquals(1, shoppingList.getNumberOfAllElements());
    }
}