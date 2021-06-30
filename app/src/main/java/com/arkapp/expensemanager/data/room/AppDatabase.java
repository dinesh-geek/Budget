package com.arkapp.expensemanager.data.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.arkapp.expensemanager.data.models.Budget;
import com.arkapp.expensemanager.data.models.Expense;
import com.arkapp.expensemanager.data.models.UserLogin;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Abdul Rehman on 17-05-2020.
 * Contact email - abdulrehman0796@gmail.com
 */


/**
 * This class is used to create SQL db for the app. All tables are created and defined in this class.
 */
@Database(entities = {UserLogin.class, Expense.class, Budget.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String dbName = "EXPENSE_MANAGER_DB";

    public abstract UserLoginDao userLoginDao();

    public abstract ExpenseDao expenseDao();

    public abstract BudgetDao budgetDao();

    public static final class Companion {
        @NotNull
        public final AppDatabase getDatabase(@NotNull Context context) {
            return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppDatabase.dbName).build();
        }
    }
}
