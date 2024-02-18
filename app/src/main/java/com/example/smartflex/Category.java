package com.example.smartflex;


public class Category {
    //icon, name, cost, costtype
    int icon;
    String name;
    float cost;
    CostType costType;
    boolean showMenu;

    //constructor
    // Constructor
    public Category(int icon, String name, float cost, CostType costType, boolean showMenu) {
        this.icon = icon;
        this.name = name;
        this.cost = cost;
        this.costType = costType;
        this.showMenu = showMenu;
    }
}
