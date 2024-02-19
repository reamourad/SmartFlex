package com.example.smartflex;

import static com.example.smartflex.Database.needsCategory;
import static com.example.smartflex.Database.newCategory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AddCategoryPage extends AppCompatActivity {

    private static final String ERROR_EMPTY_NAME = "Please enter a category name.";
    private static final String ERROR_INVALID_COST = "Please enter a valid budget amount (positive number).";
    private static final String ERROR_NO_OPTION_SELECTED = "Please select a cost type.";

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

        // Confirmation handling
        TextView confirmedTextView = findViewById(R.id.ConfirmedString);

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(v -> {
            TextView categoryNameInput = findViewById(R.id.categoryName);
            String categoryName = categoryNameInput.getText().toString();
            float cost = 0;
            CostType costType = null;

            // Clear any previous error messages
            confirmedTextView.setVisibility(View.GONE);

            // Category name validation
            if (categoryName.isEmpty()) {
                showError(ERROR_EMPTY_NAME);
                return;
            }

            // Budget cost validation
            EditText costInput = findViewById(R.id.budgetCost);
            try {
                String costString = costInput.getText().toString();
                cost = Float.parseFloat(costString);
                cost = Math.round(cost * 100.0f) / 100.0f;
                if (cost <= 0) {
                    throw new NumberFormatException(); // Handle non-positive cost
                }
            } catch (NumberFormatException e) {
                showError(ERROR_INVALID_COST);
                return;
            }

            // Cost type selection validation
            RadioGroup radioGroup = findViewById(R.id.radioGroup);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == R.id.fixedOption) {
                costType = CostType.FIXED;
            } else if (selectedId == R.id.variableOption) {
                costType = CostType.VARIABLE;
            } else {
                showError(ERROR_NO_OPTION_SELECTED);
                return;
            }

            // If no errors, create and add category
            newCategory = new Category(R.drawable.car, categoryName, cost, costType, false);
;
            finish();
        });
    }

    private void showError(String message) {
        TextView confirmedTextView = findViewById(R.id.ConfirmedString);
        confirmedTextView.setText(message);
        confirmedTextView.setVisibility(View.VISIBLE);
        confirmedTextView.setTextColor(Color.RED); // Use a visible error color
    }
}