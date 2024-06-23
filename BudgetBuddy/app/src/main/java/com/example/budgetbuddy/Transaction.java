package com.example.budgetbuddy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction_table")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private double amount;

    private String categoryName;
    private int categoryIcon;


    public Transaction() {}

    public Transaction(String date, double amount) {
        this.date = date;
        this.amount = amount;
        this.categoryName = categoryName;
        this.categoryIcon = categoryIcon;

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

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public int getCategoryIcon() { return categoryIcon; }
    public void setCategoryIcon(int categoryIcon) { this.categoryIcon = categoryIcon; }
}