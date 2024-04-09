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
    static boolean isFirstLaunch = true;
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
        userData.put("income", income);
        userData.put("balance", balance);
        userData.put("incomeFrequency", incomeFrequency);
        userData.put("budgetFrequency", budgetFrequency);

        // Create a HashMap to store categories
        HashMap<String, Object> categoriesData = new HashMap<>();
        // Store needs categories
        for (Category category : needsCategory) {
            // Add category with type "needs"
            HashMap<String, Object> categoryData = new HashMap<>();
            categoryData.put("icon", category.icon);
            categoryData.put("name", category.name);
            categoryData.put("cost", category.cost);
            categoryData.put("costType", category.costType);
            categoryData.put("showMenu", category.showMenu);
            categoryData.put("categoryType", "needs"); // Add category type
            categoriesData.put(category.id, categoryData);
        }

// Store wants categories
        for (Category category : wantsCategory) {
            // Add category with type "wants"
            HashMap<String, Object> categoryData = new HashMap<>();
            categoryData.put("icon", category.icon);
            categoryData.put("name", category.name);
            categoryData.put("cost", category.cost);
            categoryData.put("costType", category.costType);
            categoryData.put("showMenu", category.showMenu);
            categoryData.put("categoryType", "wants"); // Add category type
            categoriesData.put(category.id, categoryData);
        }

// Store savings categories
        for (Category category : savingsCategory) {
            // Add category with type "savings"
            HashMap<String, Object> categoryData = new HashMap<>();
            categoryData.put("icon", category.icon);
            categoryData.put("name", category.name);
            categoryData.put("cost", category.cost);
            categoryData.put("costType", category.costType);
            categoryData.put("showMenu", category.showMenu);
            categoryData.put("categoryType", "savings"); // Add category type
            categoriesData.put(category.id, categoryData);
        }

        userData.put("categories", categoriesData);

        userRef.setValue(userData);
    }

    // Function to fetch user data from Realtime Database
    static public void fetchUserDataFromRealtimeDatabase(String userId, final OnDataFetchedListener listener) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/" + userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    HashMap<String, Object> userData = (HashMap<String, Object>) dataSnapshot.getValue();
                    percentageNeeds = (int) (long) userData.get("needsPercentage");
                    amountNeeds = Float.parseFloat(userData.get("amountNeeds").toString());
                    remainingNeeds = Float.parseFloat(userData.get("remainingNeeds").toString());
                    percentageWants = (int) (long) userData.get("percentageWants");
                    amountWants = Float.parseFloat(userData.get("amountWants").toString());
                    remainingWants = Float.parseFloat(userData.get("remainingWants").toString());
                    percentageSavings = (int) (long) userData.get("percentageSaving");
                    amountSavings = Float.parseFloat(userData.get("amountSavings").toString());
                    remainingSavings = Float.parseFloat(userData.get("remainingSavings").toString());
                    income = (int) (long) userData.get("income");
                    balance = Float.parseFloat(userData.get("balance").toString());

                    // Retrieve and update categories
                    Map<String, Object> categoriesData = (Map<String, Object>) userData.get("categories");
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
                            needsCategory.clear();
                            wantsCategory.clear();
                            savingsCategory.clear();

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
                    listener.onDataFetched();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

}

