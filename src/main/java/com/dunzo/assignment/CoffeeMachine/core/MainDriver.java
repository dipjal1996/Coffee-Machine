package com.dunzo.assignment.CoffeeMachine.core;

import com.dunzo.assignment.CoffeeMachine.exception.CoffeeMachineException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * This is the main driver class, which creates the instance of the coffee machine, and drives the user through all the functionalities provided by the coffee machine.
 * this is an interactive class, where user needs to input some values based upon their choices.
 * it supports all features like prepare a beverage, show current quantities of ingredients, refill ingredients, show available outlets, etc
 */
public class MainDriver {

    //name of the input json file present in the resources folder
    private static final String FILE_NAME = "input.json";

    public static void main(String[] args) throws Exception {
        MainDriver mainDriver = new MainDriver();
        mainDriver.run();
    }

    private void run() throws ParseException, IOException, CoffeeMachineException {
        CoffeeMachine coffeeMachine = initializeCoffeeMachine();
        Scanner console = new Scanner(System.in);
        int timer = 0;
        boolean cont = true;
        while(cont) {
            System.out.println("Type 1 to show the current quantities of all the ingredients");
            System.out.println("Type 2 to show all the beverages supported by the machine");
            System.out.println("Type 3 to show all the current available outlets in the machine");
            System.out.println("Type 4 to prepare a beverage");
            System.out.println("Type 5 to show all the ingredients which are running low in quantities.");
            System.out.println("Type 6 to refill the ingredient.");
            System.out.println("Type 0 to exit");

            int choice = console.nextInt();
            switch (choice) {
                case 1:
                    showIngredientsQuantities(coffeeMachine);
                    break;
                case 2:
                    showAllBeveragesSupported(coffeeMachine);
                    break;
                case 3:
                    showAllAvailableOutlets(coffeeMachine, timer);
                    break;
                case 4:
                    prepareBeverage(coffeeMachine, timer, console);
                    break;
                case 5:
                    showIngredientsRunningLow(coffeeMachine);
                    break;
                case 6:
                    refillIngredients(coffeeMachine, console);
                    break;
                case 0:
                    cont = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            ++timer;
        }
    }

    private void showIngredientsQuantities(CoffeeMachine coffeeMachine) {
        System.out.println("Showing the current quanitity for each of the ingredients in the coffee machine");
        for(Map.Entry<String, Double> entry : coffeeMachine.getIngredientsQuantity().entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " ml");
        }
    }

    private void showAllBeveragesSupported(CoffeeMachine coffeeMachine) {
        System.out.println("Showing all the beverages supported by the coffee machine");
        for(Map.Entry<String, Map<String, Double>> entry : coffeeMachine.getBeverageInfo().entrySet()) {
            System.out.println(entry.getKey());
        }
    }

    private void showAllAvailableOutlets(CoffeeMachine coffeeMachine, int time) {
        System.out.println(String.format("Showing all the available outlets at the time %d", time));
        for(Integer outletNumber : coffeeMachine.allAvailableOutlets(time)) {
            System.out.println("Outlet " + outletNumber + " is available");
        }
    }

    private void prepareBeverage(CoffeeMachine coffeeMachine, int time, Scanner console) throws CoffeeMachineException {
        System.out.println("Which outlet you want the beverage to be prepared from?");
        int outletNumber = console.nextInt();
        System.out.println("What beverage do you want to have?");
        String beverage = console.next();
        System.out.println(coffeeMachine.prepareBeverage(new BeverageRequest(outletNumber, beverage, time)));
    }

    private void showIngredientsRunningLow(CoffeeMachine coffeeMachine) {
        System.out.println("Showing the ingredients which are currently running low in quantity.");
        if(coffeeMachine.showIngredientsRunningLow().isEmpty()) {
            System.out.println("Currently there are no ingredients which are running low in quantity");
            return;
        }
        for(String ingredient : coffeeMachine.showIngredientsRunningLow()) {
            System.out.println("Ingredient " + ingredient + " is running low.");
        }
    }

    private void refillIngredients(CoffeeMachine coffeeMachine, Scanner console) throws CoffeeMachineException {
        System.out.println("Which ingredient you want to refill?");
        String ingredient = console.next();
        System.out.println("What is the refill quantity?");
        Double refillQuantity = console.nextDouble();
        coffeeMachine.refillIngredient(ingredient, refillQuantity);
        System.out.println(String.format("Ingredient %s has been refilled by %f ml", ingredient, refillQuantity));
    }

    //Initializes the coffee machine using coffee machine params extractor to extract required params from the json object.
    private CoffeeMachine initializeCoffeeMachine() throws ParseException, IOException {
        JSONObject jsonObject = parseJsonObjectFromResourceFilePath(FILE_NAME);
        CoffeeMachineInputParams coffeeMachineInputParams = CoffeeMachineParamsExtractor.extractCoffeeMachineInputParamsFromJsonObject(jsonObject);
        CoffeeMachine coffeeMachine = new CoffeeMachine(coffeeMachineInputParams.getNumOutlets(), coffeeMachineInputParams.getIngredientsQuantity(), coffeeMachineInputParams.getBeverageInfo());
        return coffeeMachine;
    }

    //Reads the json file from the specified path, and converts it to jsonObject.
    private JSONObject parseJsonObjectFromResourceFilePath(String filePath) throws ParseException, IOException {
        File file = new File(this.getClass().getClassLoader().getResource(filePath).getFile());
        return (JSONObject) new JSONParser().parse(new FileReader(file));
    }
}
