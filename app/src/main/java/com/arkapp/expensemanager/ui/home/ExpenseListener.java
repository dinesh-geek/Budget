package com.arkapp.expensemanager.ui.home;

import com.arkapp.expensemanager.data.models.Expense;

import java.util.List;

/**
 * Created by Abdul Rehman on 10-06-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
public interface ExpenseListener {
    void onExpenseFetched(List<Expense> data);

    void onError();
}
