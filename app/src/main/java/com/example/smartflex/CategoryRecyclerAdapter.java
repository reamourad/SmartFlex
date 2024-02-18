package com.example.smartflex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Category> list;
    Context context;

    //constructor
    public CategoryRecyclerAdapter(Context context, List<Category> articlesList) {
        this.list = articlesList;
        this.context = context;
    }

    //responsible for inflating the item layout and creating the corresponding ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recycler_list, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Category entity = list.get(position);
        if(holder instanceof CategoryViewHolder){
            ((CategoryViewHolder)holder).title.setText(entity.name);
            ((CategoryViewHolder) holder).imageView.setImageDrawable(ContextCompat.getDrawable(context, entity.icon));
            ((CategoryViewHolder) holder).costType.setText(entity.costType.toString());
            ((CategoryViewHolder) holder).cost.setText("$" + Float.toString(entity.cost));

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
        TextView costType;

        TextView cost;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageView);
            container = itemView.findViewById(R.id.container);
            costType = itemView.findViewById(R.id.costType);
            cost = itemView.findViewById(R.id.cost);
        }
    }
}
