/*
* Project: CalculatorGUI
* Klasse: 2 aApc
* Author: Burim Shala
* Last change:
* by: Shala
* date: 17.09.2024
* */
package com.javacodegeeks.example;

import java.util.function.DoubleBinaryOperator;

public enum Operation {
    ADDITION((x, y) -> x+y),
    SUBTRACTION((x, y) -> x-y),
    DIVISION((x, y) -> x/y),
    MULTIPLICATION((x, y) -> x*y),
    PERCENTAGE((x, y) -> x%y);


    private DoubleBinaryOperator operator;

    Operation(DoubleBinaryOperator operator) {
        this.operator = operator;
    }

    public DoubleBinaryOperator getOperator() {
        return operator;
    }
}
