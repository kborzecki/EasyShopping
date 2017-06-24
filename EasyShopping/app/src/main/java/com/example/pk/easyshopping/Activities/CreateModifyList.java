package com.example.pk.easyshopping.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pk.easyshopping.Adapters.ProductsAdapter;
import com.example.pk.easyshopping.Models.CustomItemClickListener;
import com.example.pk.easyshopping.Models.ShoppingList;
import com.example.pk.easyshopping.Models.ShoppingListItem;
import com.example.pk.easyshopping.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CreateModifyList extends AppCompatActivity {
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;

    private EditText mListName;
    private EditText mItemName;
    private RecyclerView mRecyclerView;
    private ProductsAdapter mProductsAdapter;
    private ImageButton mButtonAddItem;
    private boolean modify = false;
    private String modifyShoppingListName = null;
    ShoppingList shoppingList;
    ArrayList<ShoppingListItem> productsListToSave;
    ArrayList<ShoppingListItem> productsList = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            =   new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_save:
                    productsListToSave = mProductsAdapter.getShoppingListItems();
                    if(productsListToSave.size() > 0) {
                        if(modify && !mListName.getText().toString().equals("")){
                            prefsEditor.remove(modifyShoppingListName);
                            prefsEditor.commit();
                        }
                        if(mListName.getText().toString().equals("")){
                            Toast.makeText(CreateModifyList.this, "Podaj nazwę listy!", Toast.LENGTH_SHORT).show();
                        } else if (mPrefs.contains(mListName.getText().toString())) {
                            Toast.makeText(CreateModifyList.this, "Lista o takiej nazwie już istnieje.", Toast.LENGTH_SHORT).show();
                        } else {
                            shoppingList = new ShoppingList(mListName.getText().toString(), productsListToSave);
                            shoppingList.updateNumberOfBoughtElements();

                            Gson gson = new Gson();
                            String json = gson.toJson(shoppingList);
                            prefsEditor.putString(shoppingList.getListName(), json);
                            prefsEditor.commit();

                            Context context = CreateModifyList.this;
                            Intent myIntent = new Intent(context, ShoppingLists.class);
                            context.startActivity(myIntent);

                            finish();
                        }
                    } else {
                        Toast.makeText(CreateModifyList.this, "Nie można utworzyć listy bez zawartości.", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                case R.id.action_cancel:
                    finish();
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = getSharedPreferences(getString(R.string.shared_preferences), MODE_PRIVATE);
        prefsEditor = mPrefs.edit();
        prefsEditor.apply();

        setContentView(R.layout.activity_create_modify_list);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_create_modify_list);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().setGroupCheckable(0, false, true);

        mListName = (EditText) findViewById(R.id.et_list_name);
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra("INGREDIENTS")) {
            if(intentThatStartedThisActivity.hasExtra("RECIPE_NAME")){
                mListName.setText(intentThatStartedThisActivity.getStringExtra("RECIPE_NAME"));
            }
            productsList = intentThatStartedThisActivity.getParcelableArrayListExtra("INGREDIENTS");
        } else if (intentThatStartedThisActivity.hasExtra("SHOPPING_LIST_NAME")) {
            if(mPrefs.contains(intentThatStartedThisActivity.getStringExtra("SHOPPING_LIST_NAME"))){
                Gson gson = new Gson();
                shoppingList = gson.fromJson(
                        mPrefs.getString(intentThatStartedThisActivity.getStringExtra("SHOPPING_LIST_NAME"), ""),
                        ShoppingList.class);
                productsList = shoppingList.getShoppingListItemsList();
                modifyShoppingListName = shoppingList.getListName();
                mListName.setText(modifyShoppingListName);
                modify = true;
            } else{
                productsList = new ArrayList<>();
                mListName.setText("Wystąpił błąd");
            }

        } else {
            productsList = new ArrayList<>();
        }

        mItemName = (EditText) findViewById(R.id.et_add_item);
        mButtonAddItem = (ImageButton) findViewById(R.id.ib_add_item);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_create_modify_list);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(CreateModifyList.this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mProductsAdapter = new ProductsAdapter(CreateModifyList.this, productsList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
            }
        });
        mRecyclerView.setAdapter(mProductsAdapter);

        mButtonAddItem.setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View v){
                addItemToIngredients();
            }
        });



        mItemName.setOnKeyListener(new ImageButton.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == 66 && !mItemName.getText().toString().equals("")) {
                    mProductsAdapter.prepend(mItemName.getText().toString());
                    mItemName.setText("");
                    mRecyclerView.smoothScrollToPosition(0);
                    return false;
                }
                if(keyCode == 4) {
                    onBackPressed();
                }
                return true;

            }
        });

    }

    private void addItemToIngredients() {
        if(!mItemName.getText().toString().equals("")) {
            mProductsAdapter.prepend(mItemName.getText().toString());
            mItemName.setText("");
            mRecyclerView.scrollToPosition(0);
        }
    }
}
