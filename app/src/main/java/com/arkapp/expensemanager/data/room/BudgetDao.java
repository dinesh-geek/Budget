package com.arkapp.expensemanager.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.arkapp.expensemanager.data.models.Budget;

import java.util.List;

/**
 * Created by Abdul Rehman on 17-05-2020.
 * Contact email - abdulrehman0796@gmail.com
 */

/**
 * This class is used to define query for the Login table
 */
@Dao
public interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Budget var2);

    @Query("SELECT * FROM BUDGET WHERE uid = :uid")
    List<Budget> getUserBudget(int uid);

}
