package com.arkapp.expensemanager.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import com.arkapp.expensemanager.data.models.UserLogin;
import com.arkapp.expensemanager.data.repository.PrefRepository;
import com.arkapp.expensemanager.data.room.AppDatabase;
import com.arkapp.expensemanager.data.room.UserLoginDao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.arkapp.expensemanager.utils.Constants.ENTERED_USER_NAME;

/**
 * Created by Abdul Rehman on 10-06-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
public class CheckLoggedInUserAsyncTask extends AsyncTask<Void, Void, List<UserLogin>> {
    @SuppressLint("StaticFieldLeak")
    private final Activity context;
    private final PrefRepository prefRepository;
    private final ExpenseListener listener;

    public CheckLoggedInUserAsyncTask(@NotNull Activity context,
                                      @NotNull PrefRepository prefRepository,
                                      ExpenseListener listener) {
        super();
        this.context = context;
        this.prefRepository = prefRepository;
        this.listener = listener;

    }

    @Nullable
    @Override
    protected List<UserLogin> doInBackground(@NotNull Void... params) {
        UserLoginDao userLoginDao = (new AppDatabase.Companion()).getDatabase(context).userLoginDao();
        return userLoginDao.checkLoggedInUser(ENTERED_USER_NAME);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    protected void onPostExecute(@Nullable List<UserLogin> data) {
        prefRepository.setCurrentUser(data.get(0));
        new GetAllExpenseTask(context, prefRepository, listener).execute();
    }

}
