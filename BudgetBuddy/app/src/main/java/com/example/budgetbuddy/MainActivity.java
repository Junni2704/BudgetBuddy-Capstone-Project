package com.example.budgetbuddy;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);


        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("BudgetBuddy");

        //Starting With View Transaction
        binding.floatingActionButton.setOnClickListener(view -> {
            AddTransactionFragment fragment = new AddTransactionFragment();
            fragment.show(getSupportFragmentManager(), null);
        });
    }
}