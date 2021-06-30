package com.arkapp.expensemanager.data.models;

/**
 * Created by Abdul Rehman on 02-06-2020.
 * Contact email - abdulrehman0796@gmail.com
 */


public class CurrencyType {

    private final String name;
    private final String symbol;
    private final double usdValue;

    public CurrencyType(String name, String symbol, double usdValue) {
        this.name = name;
        this.symbol = symbol;
        this.usdValue = usdValue;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getUsdValue() {
        return usdValue;
    }
}