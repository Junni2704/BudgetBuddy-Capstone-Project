package com.example.budgetbuddy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction_table")
public class DataModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private double amount;

    public DataModel(String date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}
