package com.arkapp.expensemanager.ui.addExpense;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.arkapp.expensemanager.databinding.RvExpenseTypeBinding;

import org.jetbrains.annotations.NotNull;


public final class ExpenseTypeViewHolder extends ViewHolder {
    @NotNull
    public final RvExpenseTypeBinding viewBinding;

    public ExpenseTypeViewHolder(@NotNull RvExpenseTypeBinding viewBinding) {
        super(viewBinding.getRoot());
        this.viewBinding = viewBinding;
    }
}
