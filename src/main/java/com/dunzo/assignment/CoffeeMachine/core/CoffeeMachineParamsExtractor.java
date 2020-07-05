package com.dunzo.assignment.CoffeeMachine.core;

import org.json.simple.JSONObject;

import java.util.Map;

/**
 * This class is responsible to create the object of {@link CoffeeMachineInputParams} by extracting the field values from the json object provided.
 */
public class CoffeeMachineParamsExtractor {

    public static CoffeeMachineInputParams extractCoffeeMachineInputParamsFromJsonObject(JSONObject jsonObject) {
        jsonObject = (JSONObject) jsonObject.get("machine");

        int numOutlets = ((Map<String, Long>) jsonObject.get("outlets")).get("count_n").intValue();

        Map<String, Double> ingredientsQuantity = ((Map<String, Double>) jsonObject.get("total_items_quantity"));

        //To cast the type from long to double, for values which are not decimal.
        for(Map.Entry<String, Double> entry : ingredientsQuantity.entrySet()) {
            entry.setValue(Double.valueOf(String.valueOf(entry.getValue())));
        }

        Map<String, Map<String, Double>> beveragesInfo = ((Map<String, Map<String, Double>>) jsonObject.get("beverages"));

        //To cast the type from long to double, for values which are not decimal.
        for(Map.Entry<String, Map<String, Double>> entry : beveragesInfo.entrySet()) {
            for(Map.Entry<String, Double> kv : entry.getValue().entrySet()) {
                kv.setValue(Double.valueOf(String.valueOf(kv.getValue())));
            }
        }
        return new CoffeeMachineInputParams(numOutlets, ingredientsQuantity, beveragesInfo);
    }
}
