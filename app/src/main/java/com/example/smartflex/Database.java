package com.example.smartflex;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        userData.put("income", income);
        userData.put("balance", balance);
        userData.put("incomeFrequency", incomeFrequency);
        userData.put("budgetFrequency", budgetFrequency);

        // Create a HashMap to store categories
        HashMap<String, Object> categoriesData = new HashMap<>();
        // Store needs categories
        for (Category category : needsCategory) {
            categoriesData.put(category.id, category);
        }
        // Store wants categories
        for (Category category : wantsCategory) {
            categoriesData.put(category.id, category);
        }
        // Store savings categories
        for (Category category : savingsCategory) {
            categoriesData.put(category.id, category);
        }

        userData.put("categories", categoriesData);

        userRef.setValue(userData);
    }

    // Function to fetch user data from Realtime Database
    static public void fetchUserDataFromRealtimeDatabase(String userId) {
        // Get a reference to the user's Realtime Database location
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/" + userId);

        // Listen for changes in the user's data
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if data exists for the user
                if (dataSnapshot.exists()) {
                    // Retrieve user data
                    Map<String, Object> userDataMap = (Map<String, Object>) dataSnapshot.getValue();
                    if (userDataMap != null) {
                        // Update local variables with user data
                        percentageNeeds = userDataMap.get("needsPercentage") != null ? (int) (long) userDataMap.get("needsPercentage") : 0;
                        amountNeeds = userDataMap.get("amountNeeds") != null ? Float.parseFloat(userDataMap.get("amountNeeds").toString()) : 0;
                        remainingNeeds = userDataMap.get("remainingNeeds") != null ? Float.parseFloat(userDataMap.get("remainingNeeds").toString()) : 0;
                        percentageWants = userDataMap.get("percentageWants") != null ? (int) (long) userDataMap.get("percentageWants") : 0;
                        amountWants = userDataMap.get("amountWants") != null ? Float.parseFloat(userDataMap.get("amountWants").toString()) : 0;
                        remainingWants = userDataMap.get("remainingWants") != null ? Float.parseFloat(userDataMap.get("remainingWants").toString()) : 0;
                        percentageSavings = userDataMap.get("percentageSaving") != null ? (int) (long) userDataMap.get("percentageSaving") : 0;
                        amountSavings = userDataMap.get("amountSavings") != null ? Float.parseFloat(userDataMap.get("amountSavings").toString()) : 0;
                        remainingSavings = userDataMap.get("remainingSavings") != null ? Float.parseFloat(userDataMap.get("remainingSavings").toString()) : 0;
                        income = userDataMap.get("income") != null ? (int) (long) userDataMap.get("income") : 0;
                        balance = userDataMap.get("balance") != null ? Float.parseFloat(userDataMap.get("balance").toString()) : 0;

                        // Convert incomeFrequency to Frequency enum
                        if (userDataMap.get("incomeFrequency") != null) {
                            try {
                                incomeFrequency = Frequency.valueOf(userDataMap.get("incomeFrequency").toString());
                            } catch (IllegalArgumentException e) {
                                incomeFrequency = Frequency.WEEKLY; // Default value if conversion fails
                            }
                        } else {
                            incomeFrequency = Frequency.WEEKLY; // Default value if data is null
                        }

                        // Convert budgetFrequency to Frequency enum
                        if (userDataMap.get("budgetFrequency") != null) {
                            try {
                                budgetFrequency = Frequency.valueOf(userDataMap.get("budgetFrequency").toString());
                            } catch (IllegalArgumentException e) {
                                budgetFrequency = Frequency.WEEKLY; // Default value if conversion fails
                            }
                        } else {
                            budgetFrequency = Frequency.WEEKLY; // Default value if data is null
                        }

                        // Retrieve and update categories
                        Map<String, Object> categoriesData = (Map<String, Object>) userDataMap.get("categories");
                        if (categoriesData != null) {
                            for (Map.Entry<String, Object> entry : categoriesData.entrySet()) {
                                // Parse category data and add to appropriate list
                                Map<String, Object> categoryMap = (Map<String, Object>) entry.getValue();
                                String name = categoryMap.get("name").toString();
                                int icon = Integer.parseInt(categoryMap.get("icon").toString());
                                float cost = Float.parseFloat(categoryMap.get("cost").toString());
                                CostType costType = CostType.valueOf(categoryMap.get("costType").toString());
                                boolean showMenu = (boolean) categoryMap.get("showMenu");
                                Category category = new Category(icon, name, cost, costType, showMenu);

                                // Determine which list to add the category to based on its type
                                String categoryType = categoryMap.get("categoryType").toString();
                                switch (categoryType) {
                                    case "needs":
                                        needsCategory.add(category);
                                        break;
                                    case "wants":
                                        wantsCategory.add(category);
                                        break;
                                    case "savings":
                                        savingsCategory.add(category);
                                        break;
                                    default:
                                        // Handle unknown category type
                                        break;
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}
