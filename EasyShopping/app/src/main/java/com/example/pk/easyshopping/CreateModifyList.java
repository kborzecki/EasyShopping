package com.example.pk.easyshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ciezczak.mateusz.easyshopping.ShoppingList;
import com.ciezczak.mateusz.easyshopping.ShoppingListItem;

import java.util.ArrayList;
import java.util.Collections;

public class CreateModifyList extends AppCompatActivity {
    private TextView mTextView;
    ShoppingList shoppingList;
    ArrayList<ShoppingListItem> productsList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_modify_list);

        mTextView = (TextView) findViewById(R.id.textView2);
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra("INGREDIENTS")) {
            productsList = intentThatStartedThisActivity.getParcelableArrayListExtra("INGREDIENTS");

            StringBuilder string = new StringBuilder();
            for (int i = 0; i < productsList.size(); i++){
                string
                        .append(productsList.get(i).name)
                        .append("\n");
            }

            mTextView.setText(string);


        } else if (intentThatStartedThisActivity.hasExtra("SHOPPING_LIST")) {
            shoppingList = intentThatStartedThisActivity.getParcelableExtra("SHOPPING_LIST");


        }
    }
}
