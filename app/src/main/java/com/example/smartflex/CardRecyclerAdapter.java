package com.example.smartflex;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Category entity = list.get(position);
        if(holder instanceof CardViewHolder){
            ((CardViewHolder)holder).title.setText(entity.name);
            ((CardViewHolder) holder).imageView.setImageDrawable(ContextCompat.getDrawable(context, entity.icon));
            ((CardViewHolder) holder).cost.setText("$" + Float.toString(entity.moneySpent));
            // Check if cost exceeds available money
            if (entity.cost < entity.moneySpent) {
                ((CardViewHolder)holder).cost.setTextColor(Color.RED);
            } else {
                ((CardViewHolder)holder).cost.setTextColor(Color.BLACK);
            }
            ((CardViewHolder)holder).button.setOnClickListener(v->{
                // Get the context from the button's view hierarchy
                Context context = v.getContext();
                // Start the activity using the context retrieved
                Intent intent = new Intent(context, CardClickedPage.class);
                intent.putExtra("categoryId", entity.id);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        LinearLayout container;
        TextView cost;
        Button button;

        public CardViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.icon);
            container = itemView.findViewById(R.id.container);
            cost = itemView.findViewById(R.id.cost);
            button = itemView.findViewById(R.id.button);
        }
    }
}
