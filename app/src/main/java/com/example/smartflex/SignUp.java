package com.example.smartflex;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {


        TextInputEditText editTextEmail, editTextPassword;
        Button buttonSignUp;
        FirebaseAuth mAuth;
        ProgressBar progressBar;
        TextView textView;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.Email);
        editTextPassword = findViewById(R.id.Password);
        buttonSignUp = findViewById(R.id.signUpButton);
        progressBar = findViewById(R.id.progressBar);
        textView =  findViewById(R.id.loginNow);

      textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent= new Intent(getApplicationContext(), Login.class);
              startActivity(intent);
              finish();

          }
      });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());


                //check if the email is empty
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignUp.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    return;

                }
                //check if the password is empty
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignUp.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //assign user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignUp.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(SignUp.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}