package com.example.budgetbuddy;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DatabaseDAO {
    @Insert
    void insert(Transaction transaction);

    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    LiveData<List<Transaction>> getAllTransactions();


    @Query("SELECT * FROM transaction_table WHERE date = :date ORDER BY id DESC")
    LiveData<List<Transaction>> getTransactionsForDate(String date);

    @Query("SELECT * FROM transaction_table WHERE strftime('%Y', date) = :year AND strftime('%m', date) = :month ORDER BY id DESC")
    LiveData<List<Transaction>> getTransactionsForMonth(String year, String month);
    @Delete
    void delete(Transaction transaction);
}