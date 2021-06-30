package com.arkapp.expensemanager.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Abdul Rehman on 02-06-2020.
 * Contact email - abdulrehman0796@gmail.com
 */


/**
 * EXPENSE SQL table is created using the following class definition
 * This sql table is used for storing expenses of user.
 */
@Entity(tableName = "EXPENSE")
public class Expense {

    @PrimaryKey(autoGenerate = true)
    @Nullable
    private final Integer id;

    @ColumnInfo(name = "uid")
    private final Integer uid;

    @ColumnInfo(name = "expenseType")
    private final String expenseType;

    @ColumnInfo(name = "expenseValue")
    private final String expenseValue;

    @ColumnInfo(name = "date")
    private final String date;

    public Expense(@Nullable Integer id, Integer uid, String expenseType, String expenseValue,
                   String date) {
        this.id = id;
        this.uid = uid;
        this.expenseType = expenseType;
        this.expenseValue = expenseValue;
        this.date = date;
    }


    @Nullable
    public Integer getId() {
        return id;
    }

    public Integer getUid() {
        return uid;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public String getDate() {
        return date;
    }

    public String getExpenseValue() {
        return expenseValue;
    }
}