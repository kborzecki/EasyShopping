package com.example.pk.easyshopping;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ciezczak.mateusz.easyshopping.ShoppingList;

import java.util.ArrayList;

/**
 * Created by Lagger on 2017-06-24.
 */

public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListsAdapter.ViewHolder> {

    private Context context;
    ArrayList<ShoppingList> data = new ArrayList<>();

    CustomItemClickListener listener;


    public ShoppingListsAdapter(Context context, ArrayList<ShoppingList> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.shopping_list_item;
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
        ShoppingList current = data.get(position);
        holder.tvShoppingListName.setText(current.getListName());

        StringBuilder itemCount = new StringBuilder();
        itemCount.append(current.getNumberOfBoughtElements())
                .append("/")
                .append(current.getNumberOfAllElements());

        holder.tvShoppingListItemCount.setText(itemCount.toString());

        listener = new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(context, ListDetails.class);
                intent.putExtra("SHOPPING_LIST_NAME", data.get(position).getListName());
                context.startActivity(intent);
            }
        };

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvShoppingListName;
        TextView tvShoppingListItemCount;

        public ViewHolder(View itemView){
            super(itemView);
            tvShoppingListName = (TextView) itemView.findViewById(R.id.tv_shopping_list_name);
            tvShoppingListItemCount = (TextView) itemView.findViewById(R.id.tv_shopping_list_item_count);
        }

    }


}
