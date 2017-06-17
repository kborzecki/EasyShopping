package com.example.pk.easyshopping;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecipesList extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;


    private RecyclerView mRecyclerView;

    private RecipesAdapter mRecipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRecipesData();
                swipeContainer.setRefreshing(false);
            }
        });



        loadRecipesData();
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
            Context context = RecipesList.this;
            Intent myIntent = new Intent(context, ShoppingLists.class);
            context.startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadRecipesData() {
        new FetchRecipesTask().execute();
    }

    public class FetchRecipesTask extends AsyncTask<String, Void, String> {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("http://bornt2.myqnapcloud.com:4567/recipes");
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

            final List<BasicRecipeData> data = new ArrayList<>();

            try {
                JSONArray jArray = new JSONArray(result);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    BasicRecipeData recipesData = new BasicRecipeData();
                    recipesData.recipeName = json_data.getString("name");
                    recipesData.recipeImageURL = json_data.getString("imageURL");
                    recipesData.recipeID = json_data.getString("id");
                    recipesData.recipeLiked = json_data.getInt("liked");
                    data.add(recipesData);
                }

                    mRecyclerView = (RecyclerView) findViewById(R.id.rv_recipes);

                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(RecipesList.this, LinearLayoutManager.VERTICAL, false);

                    mRecyclerView.setLayoutManager(layoutManager);

                    mRecipesAdapter = new RecipesAdapter(RecipesList.this, data, new CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            //Toast.makeText(RecipesList.this, data.get(position).recipeID, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RecipesList.this, DetailedRecipe.class);
                            intent.putExtra(Intent.EXTRA_TEXT, data.get(position).recipeID);
                            startActivity(intent);
                        }
                    });
                mRecyclerView.setAdapter(mRecipesAdapter);
                } catch (JSONException e) {
                Toast.makeText(RecipesList.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }

}
