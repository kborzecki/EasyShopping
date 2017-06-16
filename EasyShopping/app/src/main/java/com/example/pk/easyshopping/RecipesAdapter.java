package com.example.pk.easyshopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

/**
 * Created by Lagger on 2017-06-16.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.MyHolder> {

    private Context context;
    List<BasicRecipeData> data = Collections.emptyList();
    CustomItemClickListener listener;


    public RecipesAdapter(Context context, List<BasicRecipeData> data, CustomItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipes_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        final MyHolder holder = new MyHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getLayoutPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
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

    class MyHolder extends RecyclerView.ViewHolder {

        TextView textRecipeName;
        ImageView ivRecipeImage;

        public MyHolder(View itemView){
            super(itemView);
            textRecipeName = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            ivRecipeImage = (ImageView) itemView.findViewById(R.id.iv_recipe_image);
        }

    }


}
