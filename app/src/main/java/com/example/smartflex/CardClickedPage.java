package com.example.smartflex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static com.example.smartflex.Database.getCategoryById;

import java.util.Objects;

public class CardClickedPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_clicked_page);

        Button done = findViewById(R.id.doneButton);
        done.setOnClickListener(v->{
            startActivity(new Intent(CardClickedPage.this, MainActivity.class));
        });

        // Get the category ID from intent extras
        String categoryId = getIntent().getStringExtra("categoryId");
        Category category = getCategoryById(categoryId);

        // Implement this method to retrieve the Category object

        TextView costTextView = findViewById(R.id.costTextView);
        costTextView.setText(category.moneySpent + "/" + category.cost);

        EditText input = findViewById(R.id.input);
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Get the value entered by the user
                    String inputText = input.getText().toString();
                    if (!inputText.isEmpty()) {
                        // Parse the input value to float
                        float inputValue = Float.parseFloat(inputText);
                        // Update the moneySpent field
                        category.moneySpent += inputValue;
                        // Update the TextView to reflect the new value
                        costTextView.setText(category.moneySpent + "/" + category.cost);
                        // Clear the input field
                        input.setText("");
                        return true; // Consume the event
                    }
                }
                return false; // Let the system handle the event
            }
        });
    }
}