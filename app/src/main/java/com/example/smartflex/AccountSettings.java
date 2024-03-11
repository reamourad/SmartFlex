package com.example.smartflex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

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
    }
}