package com.example.smartflex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountSettings extends AppCompatActivity {

    private  bottoms_icon BottomIconFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        FragmentManager fragmentManager = getSupportFragmentManager();
        BottomIconFragment = (bottoms_icon) fragmentManager.findFragmentById(R.id.fragmentContainerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BottomIconFragment.setColorAccountSettings();

        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSettings.this, SignUp.class));
            }
        });

        Button logInButton = findViewById(R.id.logInButton);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSettings.this, Login.class));
            }
        });
    }
}