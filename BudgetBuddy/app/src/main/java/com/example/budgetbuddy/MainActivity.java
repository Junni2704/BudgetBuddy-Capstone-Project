package com.example.budgetbuddy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.budgetbuddy.databinding.ActivityMainBinding;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TransactionAdapter adapter;
    private TransactionViewModel transactionViewModel;
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
        transactionViewModel.getAllTransactions().observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                adapter.submitList(transactions);
            }
        });

        transactionViewModel.getTotalIncome().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double totalIncome) {
                binding.incomeView.setText(String.format("$%.2f", totalIncome));
            }
        });

        transactionViewModel.getTotalExpense().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double totalExpense) {
                binding.expenseView.setText(String.format("$%.2f", totalExpense));
            }
        });

        transactionViewModel.getBalance().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double balance) {
                binding.totalView.setText(String.format("$%.2f", balance));
            }
        });

        //Starting With View Transaction
        binding.floatingActionButton.setOnClickListener(view -> {
            AddTransactionFragment fragment = new AddTransactionFragment();
            fragment.show(getSupportFragmentManager(), null);
        });
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
}

//Closing #3