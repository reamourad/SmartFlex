package com.example.smartflex;

import static com.example.smartflex.Database.amountWants;
import static com.example.smartflex.Database.income;
import static com.example.smartflex.Database.needsCategory;
import static com.example.smartflex.Database.newCategory;
import static com.example.smartflex.Database.percentageWants;
import static com.example.smartflex.Database.remainingWants;
import static com.example.smartflex.Database.wantsCategory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreateWants extends AppCompatActivity {
    CategoryRecyclerAdapter adapter;
    private static final int REQUEST_CODE_DELETE_ITEM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wants);

    }

    @Override
    protected void onResume(){
        super.onResume();
        if(newCategory != null){
            wantsCategory.add(newCategory);
            newCategory = null;
        }
        adapter = new CategoryRecyclerAdapter(getApplicationContext(), wantsCategory);

        //get the amount of money we have in wants
        amountWants = percentageWants / 100.0f * income;
        remainingWants = amountWants;

        //get and set the remaining money
        for (Category want : wantsCategory) {
            remainingWants -= want.cost;
        }
        remainingWants = (int) (remainingWants * 100.0f) / 100.0f;
        TextView remainingMoneyTextView = findViewById(R.id.remainingMoney);
        remainingMoneyTextView.setText(String.format("%.2f$", remainingWants));

        //cant confirm if the remaining money is lesser than 0
        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(v -> {
            if (remainingWants >= 0) {
                startActivity(new Intent(CreateWants.this, CreateSavings.class));
            }
        });

        //add the recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        // Swipe action only left
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                wantsCategory.remove(position);
                adapter.notifyItemRemoved(position);

                // Call onCreate again
                recreate();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //add category button
        LinearLayout btn = findViewById(R.id.addCategoryButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateWants.this, AddCategoryPage.class));
            }
        });

        //Go back to the last page
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateWants.this, CreateNeeds.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DELETE_ITEM && resultCode == RESULT_OK) {
            // Call onCreate again if an item is deleted
            recreate();
        }
    }
}