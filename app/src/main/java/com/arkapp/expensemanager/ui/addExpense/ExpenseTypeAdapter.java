package com.arkapp.expensemanager.ui.addExpense;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.arkapp.expensemanager.R;
import com.arkapp.expensemanager.data.models.ExpenseType;
import com.arkapp.expensemanager.databinding.RvExpenseTypeBinding;
import com.arkapp.expensemanager.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.arkapp.expensemanager.utils.GlideUtilsKt.loadImage;

public final class ExpenseTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ExpenseType> expenseTypes;
    private final Dialog dialog;

    public ExpenseTypeAdapter(List<ExpenseType> expenseTypes,
                              BottomSheetDialog dialog) {
        this.expenseTypes = expenseTypes;
        this.dialog = dialog;
    }

    @NotNull
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ExpenseTypeViewHolder(DataBindingUtil
                                                 .inflate(
                                                         LayoutInflater.from(parent.getContext()),
                                                         R.layout.rv_expense_type,
                                                         parent,
                                                         false));
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        RvExpenseTypeBinding binding = ((ExpenseTypeViewHolder) holder).viewBinding;

        loadImage(binding.ivIcon, expenseTypes.get(position).getResId());
        binding.tvName.setText(expenseTypes.get(position).getName());
        binding.parent.setOnClickListener(v -> {
            Constants.SELECTED_EXPENSE_TYPE = expenseTypes.get(position);
            dialog.dismiss();
        });
    }

    public int getItemCount() {
        return this.expenseTypes.size();
    }

    public long getItemId(int position) {
        return (expenseTypes.get(position)).hashCode();
    }
}
