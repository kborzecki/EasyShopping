package com.example.pk.easyshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailedRecipe extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_recipe);

        mTextView = (TextView) findViewById(R.id.tv_detailed_recipe);

        Intent intentThatStartedThisActivity = getIntent();


        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            String recipeID = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            mTextView.setText(recipeID);
        }
    }
}
