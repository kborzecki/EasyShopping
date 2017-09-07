package com.example.pk.easyshopping.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pk.easyshopping.Activities.DetailedRecipe;
import com.example.pk.easyshopping.Models.BasicRecipeData;
import com.example.pk.easyshopping.Models.CustomItemClickListener;
import com.example.pk.easyshopping.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lagger on 2017-06-16.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> implements Filterable{

    private Context context;
    private List<BasicRecipeData> data = Collections.emptyList();
    private List<BasicRecipeData> filtered = Collections.emptyList();

    private CustomItemClickListener listener;


    public RecipesAdapter(Context context, List<BasicRecipeData> data) {
        this.context = context;
        this.data = data;
        this.filtered = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipes_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getLayoutPosition());
            }
        });
        return holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BasicRecipeData current = data.get(position);
        holder.textRecipeName.setText(current.recipeName);
        listener = new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(context, DetailedRecipe.class);
                intent.putExtra(Intent.EXTRA_TEXT, data.get(position).recipeID);
                context.startActivity(intent);
            }
        };
        Glide.with(context).load(current.recipeImageURL)
                .placeholder(R.mipmap.ic_error_recipe)
                .error(R.mipmap.ic_error_recipe)
                .into(holder.ivRecipeImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    data = filtered;
                } else {

                    ArrayList<BasicRecipeData> filteredList = new ArrayList<>();

                    for (BasicRecipeData recipe : filtered) {

                        if (recipe.recipeName.toLowerCase().contains(charString)) {

                            filteredList.add(recipe);
                        }
                    }

                    data = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<BasicRecipeData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textRecipeName;
        ImageView ivRecipeImage;

        ViewHolder(View itemView) {
            super(itemView);
            textRecipeName = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            ivRecipeImage = (ImageView) itemView.findViewById(R.id.iv_recipe_image);
        }

    }


}
