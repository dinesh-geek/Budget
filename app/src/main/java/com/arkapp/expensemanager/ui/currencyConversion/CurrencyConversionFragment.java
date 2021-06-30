package com.arkapp.expensemanager.ui.currencyConversion;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arkapp.expensemanager.data.models.CurrencyType;
import com.arkapp.expensemanager.databinding.BottomeSheetSelectCurrencyBinding;
import com.arkapp.expensemanager.databinding.FragmentCurrencyConversionBinding;
import com.davidmiguel.numberkeyboard.NumberKeyboardListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import static com.arkapp.expensemanager.utils.CommonUtils.getCurrencyResult;
import static com.arkapp.expensemanager.utils.Constants.CURRENCY_BASE;
import static com.arkapp.expensemanager.utils.Constants.CURRENCY_RESULT;
import static com.arkapp.expensemanager.utils.Constants.getAllCurrency;
import static com.arkapp.expensemanager.utils.ViewUtilsKt.initVerticalAdapter;
import static com.arkapp.expensemanager.utils.ViewUtilsKt.isDoubleClicked;


public class CurrencyConversionFragment extends Fragment {

    private FragmentCurrencyConversionBinding binding;
    private BottomSheetDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrencyConversionBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCurrency();

        BottomeSheetSelectCurrencyBinding dialogBinding = getBottomView();

        binding.tvBaseCurrency.setOnClickListener(v -> {
            if (isDoubleClicked(1000)) return;
            setCurrencyRv(true, dialogBinding);
            dialog.show();
        });

        binding.ivBaseDropDown.setOnClickListener(v -> {
            if (isDoubleClicked(1000)) return;
            setCurrencyRv(true, dialogBinding);
            dialog.show();
        });

        binding.tvConvertedCurrency.setOnClickListener(v -> {
            if (isDoubleClicked(1000)) return;
            setCurrencyRv(false, dialogBinding);
            dialog.show();
        });

        binding.ivResultDropDown.setOnClickListener(v -> {
            if (isDoubleClicked(1000)) return;
            setCurrencyRv(false, dialogBinding);
            dialog.show();
        });

        dialog.setOnDismissListener(dialog -> {
            setCurrency();
        });

        binding.ivSwap.setOnClickListener(v -> {
            if (isDoubleClicked(500)) return;
            CurrencyType temp = CURRENCY_BASE;
            CURRENCY_BASE = CURRENCY_RESULT;
            CURRENCY_RESULT = temp;
            setCurrency();
        });

        binding.etBaseValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    double enteredAmount = Double.parseDouble(s.toString());
                    binding.tvConvertedValue.setText(String.format("%s", getCurrencyResult(enteredAmount)));
                } else binding.tvConvertedValue.setText("0.0");

            }
        });

        binding.keyboard.setListener(new NumberKeyboardListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onNumberClicked(int number) {
                binding.etBaseValue.setText(String.format("%s%d", binding.etBaseValue.getText().toString(), number));
            }

            @Override
            public void onLeftAuxButtonClicked() {
                String value = binding.etBaseValue.getText().toString();
                if (!TextUtils.isEmpty(value) && !value.substring(value.length() - 1).equals(".")) {
                    binding.etBaseValue.setText(String.format("%s.", binding.etBaseValue.getText().toString()));
                }
            }

            @Override
            public void onRightAuxButtonClicked() {
                String value = binding.etBaseValue.getText().toString();
                if (!TextUtils.isEmpty(value)) {
                    binding.etBaseValue.setText(value.substring(0, value.length() - 1));
                }
            }
        });
    }

    private void setCurrency() {
        if (CURRENCY_BASE != null) {
            binding.tvBaseCurrency.setText(CURRENCY_BASE.getSymbol());
        }
        if (CURRENCY_RESULT != null) {
            binding.tvConvertedCurrency.setText(CURRENCY_RESULT.getSymbol());
        }

        if (!TextUtils.isEmpty(binding.etBaseValue.getText().toString())) {
            double enteredAmount = Double.parseDouble(binding.etBaseValue.getText().toString());
            binding.tvConvertedValue.setText(String.format("%s", getCurrencyResult(enteredAmount)));
        } else binding.tvConvertedValue.setText("0.0");
    }

    private BottomeSheetSelectCurrencyBinding getBottomView() {
        BottomeSheetSelectCurrencyBinding dialogBinding = BottomeSheetSelectCurrencyBinding.inflate(LayoutInflater.from(requireContext()));
        dialog = new BottomSheetDialog(requireContext());
        dialog.setContentView(dialogBinding.getRoot());
        dialog.setOnShowListener(dialog12 -> {
            View view = (((BottomSheetDialog) dialog12).findViewById(com.google.android.material.R.id.design_bottom_sheet));
            BottomSheetBehavior.from(view).setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        return dialogBinding;
    }

    private void setCurrencyRv(boolean isBaseCurrency,
                               BottomeSheetSelectCurrencyBinding dialogBinding) {
        initVerticalAdapter(
                dialogBinding.rvCurrency,
                new CurrencyTypeAdapter(getAllCurrency(), dialog, isBaseCurrency),
                true);
    }
}