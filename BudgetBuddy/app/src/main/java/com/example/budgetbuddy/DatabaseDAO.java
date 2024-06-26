package com.example.budgetbuddy;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DatabaseDAO {
    @Insert
    void insert(Transaction transaction);

    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    LiveData<List<Transaction>> getAllTransactions();
}
