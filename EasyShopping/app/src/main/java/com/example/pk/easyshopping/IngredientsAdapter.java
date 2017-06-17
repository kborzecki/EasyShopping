package com.example.pk.easyshopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Lagger on 2017-06-17.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyHolder>{

    private Context context;
    List<RecipeIngredientData> data = Collections.emptyList();
    CustomItemClickListener listener;


    public IngredientsAdapter(Context context, List<RecipeIngredientData> data, CustomItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public IngredientsAdapter.MyHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingredients_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        final IngredientsAdapter.MyHolder holder = new IngredientsAdapter.MyHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getLayoutPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.MyHolder holder, int position) {
        RecipeIngredientData current = data.get(position);
        StringBuilder ingredientString = new StringBuilder();
        ingredientString
                .append(current.ingredientName)
                .append(" -- ")
                .append(current.ingredientQuantity)
                .append(" ")
                .append(current.ingredientQuantityDisplay);
        holder.tvIngredient.setText(ingredientString);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView tvIngredient;


        public MyHolder(View itemView){
            super(itemView);
            tvIngredient = (TextView) itemView.findViewById(R.id.tv_ingredient);
        }

    }

}
