package com.example.smartflex;

import static com.example.smartflex.Database.isFirstLaunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoadingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if(user != null){
            Database.fetchUserDataFromRealtimeDatabase(user.getUid(), new OnDataFetchedListener() {
                @Override
                public void onDataFetched() {
                    startActivity(new Intent(LoadingPage.this, MainActivity.class));
                }

                @Override
                public void onError(String errorMessage) {

                }
            });
        }
        else{
            startActivity(new Intent(LoadingPage.this, MainActivity.class));
        }

    }
}