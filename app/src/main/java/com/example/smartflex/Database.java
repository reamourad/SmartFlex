package com.example.smartflex;

import java.util.ArrayList;
import java.util.List;

public class Database {
    static int percentageNeeds = 20;
    static float amountNeeds = 0;
    static float remainingNeeds = 0;
    static int percentageWants = 0;
    static int percentageSavings = 0;

    static int income = 1000;
    static Frequency incomeFrequency = Frequency.WEEKLY;
    static Frequency budgetFrequency = Frequency.WEEKLY;

    static List<Category> needsCategory = new ArrayList<>();

}
