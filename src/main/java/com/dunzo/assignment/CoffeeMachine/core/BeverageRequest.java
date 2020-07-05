package com.dunzo.assignment.CoffeeMachine.core;

public class BeverageRequest {

    private Integer outletNumber;

    private String beverage;

    private Integer time;

    public BeverageRequest(Integer outletNumber, String beverage, Integer time) {
        this.outletNumber = outletNumber;
        this.beverage = beverage;
        this.time = time;
    }

    public void setOutletNumber(Integer outletNumber) {
        this.outletNumber = outletNumber;
    }

    public void setBeverage(String beverage) {
        this.beverage = beverage;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getOutletNumber() {
        return outletNumber;
    }

    public String getBeverage() {
        return beverage;
    }

    public Integer getTime() {
        return time;
    }
}
