package com.dunzo.assignment.CoffeeMachine.core;

import java.util.List;

public class OutletResponse {

    //Represents the list of ingredients which are insufficient/unavailable, in order to prepare a beverage
    private List<String> unAvailableIngredients;

    public OutletResponse(List<String> unAvailableIngredients) {
        this.unAvailableIngredients = unAvailableIngredients;
    }

    public List<String> getUnAvailableIngredients() {
        return unAvailableIngredients;
    }

    public void setUnAvailableIngredients(List<String> unAvailableIngredients) {
        this.unAvailableIngredients = unAvailableIngredients;
    }
}
