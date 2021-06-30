package com.arkapp.expensemanager.ui.manageBudget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import com.arkapp.expensemanager.data.models.Budget;
import com.arkapp.expensemanager.data.repository.PrefRepository;
import com.arkapp.expensemanager.data.room.AppDatabase;
import com.arkapp.expensemanager.data.room.BudgetDao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.arkapp.expensemanager.utils.Constants.CURRENT_BUDGET;

/**
 * Created by Abdul Rehman on 10-06-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
public class GetBudgetTask extends AsyncTask<Void, Void, List<Budget>> {
    @SuppressLint("StaticFieldLeak")
    private final Activity context;
    private final PrefRepository prefRepository;
    private final BudgetListener listener;

    public GetBudgetTask(@NotNull Activity context,
                         @NotNull PrefRepository prefRepository,
                         BudgetListener listener) {
        super();
        this.context = context;
        this.prefRepository = prefRepository;
        this.listener = listener;
    }

    @Nullable
    @Override
    protected List<Budget> doInBackground(@NotNull Void... params) {
        BudgetDao budgetDao = (new AppDatabase.Companion()).getDatabase(context).budgetDao();
        return budgetDao.getUserBudget(prefRepository.getCurrentUser().getUid());
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    protected void onPostExecute(@Nullable List<Budget> data) {
        if (data != null && data.size() > 0 && data.get(0).getValue() != null) {
            CURRENT_BUDGET = data.get(0).getValue();
            listener.budgetFetched();
        } else {
            new DialogAddMonthBudget(context, prefRepository, listener).show();
        }
    }

}
