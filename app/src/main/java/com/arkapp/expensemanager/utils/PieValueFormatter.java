package com.arkapp.expensemanager.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * Created by Abdul Rehman on 11-06-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
public class PieValueFormatter extends ValueFormatter {
    @Override
    public String getFormattedValue(float value) {
        return String.format("$%s", value);
    }
}
