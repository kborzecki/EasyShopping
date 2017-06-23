//TODO: fix recyclerview bugs and create_modify_list layout
package com.example.pk.easyshopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ciezczak.mateusz.easyshopping.ShoppingList;
import com.ciezczak.mateusz.easyshopping.ShoppingListItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by Lagger on 2017-06-17.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyHolder>{

    private Context context;
    List<ShoppingListItem> data = Collections.emptyList();
    CustomItemClickListener listener;


    public ProductsAdapter(Context context, List<ShoppingListItem> data, CustomItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public ProductsAdapter.MyHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.shopping_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        final ProductsAdapter.MyHolder holder = new ProductsAdapter.MyHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getLayoutPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ProductsAdapter.MyHolder holder, final int position) {
        ShoppingListItem current = data.get(position);
        Log.i("Produkt", current.name);
        holder.tvShoppingListItem.setText(current.name);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView tvShoppingListItem;

        public MyHolder(View itemView){
            super(itemView);
            tvShoppingListItem = (TextView) itemView.findViewById(R.id.tv_shopping_list_item_name);
        }

    }
    public List<ShoppingListItem> getShoppingListItems() {
        return data;
    }

}
