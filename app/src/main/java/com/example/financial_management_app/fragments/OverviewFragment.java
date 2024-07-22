package com.example.financial_management_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.financial_management_app.R;
import com.example.financial_management_app.viewmodels.OverviewViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;

public class OverviewFragment extends Fragment {
    private OverviewViewModel mViewModel;
    private TextView totalIncome;
    private TextView totalExpense;
    private TextView finalResult;
    private BarChart barChart;
    private PieChart pieChart;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(OverviewViewModel.class);

        totalIncome = view.findViewById(R.id.total_income);
        totalExpense = view.findViewById(R.id.total_expense);
        finalResult = view.findViewById(R.id.final_result);
        barChart = view.findViewById(R.id.bar_chart);
        pieChart = view.findViewById(R.id.pie_chart);

        mViewModel.getIncome().observe(getViewLifecycleOwner(), income -> {
            totalIncome.setText(income + " đ");
        });

        mViewModel.getExpense().observe(getViewLifecycleOwner(), expense -> {
            totalExpense.setText(expense + " đ");
        });

        mViewModel.getFinalResult().observe(getViewLifecycleOwner(), result -> {
            finalResult.setText(result + " đ");
        });

        mViewModel.getBarChartData().observe(getViewLifecycleOwner(), data -> {
            // cập nhật dữ liệu biểu đồ cột
            barChart.setData((BarData) data);
            barChart.invalidate();
        });

        mViewModel.getPieChartData().observe(getViewLifecycleOwner(), data -> {
            // cập nhật dữ liệu biểu đồ tròn
            pieChart.setData((PieData) data);
            pieChart.invalidate();
        });

        // Tải dữ liệu khi hiển thị
        mViewModel.loadOverviewData();
    }
}
