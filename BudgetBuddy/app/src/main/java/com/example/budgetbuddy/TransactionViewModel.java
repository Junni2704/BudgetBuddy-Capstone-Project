package com.example.budgetbuddy;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionRepo repository;
    private LiveData<List<Transaction>> allTransactions;
    private LiveData<Double> totalIncome;
    private LiveData<Double> totalExpense;
    private LiveData<Double> balance;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepo(application);
        allTransactions = repository.getAllTransactions();

        totalIncome = Transformations.map(allTransactions, transactions -> {
            double sum = 0;
            for (Transaction transaction : transactions) {
                if ("Income".equals(transaction.getTransactionType())) {
                    sum += transaction.getAmount();
                }
            }
            return sum;
        });
        totalExpense = Transformations.map(allTransactions, transactions -> {
            double sum = 0;
            for (Transaction transaction : transactions) {
                if ("Expense".equals(transaction.getTransactionType())) {
                    sum += transaction.getAmount();
                }
            }
            return sum;
        });

        balance = Transformations.map(allTransactions, transactions -> {
            double income = 0;
            double expense = 0;
            for (Transaction transaction : transactions) {
                if ("Income".equals(transaction.getTransactionType())) {
                    income += transaction.getAmount();
                } else if ("Expense".equals(transaction.getTransactionType())) {
                    expense += transaction.getAmount();
                }
            }
            return income - expense;
        });
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    public LiveData<Double> getTotalIncome() {
        return totalIncome;
    }

    public LiveData<Double> getTotalExpense() {
        return totalExpense;
    }

    public LiveData<Double> getBalance() {
        return balance;
    }

    public void insert(Transaction transaction) {
        repository.insert(transaction);
    }

    public LiveData<List<Transaction>> getTransactionsForDate(String date) {
        return repository.getTransactionsForDate(date);
    }

    public LiveData<List<Transaction>> getTransactionsForMonth(String year, String month) {
        return repository.getTransactionsForMonth(year, month);
    }
}