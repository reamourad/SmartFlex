package com.example.smartflex;


public class Category {
    //icon, name, cost, costtype
    int icon;
    String name;
    float cost;
    CostType costType;
    float moneySpent;
    boolean showMenu;

    //constructor
    // Constructor
    public Category(int icon, String name, float cost, CostType costType, boolean showMenu) {
        this.icon = icon;
        this.name = name;
        this.cost = cost;
        this.costType = costType;
        if(costType == CostType.FIXED){
            moneySpent = cost;
        }
        else{
            moneySpent = 0;
        }
        this.showMenu = showMenu;
    }
}
