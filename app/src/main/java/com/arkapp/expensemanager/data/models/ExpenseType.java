package com.arkapp.expensemanager.data.models;

/**
 * Created by Abdul Rehman on 02-06-2020.
 * Contact email - abdulrehman0796@gmail.com
 */


public class ExpenseType {

    private final String name;
    private final Integer resId;
    private final Integer color;


    public ExpenseType(String name, Integer resId, Integer color) {
        this.resId = resId;
        this.name = name;
        this.color = color;
    }

    public Integer getResId() {
        return resId;
    }

    public String getName() {
        return name;
    }

    public Integer getColor() {
        return color;
    }
}