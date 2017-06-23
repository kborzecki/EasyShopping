package com.example.pk.easyshopping;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ciezczak.mateusz.easyshopping.ShoppingList;
import com.ciezczak.mateusz.easyshopping.ShoppingListItem;

import java.util.ArrayList;

public class CreateModifyList extends AppCompatActivity {

    private EditText mListName;
    private EditText mItemName;
    private RecyclerView mRecyclerView;
    private ProductsAdapter mProductsAdapter;
    private ImageButton mButtonAddItem;
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

        mListName = (EditText) findViewById(R.id.et_list_name);
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra("INGREDIENTS")) {
            if(intentThatStartedThisActivity.hasExtra("RECIPE_NAME")){
                mListName.setText(intentThatStartedThisActivity.getStringExtra("RECIPE_NAME"));
            }
            productsList = intentThatStartedThisActivity.getParcelableArrayListExtra("INGREDIENTS");

            Log.i("Produkt 1:", productsList.get(0).name);




        } else if (intentThatStartedThisActivity.hasExtra("SHOPPING_LIST")) {
            shoppingList = intentThatStartedThisActivity.getParcelableExtra("SHOPPING_LIST");


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

        //TODO: fix bug with screen "scrolling" when pressing enter key on keyboard
        //TODO: add onKeyListener for mItemName - enter -> same action as mButtonAddItem on click

        mButtonAddItem.setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View v){
                if(!mItemName.getText().toString().equals("")) {
                    mProductsAdapter.prepend(mItemName.getText().toString());
                    mItemName.setText("");
                    mRecyclerView.scrollToPosition(0);
                }
            }
        });

    }
}
