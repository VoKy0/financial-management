package com.example.financial_management_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financial_management_app.models.Budgets;

import java.sql.Date;

public class BudgetDetailViewModel extends ViewModel {
    private MutableLiveData<Budgets> budget;
    private MutableLiveData<Boolean> budgetUpdated;
    private Budgets model;

    public BudgetDetailViewModel() {
        budget = new MutableLiveData<>();
        budgetUpdated = new MutableLiveData<>(false);
        model = new Budgets();
    }

    public LiveData<Budgets> getBudget() {
        return budget;
    }
    public LiveData<Boolean> isBudgetUpdated() {
        return budgetUpdated;
    }

    public void loadBudget(int budget_id) {
        new Thread(() -> {
            Budgets budgetDetail = model.getBudgetByID(budget_id);
            budget.postValue(budgetDetail);
        }).start();
    }

    public void updateBudget(int id, int wallet_id, String name, Double amount, String note, Date budget_date) {
        new Thread(() -> {
            try {
                Budgets budget = model.getBudgetByID(id);
                if (budget != null) {
                    // Kiểm tra các giá trị đầu vào trước khi cập nhật
                    if (wallet_id < 0 || name == null || name.isEmpty() || amount == null || amount < 0 || budget_date == null) {
                        throw new IllegalArgumentException("Invalid input values");
                    }

                    budget.setWalletID(wallet_id);
                    budget.setName(name);
                    budget.setAmount(amount);
                    budget.setNote(note);
                    budget.setBudgetDate(budget_date);

            model.updateBudget(budget);
            budgetUpdated.postValue(true);
                }
            } catch (Exception e) {
                e.printStackTrace();

                budgetUpdated.postValue(false);
            }

        }).start();
    }

    public void resetBudgetUpdated() {
        budgetUpdated.postValue(false);
    }
}
