package com.example.pk.easyshopping;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class DetailedRecipe extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mTVRecipeName;
    private TextView mTVRecipePrepTime;
    private TextView mTVRecipeDifficulty;
    private TextView mTVIngredientsList;
    private TextView mTVRecipeSteps;
    private DetailedRecipeData recipeData = new DetailedRecipeData();

   private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_create_list_from_ingredients:
                    Context context = DetailedRecipe.this;
                    Intent myIntent = new Intent(context, IngredientsList.class);
                    myIntent.putExtra("INGREDIENTS_LIST", recipeData.recipeIngredients);
                    myIntent.putExtra("RECIPE_NAME", recipeData.recipeName);
                    context.startActivity(myIntent);
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_recipe);

        Intent intentThatStartedThisActivity = getIntent();

        mImageView = (ImageView) findViewById(R.id.iv_detailed_recipe_image);
        mTVRecipeName = (TextView) findViewById(R.id.tv_detailed_recipe_name);
        mTVRecipePrepTime = (TextView) findViewById(R.id.tv_detailed_recipe_prep_time);
        mTVRecipeDifficulty = (TextView) findViewById(R.id.tv_detailed_recipe_difficulty);
        mTVIngredientsList = (TextView) findViewById(R.id.tv_detailed_recipe_ingredients_list);
        mTVRecipeSteps = (TextView) findViewById(R.id.tv_detailed_recipe_steps);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_detailed_recipe);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().setGroupCheckable(0, false, true);

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            String recipeID = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            loadDetailedData(recipeID);
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
            Context context = DetailedRecipe.this;
            Intent myIntent = new Intent(context, ShoppingLists.class);
            context.startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadDetailedData(String id) {
        new FetchRecipesTask().execute(id);
    }

    public class FetchRecipesTask extends AsyncTask<String, Void, String> {
        URL url = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                String urlString = "http://bornt2.myqnapcloud.com:4567/recipes" + "?id=" + params[0];
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = urlConnection.getInputStream();

                    Scanner scanner = new Scanner(in);
                    scanner.useDelimiter("\\A");

                    boolean hasInput = scanner.hasNext();
                    if (hasInput) {
                        return scanner.next();
                    } else {
                        return null;
                    }
                } finally {
                    urlConnection.disconnect();
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "wystąpił błąd";
            }
        }


        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json_data = new JSONObject(result);



                recipeData.recipeName = json_data.getString("name");
                recipeData.recipeCategory = json_data.getString("category");
                recipeData.recipeSubCategory = json_data.getString("sub_category");
                recipeData.recipeDifficulty = json_data.getString("difficulty");
                recipeData.recipeImageURL = json_data.getString("imageURL");
                recipeData.recipePrepTime = json_data.getString("prep_time");
                recipeData.recipeLiked = json_data.getInt("liked");

                JSONArray stepsArr = json_data.getJSONArray("steps");
                //ArrayList<String> steps = new ArrayList<>();
                recipeData.recipeSteps = new ArrayList<>();
                for(int i = 0; i < stepsArr.length(); i++){
                    recipeData.recipeSteps.add(stepsArr.getString(i));
                }

                JSONArray ingredientsArr = json_data.getJSONArray("ingredients");
                //ArrayList<RecipeIngredientData> ingredients = new ArrayList<>(ingredientsArr.length());
                recipeData.recipeIngredients = new ArrayList<>();
                for(int i = 0; i < ingredientsArr.length(); i++) {
                    RecipeIngredientData ingredient = new RecipeIngredientData();
                    JSONObject json_ingredient = ingredientsArr.getJSONObject(i);
                    ingredient.ingredientName = json_ingredient.getString("name");
                    ingredient.ingredientQuantity = json_ingredient.getString("quantity");
                    ingredient.ingredientQuantityDisplay = json_ingredient.getString("quantity_display");
                    ingredient.ingredientQuantityType = json_ingredient.getString("quantity_type");
                    ingredient.ingredientType = json_ingredient.getString("type");
                    recipeData.recipeIngredients.add(ingredient);
                }





                mTVRecipeName.setText(recipeData.recipeName);
                Glide.with(DetailedRecipe.this).load(recipeData.recipeImageURL)
                        .placeholder(R.mipmap.ic_error_recipe)
                        .error(R.mipmap.ic_error_recipe)
                        .into(mImageView);
                mTVRecipeDifficulty.setText("Trudność: " + recipeData.recipeDifficulty);
                mTVRecipePrepTime.setText("Czas przygotowania: " + recipeData.recipePrepTime);

                StringBuilder ingredientsList = new StringBuilder();
                for(int i = 0; i < recipeData.recipeIngredients.size(); i++) {
                    ingredientsList
                            .append(recipeData.recipeIngredients.get(i).ingredientName)
                            .append(" -- ")
                            .append(recipeData.recipeIngredients.get(i).ingredientQuantity)
                            .append(" ")
                            .append(recipeData.recipeIngredients.get(i).ingredientQuantityDisplay);
                    if(i < recipeData.recipeIngredients.size() - 1)
                        ingredientsList.append("\n");
                }
                mTVIngredientsList.setText(ingredientsList.toString());

                StringBuilder stepsList = new StringBuilder();
                for(int i = 0; i < recipeData.recipeSteps.size(); i++) {
                    stepsList
                            .append(i+1 + ". ")
                            .append(recipeData.recipeSteps.get(i))
                            .append(".");
                    if(i < recipeData.recipeSteps.size() - 1)
                        stepsList.append("\n\n");
                }
                mTVRecipeSteps.setText(stepsList.toString());



            } catch (JSONException e) {
                Toast.makeText(DetailedRecipe.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
