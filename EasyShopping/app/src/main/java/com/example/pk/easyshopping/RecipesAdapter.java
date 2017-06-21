package com.example.pk.easyshopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lagger on 2017-06-16.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> implements Filterable{

    private Context context;
    List<BasicRecipeData> data = Collections.emptyList();
    List<BasicRecipeData> filtered = Collections.emptyList();

    CustomItemClickListener listener;


    public RecipesAdapter(Context context, List<BasicRecipeData> data, CustomItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.filtered = data;
        this.listener = listener;
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

        public ViewHolder(View itemView){
            super(itemView);
            textRecipeName = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            ivRecipeImage = (ImageView) itemView.findViewById(R.id.iv_recipe_image);
        }

    }


}
