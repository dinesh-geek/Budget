package com.arkapp.expensemanager.ui.currencyConversion;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.arkapp.expensemanager.R;
import com.arkapp.expensemanager.data.models.CurrencyType;
import com.arkapp.expensemanager.databinding.RvCurrencyBinding;
import com.arkapp.expensemanager.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class CurrencyTypeAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final List<CurrencyType> currencyTypes;
    private final Dialog dialog;
    private final boolean isBaseCurrency;

    public CurrencyTypeAdapter(List<CurrencyType> currencyTypes,
                               BottomSheetDialog dialog, boolean isBaseCurrency) {
        this.currencyTypes = currencyTypes;
        this.dialog = dialog;
        this.isBaseCurrency = isBaseCurrency;
    }

    @NotNull
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new CurrencyTypeViewHolder(DataBindingUtil
                                                  .inflate(
                                                          LayoutInflater.from(parent.getContext()),
                                                          R.layout.rv_currency,
                                                          parent,
                                                          false));
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        RvCurrencyBinding binding = ((CurrencyTypeViewHolder) holder).viewBinding;

        binding.tvName.setText(currencyTypes.get(position).getName());
        binding.tvSymbol.setText(currencyTypes.get(position).getSymbol());

        binding.parent.setOnClickListener(v -> {
            if (isBaseCurrency) Constants.CURRENCY_BASE = currencyTypes.get(position);
            else Constants.CURRENCY_RESULT = currencyTypes.get(position);
            dialog.dismiss();
        });
    }

    public int getItemCount() {
        return this.currencyTypes.size();
    }

    public long getItemId(int position) {
        return (currencyTypes.get(position)).hashCode();
    }
}
