package com.example.smartflex;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.example.smartflex.Database.fetchUserDataFromRealtimeDatabase;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseSingelton firebaseSingelton;
    ProgressBar progressBar;
    TextView textView;
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseSingelton= FirebaseSingelton.getInstance();

        editTextEmail = findViewById(R.id.Email);
        editTextPassword = findViewById(R.id.Password);
        buttonLogin = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);
        textView =  findViewById(R.id.signUpNow);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());


                //check if the email is empty
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                //check if the password is empty
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseSingelton.getFirebaseAuth().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Login Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    fetchUserDataFromRealtimeDatabase(task.getResult().getUser().getUid());
                                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}