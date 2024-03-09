package com.example.smartflex;

import static com.example.smartflex.Database.budgetFrequency;
import static com.example.smartflex.Database.income;
import static com.example.smartflex.Database.incomeFrequency;
import static com.example.smartflex.Database.percentageNeeds;
import static com.example.smartflex.Database.percentageWants;
import static com.example.smartflex.Database.percentageSavings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class createbudget_firstpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createbudget_firstpage);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner);
        Spinner dropdown2 = findViewById(R.id.spinner2);
        Spinner dropdown3 = findViewById(R.id.spinner3);
        Spinner dropdown4 = findViewById(R.id.frequencyIncome);
        Spinner dropdown5 = findViewById(R.id.spinner5);
        //create a list of items for the spinner.
        String[] items = new String[]{"","0%", "10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"};
        String[] items2 = new String[]{"", "Weekly", "Monthly", "Yearly"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, items);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_item, items2);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown2.setAdapter(adapter);
        dropdown3.setAdapter(adapter);
        dropdown4.setAdapter(adapter2);
        dropdown5.setAdapter(adapter2);

        //Go back to the last page
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(createbudget_firstpage.this, MainActivity.class));;
            }
        });
        Button confirmButton = (Button)findViewById(R.id.button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView ConfirmedString = findViewById(R.id.ConfirmedString);
                //check number 1
                checkTemplate(dropdown, dropdown2, dropdown3, ConfirmedString);

                //check number 2
                checkIncome(dropdown4, ConfirmedString);

                //check number 3
                checkBudgetLength(dropdown5, ConfirmedString);
                ConfirmedString.setVisibility(View.VISIBLE);
                //temp fix to not go through a bool
                if(ConfirmedString.getText() == "Confirmed"){
                    //go to next activity
                    startActivity(new Intent(createbudget_firstpage.this, CreateNeeds.class));
//                    Intent intent = new Intent();


                }
            }
        });

    }

    private void checkBudgetLength(Spinner dropdown5, TextView ConfirmedString) {
        String selectedBudgetFrequency = dropdown5.getSelectedItem().toString();
        if(selectedBudgetFrequency.isEmpty()){
            ConfirmedString.setText("Please select a budget length");
            return;
        }
        budgetFrequency = convertStringToFrequency(selectedBudgetFrequency);
    }

    // Helper method to convert percentage string to int
    private int convertPercentageToInt(String percentage) {
        // Remove "%" sign and parse to int
        return Integer.parseInt(percentage.replace("%", "").trim());
    }

    private void checkTemplate(Spinner dropdown, Spinner dropdown2, Spinner dropdown3, TextView ConfirmedString){
        String selectedPercentageNeeds = dropdown.getSelectedItem().toString();
        String selectedPercentageWants = dropdown2.getSelectedItem().toString();
        String selectedPercentageSavings = dropdown3.getSelectedItem().toString();

        // Check if no item is selected (empty string)
        if (selectedPercentageNeeds.isEmpty() || selectedPercentageWants.isEmpty() || selectedPercentageSavings.isEmpty()) {
            // Handle the case where no item is selected
            ConfirmedString.setText("Please select a % for each category");
            return; // Exit the onClick
        }

        //Convert dropdown string value to %
        percentageNeeds = convertPercentageToInt(selectedPercentageNeeds);
        percentageWants = convertPercentageToInt(selectedPercentageWants);
        percentageSavings = convertPercentageToInt(selectedPercentageSavings);

        //Check if they add up to 100%
        if(percentageNeeds + percentageWants + percentageSavings == 100){
            //Handle the case where they add up to 100%
            ConfirmedString.setText("Confirmed");
        }
        else{
            //Handle the case where it does not add up to 100%
            ConfirmedString.setText("The % needs to add up to 100%");
        }
    }

    private Frequency convertStringToFrequency(String frequency) {
        switch (frequency.toUpperCase()) {
            case "WEEKLY":
                return Frequency.WEEKLY;
            case "MONTHLY":
                return Frequency.MONTHLY;
            case "YEARLY":
                return Frequency.YEARLY;
            default:
                throw new IllegalArgumentException("Invalid frequency: " + frequency);
        }
    }

    private int convertFrequencyToNumber(Frequency frequency) {
        switch (frequency) {
            case WEEKLY:
                return 7;
            case MONTHLY:
                return 30;
            case YEARLY:
                return 365;
            default:
                throw new IllegalArgumentException("Invalid frequency: " + frequency);
        }
    }

    private void checkIncome(Spinner dropdown4, TextView ConfirmedString){
        //get and check income
        EditText incomeInput = findViewById(R.id.incomeInput);
        String incomeInputString = incomeInput.getText().toString();

        //handle the case where the income is empty
        if(incomeInputString.isEmpty()){
            ConfirmedString.setText("Please enter your income");
            return;
        }

        //check frequency
        String selectedFrequency = dropdown4.getSelectedItem().toString();
        //handle the case where frequency is empty
        if(selectedFrequency.isEmpty()){
            ConfirmedString.setText("Please enter the frequency of your income");
            return;
        }

        int IF = convertFrequencyToNumber(incomeFrequency);
        int bf = convertFrequencyToNumber(budgetFrequency);

        income = Integer.parseInt(incomeInputString)/IF
                *bf;
        incomeFrequency = convertStringToFrequency(selectedFrequency);

    }



}