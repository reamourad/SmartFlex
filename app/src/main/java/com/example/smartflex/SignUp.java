package com.example.smartflex;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.example.smartflex.Database.transferGuestDataToRealtimeDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
    FirebaseSingelton firebaseSingelton;
    ProgressBar progressBar;
    TextView textView;

    private PasswordStrengthIndicator passwordStrengthIndicator;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = firebaseSingelton.getFirebaseAuth().getCurrentUser();
//        if (currentUser != null) {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseSingelton = FirebaseSingelton.getInstance();
        editTextEmail = findViewById(R.id.Email);
        editTextPassword = findViewById(R.id.Password);
        buttonSignUp = findViewById(R.id.signUpButton);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);

        //passwordStrengthIndicator = new PasswordStrengthIndicator();
        //passwordStrengthIndicator.addObserver((PasswordStrengthObserver) this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }

        });
//        editTextPassword.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String password = charSequence.toString();
//
//                //notify the observer about the change
//                passwordStrengthIndicator.notifyObservers((checkPasswordSrength(password)));
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//
//        });


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());


                //check if the email is empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUp.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    return;

                }
                //check if the password is empty
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUp.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //assign user
                firebaseSingelton.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignUp.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    transferGuestDataToRealtimeDatabase(task.getResult().getUser().getUid());
                                    Intent intent = new Intent(SignUp.this, MainActivity.class);
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

    private String checkPasswordSrength(String password) {

        if (password.length() < 6) {
            return "Week";

        } else if (password.length() < 10) {
            return "Medium";

        }else {
            return "Strong";

        }

    }
//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//        passwordStrengthIndicator.removeObserver((PasswordStrengthObserver) this);
//    }

}