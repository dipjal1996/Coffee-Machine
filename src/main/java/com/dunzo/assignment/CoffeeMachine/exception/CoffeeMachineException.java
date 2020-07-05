package com.dunzo.assignment.CoffeeMachine.exception;

public class CoffeeMachineException extends Exception {

    private ErrorEnum errorEnum;

    public CoffeeMachineException() {
        super();
    }

    public CoffeeMachineException(String message) {
        super(message);
    }

    public CoffeeMachineException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoffeeMachineException(ErrorEnum errorEnum) {
        super(errorEnum.getFormattedErrorMessage());
        this.errorEnum = errorEnum;
    }

    public ErrorEnum getErrorEnum() {
        return this.errorEnum;
    }
}
