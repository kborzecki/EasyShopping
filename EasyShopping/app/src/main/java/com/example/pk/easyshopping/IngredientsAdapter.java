package com.example.pk.easyshopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
    public void onBindViewHolder(IngredientsAdapter.MyHolder holder, final int position) {
        RecipeIngredientData current = data.get(position);
        StringBuilder ingredientString = new StringBuilder();
        ingredientString
                .append(current.ingredientName)
                .append(" -- ")
                .append(current.ingredientQuantity)
                .append(" ")
                .append(current.ingredientQuantityDisplay);
        holder.tvIngredient.setText(ingredientString);

       holder.cbIsSelected.setChecked(data.get(position).isSelected);

        holder.cbIsSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                //RecipeIngredientData ingredient = (RecipeIngredientData) cb.getTag();
                data.get(position).isSelected = cb.isChecked();

                //ingredient.isSelected = cb.isChecked();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView tvIngredient;
        CheckBox cbIsSelected;


        public MyHolder(View itemView){
            super(itemView);
            tvIngredient = (TextView) itemView.findViewById(R.id.tv_ingredient);
            cbIsSelected = (CheckBox) itemView.findViewById(R.id.cb_ingredient_selected);
        }

    }
    public List<RecipeIngredientData> getIngredientsList() {
        Log.e("Produkt 1: ", data.get(0).isSelected ? "true" : "false");
        return data;
    }

}
