package com.example.pk.easyshopping.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pk.easyshopping.Adapters.ShoppingListsAdapter;
import com.example.pk.easyshopping.Models.ShoppingList;
import com.example.pk.easyshopping.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class ShoppingLists extends AppCompatActivity {
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    private ArrayList<ShoppingList> shoppingListsData = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ShoppingListsAdapter mShoppingListsAdapter;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_create_list:
                    Context context = ShoppingLists.this;
                    Intent myIntent = new Intent(context, CreateModifyList.class);
                    context.startActivity(myIntent);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().setGroupCheckable(0, false, true);

        mPrefs = getSharedPreferences(getString(R.string.shared_preferences), MODE_PRIVATE);
        prefsEditor = mPrefs.edit();
        prefsEditor.apply();

        getListsFromSharedPreferences();


        initViews();
        mShoppingListsAdapter = new ShoppingListsAdapter(ShoppingLists.this, shoppingListsData);
        mRecyclerView.setAdapter(mShoppingListsAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getListsFromSharedPreferences();
        mShoppingListsAdapter = new ShoppingListsAdapter(ShoppingLists.this, shoppingListsData);
        mRecyclerView.setAdapter(mShoppingListsAdapter);
    }

    private void initViews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_shopping_lists);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(ShoppingLists.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void getListsFromSharedPreferences(){
        shoppingListsData = new ArrayList<>();
        Map<String, ?> shoppingListsMap = mPrefs.getAll();
        if(shoppingListsMap.size() > 0){
            Gson gson = new Gson();
            for (Object o : shoppingListsMap.values()) {
                String temp = o.toString();
                shoppingListsData.add(gson.fromJson(temp, ShoppingList.class));

            }
        }
        Collections.sort(shoppingListsData);
        Collections.reverse(shoppingListsData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_lists_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.action_recipes)
        {
            Context context = ShoppingLists.this;
            Intent myIntent = new Intent(context, RecipesList.class);
            context.startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
