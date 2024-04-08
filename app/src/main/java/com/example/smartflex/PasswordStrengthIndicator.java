package com.example.smartflex;

import java.util.ArrayList;
import java.util.List;

public class PasswordStrengthIndicator {

    private List<PasswordStrengthObserver> observers =  new ArrayList<>();

    public void addObserver(PasswordStrengthObserver observer){

        observers.add(observer);
    }
    public void removeObserver(PasswordStrengthObserver observer){

        observers.remove(observer);
    }

    public void notifyObservers(String passwordStrength){

        for (PasswordStrengthObserver observer : observers){
            observer.updatePasswordStrength(passwordStrength);
        }
    }

}
