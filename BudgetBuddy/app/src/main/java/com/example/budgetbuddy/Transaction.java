package com.example.budgetbuddy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction_table")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private double amount;
    private String transactionAccount;
    private String type;
    private String categoryName;
    private int categoryIcon;



    public Transaction() {}

    public Transaction(String date, double amount, String transactionAccount, String type, String categoryName, int categoryIcon) {
        this.date = date;
        this.amount = amount;
        this.transactionAccount = transactionAccount;
        this.type = type;
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

    public String getTransactionAccount() { return transactionAccount; }
    public void setTransactionAccount(String transactionAccount) { this.transactionAccount = transactionAccount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public int getCategoryIcon() { return categoryIcon; }
    public void setCategoryIcon(int categoryIcon) { this.categoryIcon = categoryIcon; }
}