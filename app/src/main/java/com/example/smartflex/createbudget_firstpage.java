package com.example.smartflex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class createbudget_firstpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createbudget_firstpage);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner);
        Spinner dropdown2 = findViewById(R.id.spinner2);
        Spinner dropdown3 = findViewById(R.id.spinner3);
        //create a list of items for the spinner.
        String[] items = new String[]{"","10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown2.setAdapter(adapter);
        dropdown3.setAdapter(adapter);
    }
}