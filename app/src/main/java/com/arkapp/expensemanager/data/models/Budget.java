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
 * BUDGET SQL table is created using the following class definition
 * This sql table is used for storing budget of user.
 */
@Entity(tableName = "BUDGET")
public class Budget {
    @PrimaryKey(autoGenerate = true)
    @Nullable
    private final Integer id;

    @ColumnInfo(name = "uid")
    private final Integer uid;

    @ColumnInfo(name = "value")
    private final Integer value;

    public Budget(@Nullable Integer id, Integer uid, Integer value) {
        this.id = id;
        this.uid = uid;
        this.value = value;
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public Integer getUid() {
        return uid;
    }

    public Integer getValue() {
        return value;
    }
}