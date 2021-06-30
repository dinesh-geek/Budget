package com.arkapp.expensemanager.ui.manageBudget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import com.arkapp.expensemanager.data.repository.PrefRepository;
import com.arkapp.expensemanager.databinding.DialogAddBudgetBinding;
import com.arkapp.expensemanager.utils.Constants;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.arkapp.expensemanager.utils.ViewUtilsKt.setFullWidth;
import static com.arkapp.expensemanager.utils.ViewUtilsKt.setTransparentEdges;


/**
 * Created by Abdul Rehman on 29-05-2020.
 * Contact email - abdulrehman0796@gmail.com
 */

/**
 * This dialog is used for changing the guest count in the checklist screen
 */

public class DialogAddMonthBudget extends Dialog {

    private PrefRepository prefRepository;
    private Activity activity;
    private final BudgetListener listener;


    public DialogAddMonthBudget(@NotNull Activity context, PrefRepository prefRepository,
                                BudgetListener listener) {
        super(context);
        this.prefRepository = prefRepository;
        this.activity = context;
        this.listener = listener;

    }


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final DialogAddBudgetBinding binding = DialogAddBudgetBinding.inflate(LayoutInflater.from(getContext()));
        setContentView(binding.getRoot());

        setFullWidth(getWindow());
        setTransparentEdges(getWindow());

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        binding.inputBudgetEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    binding.inputBudget.setError(null);
                    Constants.CURRENT_BUDGET = Integer.parseInt(s.toString());
                }
            }
        });


        binding.doneBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.inputBudgetEt.getText().toString()) ||
                    Integer.parseInt(binding.inputBudgetEt.getText().toString()) <= 0) {
                binding.inputBudget.setError("Please enter valid budget!");
                return;
            }
            new AddBudgetTask(activity, prefRepository, listener).execute();
            dismiss();
        });
    }
}
