package com.example.financial_management_app.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financial_management_app.models.Budgets;
import com.example.financial_management_app.models.Wallets;

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

    }

    public void loadBudgetItems(int account_id) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();

                Budgets budgets = new Budgets();
                List<Budgets> budgetList = budgets.getBudgetsByAccountID(account_id);
                budgetItems.postValue(budgetList);

                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                Log.d("Execution Time Budget - get", "Thời gian thực thi của truy vấn thông tin ví: " + executionTime + "ms");
            }
        });
    }

    public void createBudget(int account_id, int wallet_id, String name, Double amount, String note, Date budget_date) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Budgets budget = new Budgets(account_id, wallet_id, name, amount, note, budget_date);
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

