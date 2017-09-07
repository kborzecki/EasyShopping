package com.example.pk.easyshopping.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.pk.easyshopping.Models.CustomItemClickListener;
import com.example.pk.easyshopping.Models.ShoppingList;
import com.example.pk.easyshopping.Models.ShoppingListItem;
import com.example.pk.easyshopping.R;

/**
 * Created by Lagger on 2017-06-24.
 */

public class DetailedShoppingListAdapter extends RecyclerView.Adapter<DetailedShoppingListAdapter.CustomViewHolder> {

    private Context context;
    private ShoppingList data;
    private CustomItemClickListener listener;


    public DetailedShoppingListAdapter(Context context, ShoppingList data, CustomItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.detailed_shopping_list_item_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        final CustomViewHolder holder = new CustomViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getLayoutPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        ShoppingListItem current = data.get(position);

        holder.tvShoppingListItem.setText(current.name);

        holder.cbIsBought.setChecked(data.get(position).isChecked);

        holder.cbIsBought.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                //RecipeIngredientData ingredient = (RecipeIngredientData) cb.getTag();
                data.get(position).isChecked = cb.isChecked();
                data.updateNumberOfBoughtElements();
                //ingredient.isSelected = cb.isChecked();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView tvShoppingListItem;
        CheckBox cbIsBought;


        CustomViewHolder(View itemView) {
            super(itemView);
            tvShoppingListItem = (TextView) itemView.findViewById(R.id.tv_detailed_shopping_list_item_name);
            cbIsBought = (CheckBox) itemView.findViewById(R.id.cb_detailed_shopping_list_item_bought);
        }

    }
    public ShoppingList getShoppingList() {
        return data;
    }

}
