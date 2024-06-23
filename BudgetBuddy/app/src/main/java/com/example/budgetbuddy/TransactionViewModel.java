package com.example.budgetbuddy;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionRepo repository;
    private LiveData<List<Transaction>> allTransactions;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepo(application);
        allTransactions = repository.getAllTransactions();

        totalIncome = Transformations.map(allTransactions, transactions -> {
            double sum = 0;
            for (Transaction transaction : transactions) {
                if ("Income".equals(transaction.getType())) {
                    sum += transaction.getAmount();
                }
            }
            return sum;
        });
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    public LiveData<Double> getTotalIncome() {
        return totalIncome;
    }

    public void insert(Transaction transaction) {
        repository.insert(transaction);
    }
}



