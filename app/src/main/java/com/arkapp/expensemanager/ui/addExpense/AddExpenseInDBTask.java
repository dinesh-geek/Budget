package com.arkapp.expensemanager.ui.addExpense;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import com.arkapp.expensemanager.data.models.Expense;
import com.arkapp.expensemanager.data.room.AppDatabase;
import com.arkapp.expensemanager.data.room.ExpenseDao;

import static com.arkapp.expensemanager.utils.CommonUtils.convertDate;
import static com.arkapp.expensemanager.utils.CommonUtils.convertExpenseType;
import static com.arkapp.expensemanager.utils.Constants.SELECTED_EXPENSE_DATE;
import static com.arkapp.expensemanager.utils.Constants.SELECTED_EXPENSE_TYPE;

/**
 * Created by Abdul Rehman on 10-06-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
class AddExpenseInDBTask extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private final Activity activity;
    private final Integer uid;
    private final String expenseValue;

    AddExpenseInDBTask(Activity activity, String expenseValue, Integer uid) {
        this.activity = activity;
        this.uid = uid;
        this.expenseValue = expenseValue;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ExpenseDao dao = (new AppDatabase.Companion()).getDatabase(activity).expenseDao();
        dao.insert(new Expense(
                null,
                uid,
                convertExpenseType(SELECTED_EXPENSE_TYPE),
                expenseValue,
                convertDate(SELECTED_EXPENSE_DATE)));
        return null;
    }
}
