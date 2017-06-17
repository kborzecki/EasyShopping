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

public class IngredientsList extends AppCompatActivity {

    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private IngredientsAdapter mIngredientsAdapter;

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


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_ingredients_list);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().setGroupCheckable(0, false, false);
        navigation.getMenu().getItem(1).setChecked(true);

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            final ArrayList<RecipeIngredientData> data;
            data = intentThatStartedThisActivity.getParcelableArrayListExtra(Intent.EXTRA_TEXT);
            /*StringBuilder list = new StringBuilder();
            for (int i = 0; i < ingredientsList.size(); i++){
                list.append(ingredientsList.get(i).ingredientName);
                if(i < ingredientsList.size() - 1)
                    list.append("\n");

            }*/
            //ToDo: fix weird layout behaviour (activity_ingredients_list.xml, ingredients_list_item.xml)
            mRecyclerView = (RecyclerView) findViewById(R.id.rv_ingredients);

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(IngredientsList.this, LinearLayoutManager.VERTICAL, false);

            mRecyclerView.setLayoutManager(layoutManager);

            mIngredientsAdapter = new IngredientsAdapter(IngredientsList.this, data, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Toast.makeText(IngredientsList.this, data.get(position).ingredientName, Toast.LENGTH_LONG).show();
                    /*Intent intent = new Intent(IngredientsList.this, DetailedRecipe.class);
                    intent.putExtra(Intent.EXTRA_TEXT, data.get(position).recipeID);
                    startActivity(intent);*/
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
