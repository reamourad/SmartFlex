
package com.example.smartflex;
import com.google.firebase.auth.FirebaseAuth;
public class FirebaseSingelton {

    private static FirebaseSingelton instance;
    private FirebaseAuth mAuth;
    private FirebaseSingelton() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static synchronized FirebaseSingelton getInstance() {
        if (instance == null) {
            instance = new FirebaseSingelton();
        }
        return instance;
    }

    public FirebaseAuth getFirebaseAuth() {
        return mAuth;
    }



}
