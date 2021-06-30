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

import static com.arkapp.expensemanager.utils.Constants.CURRENT_BUDGET;

/**
 * Created by Abdul Rehman on 10-06-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
public class AddBudgetTask extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private final Activity context;
    private final PrefRepository prefRepository;
    private final BudgetListener listener;

    public AddBudgetTask(@NotNull Activity context,
                         @NotNull PrefRepository prefRepository,
                         BudgetListener listener) {
        super();
        this.context = context;
        this.prefRepository = prefRepository;
        this.listener = listener;
    }

    @Nullable
    @Override
    protected Void doInBackground(@NotNull Void... params) {
        BudgetDao budgetDao = (new AppDatabase.Companion()).getDatabase(context).budgetDao();
        budgetDao.insert(new Budget(null, prefRepository.getCurrentUser().getUid(), CURRENT_BUDGET));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.budgetFetched();
    }
}
