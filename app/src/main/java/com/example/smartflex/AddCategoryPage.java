package com.example.smartflex;

import static com.example.smartflex.Database.needsCategory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AddCategoryPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_page);

        //Go back to the last page
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Click on confirm button
        Button confirmBtn = findViewById(R.id.confirmButton);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set up the new needs
                TextView categoryNameInput = findViewById(R.id.categoryName);
                String categoryName = categoryNameInput.getText().toString();
                EditText costInput = findViewById(R.id.budgetCost);
                float cost = Integer.parseInt(costInput.getText().toString());
                RadioGroup radioGroup = findViewById(R.id.radioGroup);
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                CostType costType = null;

                if (selectedRadioButtonId == R.id.fixedOption) {
                    costType = CostType.FIXED;
                } else if (selectedRadioButtonId == R.id.variableOption) {
                    costType = CostType.VARIABLE;
                }

                Category need = new Category(R.drawable.car, categoryName, cost, costType, false);
                needsCategory.add(need);


                //go back to needs
                startActivity(new Intent(AddCategoryPage.this, CreateNeeds.class));

            }
        });
    }
}