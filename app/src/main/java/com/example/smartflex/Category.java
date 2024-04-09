package com.example.smartflex;
import java.io.Serializable;
import java.util.UUID;

public class Category implements Serializable{
    //icon, name, cost, costtype
    private static final long serialVersionUID = 1L;
    public String id;
    public int icon;
    public String name;
    public float cost;
    public CostType costType;
    public float moneySpent;
    public boolean showMenu;


    //constructor
    // Constructor
    public Category(int icon, String name, float cost, CostType costType, boolean showMenu) {
        this.id = UUID.randomUUID().toString();
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
