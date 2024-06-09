package com.example.budgetbuddy;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionRepo {
    private DatabaseDAO transactionDao;
    private LiveData<List<Transaction>> allTransactions;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(1);

    public TransactionRepo(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        transactionDao = db.transactionDao();
        allTransactions = transactionDao.getAllTransactions();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    public void insert(Transaction transaction) {
        databaseWriteExecutor.execute(() -> transactionDao.insert(transaction));
    }
}