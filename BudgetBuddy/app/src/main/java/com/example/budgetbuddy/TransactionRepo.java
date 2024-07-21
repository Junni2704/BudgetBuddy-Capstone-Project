package com.example.budgetbuddy;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TransactionRepo {
    private DatabaseDAO transactionDao;
    private LiveData<List<Transaction>> allTransactions;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(1);
    private Executor executorService;

    public TransactionRepo(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        transactionDao = db.transactionDao();
        allTransactions = transactionDao.getAllTransactions();
        executorService = Executors.newSingleThreadExecutor();

    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    public LiveData<List<Transaction>> getTransactionsForDate(String date) {
        LiveData<List<Transaction>> transactions = transactionDao.getTransactionsForDate(date);
        transactions.observeForever(trans -> Log.d("TransactionRepo", "Transactions for date " + date + ": " + trans));
        return transactions;
    }

    public LiveData<List<Transaction>> getTransactionsForMonth(String year, String month) {
        LiveData<List<Transaction>> transactions = transactionDao.getTransactionsForMonth(year, month);
        transactions.observeForever(trans -> Log.d("TransactionRepo", "Transactions for month " + month + " of " + year + ": " + trans));
        return transactions;
    }

    public void insert(Transaction transaction) {
        databaseWriteExecutor.execute(() -> transactionDao.insert(transaction));
    }
    public void deleteTransaction(Transaction transaction) {
        executorService.execute(() -> transactionDao.delete(transaction));
    }
}
