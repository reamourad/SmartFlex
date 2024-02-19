package com.example.smartflex;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Category> list;
    Context context;

    //constructor
    public CardRecyclerAdapter(Context context, List<Category> articlesList) {
        this.list = articlesList;
        this.context = context;
    }

    //responsible for inflating the item layout and creating the corresponding ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_recycler, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Category entity = list.get(position);
        if(holder instanceof CategoryViewHolder){
            ((CategoryViewHolder)holder).title.setText(entity.name);
            ((CategoryViewHolder) holder).imageView.setImageDrawable(ContextCompat.getDrawable(context, entity.icon));
            ((CategoryViewHolder) holder).cost.setText("$" + Float.toString(entity.moneySpent));
            // Check if cost exceeds available money
            if (entity.cost > entity.moneySpent) {
                ((CategoryViewHolder)holder).cost.setTextColor(Color.RED);
            } else {
                ((CategoryViewHolder)holder).cost.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        LinearLayout container;
        TextView cost;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.icon);
            container = itemView.findViewById(R.id.container);
            cost = itemView.findViewById(R.id.cost);
        }
    }
}
