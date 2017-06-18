package com.example.pk.easyshopping;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ciezczak.mateusz.easyshopping.ShoppingList;
import com.ciezczak.mateusz.easyshopping.ShoppingListItem;

import java.util.ArrayList;
import java.util.Collections;

public class CreateModifyList extends AppCompatActivity {
    //private TextView mTextView;
    ShoppingList shoppingList;
    ArrayList<ShoppingListItem> productsList = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_save:
                    Toast.makeText(CreateModifyList.this, "Zapisz zmiany", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.action_cancel:
                    Toast.makeText(CreateModifyList.this, "Anuluj", Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_modify_list);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_create_modify_list);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().setGroupCheckable(0, false, true);

       // mTextView = (TextView) findViewById(R.id.textView2);
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra("INGREDIENTS")) {
            productsList = intentThatStartedThisActivity.getParcelableArrayListExtra("INGREDIENTS");

            StringBuilder string = new StringBuilder();
            for (int i = 0; i < productsList.size(); i++){
                string
                        .append(productsList.get(i).name)
                        .append("\n");
            }

            //mTextView.setText(string);


        } else if (intentThatStartedThisActivity.hasExtra("SHOPPING_LIST")) {
            shoppingList = intentThatStartedThisActivity.getParcelableExtra("SHOPPING_LIST");


        }
    }
}
