package com.example.financial_management_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class OverviewViewModel extends ViewModel {
    private MutableLiveData<String> income;
    private MutableLiveData<String> expense;
    private MutableLiveData<String> finalResult;
    private MutableLiveData<BarData> barChartData;
    private MutableLiveData<PieData> pieChartData;

    public OverviewViewModel() {
        income = new MutableLiveData<>();
        expense = new MutableLiveData<>();
        finalResult = new MutableLiveData<>();
        barChartData = new MutableLiveData<>();
        pieChartData = new MutableLiveData<>();
    }

    public LiveData<String> getIncome() {
        return income;
    }

    public LiveData<String> getExpense() {
        return expense;
    }

    public LiveData<String> getFinalResult() {
        return finalResult;
    }

    public LiveData<BarData> getBarChartData() {
        return barChartData;
    }

    public LiveData<PieData> getPieChartData() {
        return pieChartData;
    }

    public void loadOverviewData() {
        // Giả lập dữ liệu
        income.setValue("100.000 đ");
        expense.setValue("50.000 đ");
        finalResult.setValue("50.000 đ");

        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, 100000));
        barEntries.add(new BarEntry(1, 50000));
        BarDataSet barDataSet = new BarDataSet(barEntries, "Data");
        BarData barData = new BarData(barDataSet);
        barChartData.setValue(barData);

        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(100000, "Income"));
        pieEntries.add(new PieEntry(50000, "Expense"));
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Data");
        PieData pieData = new PieData(pieDataSet);
        pieChartData.setValue(pieData);
    }
}
