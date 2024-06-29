package com.example.financial_management_app.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financial_management_app.models.Budgets;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BudgetLedgerViewModel extends ViewModel {
    private MutableLiveData<List<Budgets>> budgetItems;
    private ExecutorService executorService;
    private MutableLiveData<Boolean> budgetAdded;


    public BudgetLedgerViewModel() {
        budgetItems = new MutableLiveData<>();
        executorService = Executors.newSingleThreadExecutor();
        budgetAdded = new MutableLiveData<>(false);
        loadBudgetItems();
    }

    public LiveData<List<Budgets>> getBudgetItems() {
        return budgetItems;
    }

    public LiveData<Boolean> getBudgetAdded() {
        return budgetAdded;
    }

    public void loadBudgetItems() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();

                Budgets budgets = new Budgets();
                List<Budgets> budgetList = budgets.getBudgetsFromDB();
                budgetItems.postValue(budgetList);

                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                Log.d("Execution Time Budget - get", "Thời gian thực thi của truy vấn thông tin ví: " + executionTime + "ms");
            }
        });
    }

    public void createBudget(int account_id, String name, String note, Double amount, Date budget_date) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Budgets budget = new Budgets(account_id, name, note, amount, budget_date);
                budget.addBudget();

                // Cập nhật danh sách ví sau khi thêm ví mới
                loadBudgetItems();

                // Đánh dấu rằng ví đã được thêm
                budgetAdded.postValue(true);
            }
        });
    }
    public void resetBudgetAdded() {
        budgetAdded.setValue(false);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}

