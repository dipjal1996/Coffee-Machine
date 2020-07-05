package com.dunzo.assignment.CoffeeMachine.core;

import java.util.Map;

public class CoffeeMachineInputParams {

    private int numOutlets;

    private Map<String, Double> ingredientsQuantity;

    private Map<String, Map<String, Double>> beverageInfo;

    public CoffeeMachineInputParams(int numOutlets, Map<String, Double> ingredientsQuantity,
                                    Map<String, Map<String, Double>> beverageInfo) {
        this.numOutlets = numOutlets;
        this.ingredientsQuantity = ingredientsQuantity;
        this.beverageInfo = beverageInfo;
    }

    public int getNumOutlets() {
        return numOutlets;
    }

    public void setNumOutlets(int numOutlets) {
        this.numOutlets = numOutlets;
    }

    public Map<String, Double> getIngredientsQuantity() {
        return ingredientsQuantity;
    }

    public void setIngredientsQuantity(Map<String, Double> ingredientsQuantity) {
        this.ingredientsQuantity = ingredientsQuantity;
    }

    public Map<String, Map<String, Double>> getBeverageInfo() {
        return beverageInfo;
    }

    public void setBeverageInfo(Map<String, Map<String, Double>> beverageInfo) {
        this.beverageInfo = beverageInfo;
    }
}
