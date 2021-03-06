package com.dunzo.assignment.CoffeeMachine.core;

import com.dunzo.assignment.CoffeeMachine.exception.CoffeeMachineException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.dunzo.assignment.CoffeeMachine.constant.Constants.TIME_TO_PREPARE_BEVERAGE;
import static com.dunzo.assignment.CoffeeMachine.exception.ErrorEnum.OUTLET_CURRENTLY_UNAVAILABLE;

/**
 * Outlet class is an observer. It has the subject {@link CoffeeMachine}.
 * It gets the prepare beverage request from CoffeeMachine.
 * If it is unavailable at the requestTime, then it throws {@link CoffeeMachineException} error.
 * If all the required ingredients to prepare the beverage are sufficient in quantities, then it will prepare the beverage, otherwise not.
 */
public class Outlet {

    //coffee machine (subject) to which this outlet (observer) is attached to
    private CoffeeMachine coffeeMachine;

    //lastUsed denotes the most recent time, when this outlet has been used, intitially it is -1 denoting that it has been not used.
    private Integer lastUsed;

    public Outlet(CoffeeMachine coffeeMachine) {
        this.coffeeMachine = coffeeMachine;
        this.coffeeMachine.attach(this);
        this.lastUsed = -1;
    }

    /**
     * Checks if the outlet is available, if not => throws {@link CoffeeMachineException} that the outlet is unavailable at the moment.
     * Checks if any of the required ingredient to prepare the beverage is insufficient => then ingredient is not prepared.
     * If all ingredients are sufficient enough to prepare the beverage => then updated the lastUsed, prepares the beverage, and updates the quantity of the used ingredients.
     * @param beverage
     * @param requestTime
     * @return the list of ingredients which are not sufficient enough to prepare the beverage.
     * @throws CoffeeMachineException
     */
    public OutletResponse prepareBeverage(String beverage, Integer requestTime) throws CoffeeMachineException {
        if(!this.isAvailable(requestTime)) {
            throw new CoffeeMachineException(OUTLET_CURRENTLY_UNAVAILABLE);
        }
        List<String> unAvailableIngredients = new ArrayList<>();
        Map<String, Double> ingredientsRequired = this.coffeeMachine.getBeverageInfo().get(beverage);
        Map<String, Double> ingredientsQuantity = this.coffeeMachine.getIngredientsQuantity();
        for(Map.Entry<String, Double> entry : ingredientsRequired.entrySet()) {
            if(ingredientsQuantity.getOrDefault(entry.getKey(), 0.0) < entry.getValue()) {
                unAvailableIngredients.add(entry.getKey());
            }
            if(!ingredientsQuantity.containsKey(entry.getKey())) {
                this.coffeeMachine.refillIngredient(entry.getKey(), 0.0);
            }
        }

        if(unAvailableIngredients.isEmpty()) {
            //denotes that there are no unavailable ingredients, so the beverage can be prepared now.
            this.lastUsed = requestTime;
            for(Map.Entry<String, Double> entry : ingredientsRequired.entrySet()) {
                ingredientsQuantity.put(entry.getKey(), ingredientsQuantity.get(entry.getKey()) - entry.getValue());
            }
        }
        return new OutletResponse(unAvailableIngredients);
    }

    /**
     * Checks if the outlet is available at the given time.
     * The outlet is available either if the lastUsed is -1, i.e it has never been used so far, or
     * if the request time is greater than or equal to the lastUsed + TIME_TO_PREPARE_BEVERAGE.
     * Because, the outlet would be busy in the interval [lastUsed, lastUsed + TIME_TO_PREPARE_BEVERAGE - 1]
     * @param time
     * @return
     */
    public boolean isAvailable(int time) {
        return this.lastUsed == -1 || (time >= this.lastUsed + TIME_TO_PREPARE_BEVERAGE);
    }

    /**
     * Closes the outlet i.e detach this observer from the subject.
     */
    public void close() {
        this.coffeeMachine.detach(this);
    }
}
