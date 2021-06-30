package com.arkapp.expensemanager.ui.currencyConversion;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.arkapp.expensemanager.databinding.RvCurrencyBinding;

import org.jetbrains.annotations.NotNull;


public final class CurrencyTypeViewHolder extends ViewHolder {
    @NotNull
    public final RvCurrencyBinding viewBinding;

    public CurrencyTypeViewHolder(@NotNull RvCurrencyBinding viewBinding) {
        super(viewBinding.getRoot());
        this.viewBinding = viewBinding;
    }
}
