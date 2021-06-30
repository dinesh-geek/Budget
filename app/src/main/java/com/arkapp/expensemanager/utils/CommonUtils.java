package com.arkapp.expensemanager.utils;

import com.arkapp.expensemanager.data.models.Expense;
import com.arkapp.expensemanager.data.models.ExpenseType;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Abdul Rehman on 10-06-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
public class CommonUtils {
    public static Gson gson = new Gson();

    public static String convertExpenseType(ExpenseType data) {
        return gson.toJson(data);
    }

    public static ExpenseType convertExpenseType(String data) {
        return gson.fromJson(data, ExpenseType.class);
    }

    public static String convertDate(Date data) {
        return gson.toJson(data);
    }

    public static Date convertDate(String data) {
        return gson.fromJson(data, Date.class);
    }

    public static ArrayList<Expense> filterCurrentMonth(List<Expense> data) {
        ArrayList<Expense> filteredData = new ArrayList<>();
        Calendar currentMonthCal = Calendar.getInstance();

        currentMonthCal.set(Calendar.DATE, 1);
        currentMonthCal.set(Calendar.HOUR, 0);
        currentMonthCal.set(Calendar.MINUTE, 0);
        currentMonthCal.set(Calendar.SECOND, 0);

        for (Expense expense : data) {
            if (convertDate(expense.getDate()).after(currentMonthCal.getTime())) {
                filteredData.add(expense);
            }
        }

        return filteredData;
    }

    public static ArrayList<Expense> filterPreviousMonth(List<Expense> data) {
        ArrayList<Expense> filteredData = new ArrayList<>();

        Calendar currentMonthCal = Calendar.getInstance();
        currentMonthCal.set(Calendar.DATE, 1);
        currentMonthCal.set(Calendar.HOUR, 0);
        currentMonthCal.set(Calendar.MINUTE, 0);
        currentMonthCal.set(Calendar.SECOND, 0);

        Calendar previousMonthCal = Calendar.getInstance();
        previousMonthCal.add(Calendar.MONTH, -1);
        previousMonthCal.set(Calendar.DATE, 1);
        previousMonthCal.set(Calendar.HOUR, 0);
        previousMonthCal.set(Calendar.MINUTE, 0);
        previousMonthCal.set(Calendar.SECOND, 0);

        for (Expense expense : data) {
            Date date = convertDate(expense.getDate());
            if (date.after(previousMonthCal.getTime()) && date.before(currentMonthCal.getTime())) {
                filteredData.add(expense);
            }
        }

        return filteredData;
    }


    public static double getCurrencyResult(Double enteredAmount) {
        double baseCurrencyUsdValue = Constants.CURRENCY_BASE.getUsdValue();
        double oneUsdToBaseCurrency = 1 / baseCurrencyUsdValue;

        double resultCurrencyUsdValue = Constants.CURRENCY_RESULT.getUsdValue();
        double oneUsdToResultCurrency = 1 / resultCurrencyUsdValue;

        double oneBaseCurrencyToResultCurrency = oneUsdToResultCurrency / oneUsdToBaseCurrency;

        return oneBaseCurrencyToResultCurrency * enteredAmount;
    }
}
