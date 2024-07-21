package com.example.budgetbuddy;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.budgetbuddy.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TransactionAdapter adapter;
    private TransactionViewModel transactionViewModel;
    private Calendar currentCalendar = Calendar.getInstance();
    private SimpleDateFormat dailyDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat monthlyDateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private boolean isMonthlyView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("BudgetBuddy");

        adapter = new TransactionAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        adapter.setOnItemLongClickListener(transaction -> showDeleteConfirmationDialog(transaction)); //AUTSAV

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                isMonthlyView = tab.getPosition() == 1; // 1 is the position for the MONTHLY tab
                updateDateDisplay();
                loadTransactions();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.prevDateBtn.setOnClickListener(view -> {
            if (isMonthlyView) {
                currentCalendar.add(Calendar.MONTH, -1);
            } else {
                currentCalendar.add(Calendar.DAY_OF_MONTH, -1);
            }
            updateDateDisplay();
            loadTransactions();
        });

        binding.nextDateBtn.setOnClickListener(view -> {
            if (isMonthlyView) {
                currentCalendar.add(Calendar.MONTH, 1);
            } else {
                currentCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            updateDateDisplay();
            loadTransactions();
        });

        updateDateDisplay();
        loadTransactions();

        binding.floatingActionButton.setOnClickListener(view -> {
            AddTransactionFragment fragment = new AddTransactionFragment();
            fragment.show(getSupportFragmentManager(), null);
        });
    }

    private void updateDateDisplay() {
        String formattedDate = isMonthlyView ? monthlyDateFormat.format(currentCalendar.getTime()) : dailyDateFormat.format(currentCalendar.getTime());
        binding.currentDate.setText(formattedDate);
    }

    private void loadTransactions() {
        if (isMonthlyView) {
            String year = String.valueOf(currentCalendar.get(Calendar.YEAR));
            String month = String.format(Locale.getDefault(), "%02d", currentCalendar.get(Calendar.MONTH) + 1);
            transactionViewModel.getTransactionsForMonth(year, month).observe(this, new Observer<List>() {
                @Override
                public void onChanged(List transactions) {
                    Log.d("MainActivity", "Monthly Transactions: " + transactions);
                    filterTransactions(transactions);
                }
            });
        } else {
            String selectedDate = dailyDateFormat.format(currentCalendar.getTime());
            transactionViewModel.getTransactionsForDate(selectedDate).observe(this, new Observer<List>() {
                @Override
                public void onChanged(List transactions) {
                    Log.d("MainActivity", "Daily Transactions: " + transactions);
                    filterTransactions(transactions);
                }
            });
        }
    }

    private void filterTransactions(List<Transaction> transactions) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        double totalIncome = 0.0;
        double totalExpense = 0.0;

        for (Transaction transaction : transactions) {
            filteredTransactions.add(transaction);
            if (transaction.getTransactionType().equalsIgnoreCase("Income")) {
                totalIncome += transaction.getAmount();
            } else {
                totalExpense += transaction.getAmount();
            }
        }

        double balance = totalIncome - totalExpense;
        binding.incomeView.setText(String.format("$%.2f", totalIncome));
        binding.expenseView.setText(String.format("$%.2f", totalExpense));
        binding.totalView.setText(String.format("$%.2f", balance));
        adapter.submitList(filteredTransactions);
    }

    private void showDeleteConfirmationDialog(Transaction transaction) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Transaction")
                .setMessage("Are you sure you want to delete this transaction?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    transactionViewModel.deleteTransaction(transaction);
                    Toast.makeText(MainActivity.this, "Transaction deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}