package com.arkapp.expensemanager.ui.manageBudget;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.arkapp.expensemanager.R;
import com.arkapp.expensemanager.data.repository.PrefRepository;
import com.arkapp.expensemanager.databinding.FragmentManageBudgetBinding;
import com.arkapp.expensemanager.utils.PieValueFormatter;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.arkapp.expensemanager.utils.Constants.CURRENT_BUDGET;
import static com.arkapp.expensemanager.utils.Constants.TOTAL_EXPENSES;
import static com.arkapp.expensemanager.utils.ViewUtilsKt.getColorRes;


public class ManageBudgetFragment extends Fragment implements BudgetListener {

    private PrefRepository prefRepository;
    private FragmentManageBudgetBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        prefRepository = new PrefRepository(requireContext());
        binding = FragmentManageBudgetBinding.inflate(inflater);
        return binding.getRoot();
    }


    private void initChartSetting() {
        binding.barChart.setFitBars(true);
        binding.barChart.animateXY(500, 500);
        binding.barChart.getLegend().setWordWrapEnabled(true);
        binding.barChart.getLegend().setTextSize(16);
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.getXAxis().setEnabled(false);
    }

    private void initChartData() {
        binding.barChart.clear();
        initLegend();
        initChartSetting();

        List<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(0f, CURRENT_BUDGET));
        entries.add(new BarEntry(1f, (float) TOTAL_EXPENSES));

        BarDataSet set = new BarDataSet(entries, "Budget VS Expense");
        set.setValueFormatter(new PieValueFormatter());
        set.setColors(getColorRes(requireContext(), R.color.green), getColorRes(requireContext(), R.color.red));

        BarData data = new BarData(set);
        data.setValueTextSize(18f);
        data.setBarWidth(0.5f);
        binding.barChart.setData(data);
    }

    private void initLegend() {
        ArrayList<LegendEntry> legendEntries = new ArrayList<>();
        legendEntries.add(new LegendEntry(
                "Budget",
                Legend.LegendForm.SQUARE,
                18f, 18f,
                null,
                getColorRes(requireContext(), R.color.green)));

        legendEntries.add(new LegendEntry(
                "Expenses",
                Legend.LegendForm.SQUARE,
                18f, 18f,
                null,
                getColorRes(requireContext(), R.color.red)));

        binding.barChart.getLegend().setCustom(legendEntries);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {
        super.onStart();

        binding.tvMonthBudget.setText("Current Month Budget\n$" + CURRENT_BUDGET);
        binding.tvMonthExpense.setText("Current Month Expenses\n$" + TOTAL_EXPENSES);

        if (CURRENT_BUDGET == 0) {
            new GetBudgetTask(requireActivity(), prefRepository, this).execute();
        } else initChartData();

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void budgetFetched() {
        binding.tvMonthBudget.setText("Current Month Budget\n$" + CURRENT_BUDGET);
        binding.tvMonthExpense.setText("Current Month Expenses\n$" + TOTAL_EXPENSES);
        initChartData();
    }

    //Used for showing the change menu option in the toolbar
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        requireActivity().getMenuInflater().inflate(R.menu.menu_manage_budget, menu);
    }

    //Toolbar options click listener
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == R.id.changeBudget) {
            new DialogAddMonthBudget(requireActivity(), prefRepository, this).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}