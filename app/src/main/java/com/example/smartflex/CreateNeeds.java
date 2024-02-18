package com.example.smartflex;

import static com.example.smartflex.Database.amountNeeds;
import static com.example.smartflex.Database.income;
import static com.example.smartflex.Database.needsCategory;
import static com.example.smartflex.Database.percentageNeeds;
import static com.example.smartflex.Database.remainingNeeds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreateNeeds extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_needs);

        //get the amount of money we have in needs
        amountNeeds = percentageNeeds/100.0f * income;
        remainingNeeds = amountNeeds;

        //get and set the remaining money
        for(Category need : needsCategory){
            remainingNeeds -= need.cost;
        }
        remainingNeeds = (int) (remainingNeeds * 100.0f) / 100.0f;
        TextView remainingMoneyTextView = findViewById(R.id.remainingMoney);
        remainingMoneyTextView.setText(String.format("%.2f$", remainingNeeds));
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        //add the recycler view
        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(getApplicationContext(), needsCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        //add category button
        LinearLayout btn = findViewById(R.id.addCategoryButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateNeeds.this, AddCategoryPage.class));
            }
        });

        //Go back to the last page
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}