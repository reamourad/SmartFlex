package com.example.smartflex;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Database {
    static int percentageNeeds = 0;
    static float amountNeeds = 0;
    static float remainingNeeds = 0;
    static int percentageWants = 0;
    static float amountWants = 0;
    static float remainingWants = 0;
    static int percentageSavings = 0;
    static float amountSavings = 0;
    static float remainingSavings = 0;

    static int income = 0;
    static float balance = 0;
    static Frequency incomeFrequency = Frequency.WEEKLY;
    static Frequency budgetFrequency = Frequency.WEEKLY;

    static List<Category> needsCategory = new ArrayList<>();
    static List<Category> wantsCategory = new ArrayList<>();
    static List<Category> savingsCategory = new ArrayList<>();

    static Category newCategory = null;

    // Method to retrieve a Category object by its ID
    static public Category getCategoryById(String categoryId) {
        for (Category category : needsCategory) {
            if (Objects.equals(category.id, categoryId)) {
                return category; // Found the Category object with the matching ID
            }
        }
        for (Category category : wantsCategory) {
            if (Objects.equals(category.id, categoryId)) {
                return category; // Found the Category object with the matching ID
            }
        }
        for (Category category : savingsCategory) {
            if (Objects.equals(category.id, categoryId)) {
                return category; // Found the Category object with the matching ID
            }
        }
        return null; // Category with the given ID not found
    }

    static public void transferGuestDataToRealtimeDatabase(String userId) {
        // Get a reference to the user's Realtime Database location
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/" + userId);

        // Create a HashMap to store data for Realtime Database
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("needsPercentage", percentageNeeds);
        userData.put("amountNeeds", amountNeeds);
        userData.put("remainingNeeds", remainingNeeds);
        userData.put("percentageWants", percentageWants);
        userData.put("amountWants", amountWants);
        userData.put("remainingWants", remainingWants);
        userData.put("percentageSaving", percentageSavings);
        userData.put("amountSavings", amountSavings);
        userData.put("remainingSavings", remainingSavings);
        //userData.put("needsCategory", needsCategory);
        //userData.put("wantsCategory", wantsCategory);
        //userData.put("savingCategory", savingsCategory);

        // Write data to the user's Realtime Database location
        userRef.setValue(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data transfer successful, update UI or navigate to logged-in view
                        //Toast.makeText(, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle data transfer errors
                        //Toast.makeText(get(), "Data Transfer Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
