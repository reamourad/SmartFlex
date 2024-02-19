package com.example.smartflex;

import java.util.ArrayList;
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

}
