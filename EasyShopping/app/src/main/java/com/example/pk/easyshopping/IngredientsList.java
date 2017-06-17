package com.example.pk.easyshopping;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class IngredientsList extends AppCompatActivity {

    private TextView mTextView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_add_to_existing_list_from_ingredients:
                    Toast.makeText(IngredientsList.this, "Dodaj do istniejącej listy zakupów", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_create_new_list_from_ingredients:
                    Toast.makeText(IngredientsList.this, "Utwórz nową listę zakupów", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);
        Intent intentThatStartedThisActivity = getIntent();

        mTextView = (TextView) findViewById(R.id.tv_ingredients_list);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_ingredients_list);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().setGroupCheckable(0, false, true);

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            ArrayList<RecipeIngredientData> ingredientsList;
            ingredientsList = intentThatStartedThisActivity.getParcelableArrayListExtra(Intent.EXTRA_TEXT);
            StringBuilder list = new StringBuilder();
            for (int i = 0; i < ingredientsList.size(); i++){
                list.append(ingredientsList.get(i).ingredientName);
                if(i < ingredientsList.size() - 1)
                    list.append("\n");

            }
            mTextView.setText(list.toString());
        }
    }
}
