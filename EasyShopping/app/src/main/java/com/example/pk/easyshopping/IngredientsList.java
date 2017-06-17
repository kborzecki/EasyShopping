package com.example.pk.easyshopping;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class IngredientsList extends AppCompatActivity {

    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private IngredientsAdapter mIngredientsAdapter;
    private List<RecipeIngredientData> data = Collections.emptyList();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_create_new_list_from_ingredients:
                    data = mIngredientsAdapter.getIngredientsList();
                    int counter = 0;
                    StringBuilder selectedIngredients = new StringBuilder();

                    for (int i = 0; i < data.size(); i++) {
                        RecipeIngredientData ingredient = data.get(i);
                        if (ingredient.isSelected) {
                            counter++;
                            selectedIngredients
                                    .append(ingredient.ingredientName)
                                    .append("\n");
                        }
                    }
                    if (counter > 0) {
                        Toast.makeText(IngredientsList.this,
                                "Wybrane produkty: \n" + selectedIngredients, Toast.LENGTH_LONG)
                                .show();
                    } else {
                        Toast.makeText(IngredientsList.this, "Nie wybrano żadnych produktów",
                                Toast.LENGTH_LONG).show();
                    }
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


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_ingredients_list);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().setGroupCheckable(0, false, false);

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            data = intentThatStartedThisActivity.getParcelableArrayListExtra(Intent.EXTRA_TEXT);

            mRecyclerView = (RecyclerView) findViewById(R.id.rv_ingredients);

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(IngredientsList.this, LinearLayoutManager.VERTICAL, false);

            mRecyclerView.setLayoutManager(layoutManager);

            mIngredientsAdapter = new IngredientsAdapter(IngredientsList.this, data, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                }
            });
            mRecyclerView.setAdapter(mIngredientsAdapter);

           // mTextView.setText(list.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.action_shopping_lists)
        {
            Context context = IngredientsList.this;
            Intent myIntent = new Intent(context, ShoppingLists.class);
            context.startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
