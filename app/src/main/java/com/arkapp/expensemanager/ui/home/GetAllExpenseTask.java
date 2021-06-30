package com.arkapp.expensemanager.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import com.arkapp.expensemanager.data.models.Expense;
import com.arkapp.expensemanager.data.repository.PrefRepository;
import com.arkapp.expensemanager.data.room.AppDatabase;
import com.arkapp.expensemanager.data.room.ExpenseDao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by Abdul Rehman on 10-06-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
public class GetAllExpenseTask extends AsyncTask<Void, Void, List<Expense>> {
    @SuppressLint("StaticFieldLeak")
    private final Activity context;
    private final PrefRepository prefRepository;
    private final ExpenseListener listener;

    public GetAllExpenseTask(@NotNull Activity context,
                             @NotNull PrefRepository prefRepository,
                             ExpenseListener listener) {
        super();
        this.context = context;
        this.prefRepository = prefRepository;
        this.listener = listener;
    }

    @Nullable
    @Override
    protected List<Expense> doInBackground(@NotNull Void... params) {
        ExpenseDao userLoginDao = (new AppDatabase.Companion()).getDatabase(context).expenseDao();
        return userLoginDao.getAllUserExpense(prefRepository.getCurrentUser().getUid());
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    protected void onPostExecute(@Nullable List<Expense> data) {
        if (data != null && data.size() > 0)
            listener.onExpenseFetched(data);
        else
            listener.onError();
    }

}
