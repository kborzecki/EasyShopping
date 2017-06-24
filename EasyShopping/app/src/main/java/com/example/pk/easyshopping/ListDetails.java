package com.example.pk.easyshopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ciezczak.mateusz.easyshopping.ShoppingList;
import com.google.gson.Gson;

public class ListDetails extends AppCompatActivity {
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;

    private RecyclerView mRecyclerView;
    private DetailedShoppingListAdapter mDetailedShoppingListAdapter;

    private TextView mDetailedShoppingListName;
    private String shoppingListName = null;
    private ShoppingList shoppingList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_edit:
                    saveListToSharedPreferences();
                    Context context = ListDetails.this;
                    Intent myIntent = new Intent(context, CreateModifyList.class);
                    myIntent.putExtra("SHOPPING_LIST_NAME", shoppingListName);
                    context.startActivity(myIntent);

                    return true;
                case R.id.action_delete:
                    prefsEditor.remove(shoppingListName);
                    prefsEditor.commit();

                    Context context2 = ListDetails.this;
                    Intent myIntent2 = new Intent(context2, ShoppingLists.class);
                    context2.startActivity(myIntent2);
                    finish();
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);

        mPrefs = getSharedPreferences(getString(R.string.shared_preferences), MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        Intent intentThatStartedThisActivity = getIntent();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_detailed_shopping_list);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().setGroupCheckable(0, false, true);

        initViews();

        if(intentThatStartedThisActivity.hasExtra("SHOPPING_LIST_NAME")){
            shoppingListName = intentThatStartedThisActivity.getStringExtra("SHOPPING_LIST_NAME");
            getListFromSharedPreferences();

            mDetailedShoppingListName.setText(shoppingList.getListName());

        }

        mDetailedShoppingListAdapter = new DetailedShoppingListAdapter(ListDetails.this, shoppingList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
            }
        });
        mRecyclerView.setAdapter(mDetailedShoppingListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListFromSharedPreferences();
        mDetailedShoppingListAdapter = new DetailedShoppingListAdapter(ListDetails.this, shoppingList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
            }
        });
        mRecyclerView.setAdapter(mDetailedShoppingListAdapter);
    }
    private void initViews(){
        mDetailedShoppingListName = (TextView) findViewById(R.id.tv_detailed_shopping_list_name);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_detailed_shopping_list);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(ListDetails.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void getListFromSharedPreferences(){
        if(mPrefs.contains(shoppingListName)){
            Gson gson = new Gson();
            shoppingList = gson.fromJson(mPrefs.getString(shoppingListName, ""), ShoppingList.class);
        } else {
            shoppingList = new ShoppingList("Wystąpił błąd");
        }
    }

    public void onBackPressed()
    {
        saveListToSharedPreferences();

        Context context = ListDetails.this;
        Intent myIntent = new Intent(context, ShoppingLists.class);
        context.startActivity(myIntent);
        finish();
    }

    public void saveListToSharedPreferences(){
        shoppingList = mDetailedShoppingListAdapter.getShoppingList();
        prefsEditor.remove(shoppingList.getListName());
        prefsEditor.commit();
        Gson gson = new Gson();
        String json = gson.toJson(shoppingList);
        prefsEditor.putString(shoppingList.getListName(), json);
        prefsEditor.commit();
    }
}
