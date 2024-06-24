package com.example.budgetbuddy;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.budgetbuddy.databinding.FragmentAddTransactionBinding;
import com.example.budgetbuddy.databinding.ListDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;


public class AddTransactionFragment extends BottomSheetDialogFragment {

    private FragmentAddTransactionBinding binding;
    private TransactionViewModel transactionViewModel;
    private String selectedAccount;
    private String transactionType;
    private String selectedCategoryName;
    private int selectedCategoryIcon;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transactionViewModel = new ViewModelProvider(requireActivity()).get(TransactionViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddTransactionBinding.inflate(inflater, container, false);

        binding.incomeBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            transactionType = "Income"; // Set transaction type to Income
        });

        binding.expenseBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            transactionType = "Expense"; // Set transaction type to Expense
        });

        binding.Date.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
            datePickerDialog.setOnDateSetListener((datePicker, year, month, day) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
                String dateToShow = dateFormat.format(calendar.getTime());
                binding.Date.setText(dateToShow);
            });
            datePickerDialog.show();
        });

        binding.transactionCategory.setOnClickListener(c -> {
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogBinding.getRoot());

            ArrayList<Category> categories = new ArrayList<>();
            categories.add(new Category("Salary", R.drawable.ic_salary, R.color.cat_color1));
            categories.add(new Category("Business", R.drawable.ic_business, R.color.cat_color2));
            categories.add(new Category("Investment", R.drawable.ic_investment, R.color.cat_color3));
            categories.add(new Category("Loan", R.drawable.ic_loan, R.color.cat_color4));
            categories.add(new Category("Rent", R.drawable.ic_rent, R.color.cat_color5));
            categories.add(new Category("Other", R.drawable.ic_other, R.color.cat_color6));

            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categories, new CategoryAdapter.CategoryClickListener() {
                @Override
                public void onCategoryClicked(Category category) {
                    binding.transactionCategory.setText(category.getCategoryName());
                    selectedCategoryName = category.getCategoryName(); // Save category name
                    selectedCategoryIcon = category.getCategoryImage(); // Save category icon
                    categoryDialog.dismiss();
                }
            });
            dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            dialogBinding.recyclerView.setAdapter(categoryAdapter);

            categoryDialog.show();
        });

        binding.transactionAccount.setOnClickListener(c -> {
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog accountsDialog = new AlertDialog.Builder(getContext()).create();
            accountsDialog.setView(dialogBinding.getRoot());

            ArrayList<Account> accounts = new ArrayList<>();
            accounts.add(new Account(0, "Cash"));
            accounts.add(new Account(0, "Credit Card"));
            accounts.add(new Account(0, "Debit Card"));
            accounts.add(new Account(0, "Direct Deposit"));
            accounts.add(new Account(0, "Cheque"));

            AccountsAdapter adapter = new AccountsAdapter(getContext(), accounts, new AccountsAdapter.AccountsClickListener() {
                @Override
                public void onAccountSelected(Account account) {
                    binding.transactionAccount.setText(account.getAccountName());
                    selectedAccount = account.getAccountName(); // Store selected account
                    accountsDialog.dismiss();
                }
            });
            dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            dialogBinding.recyclerView.setAdapter(adapter);

            accountsDialog.show();
        });

        binding.saveTransactionBtn.setOnClickListener(view -> {
            String date = binding.Date.getText().toString();
            String amountStr = binding.amount.getText().toString();
            double amount = Double.parseDouble(amountStr);
            String transactionAccount = selectedAccount; // Use selected account
            Transaction transaction = new Transaction(date, amount, transactionAccount, transactionType, selectedCategoryName, selectedCategoryIcon); // Pass transaction type and category details
            transactionViewModel.insert(transaction);
            dismiss();
        });

        return binding.getRoot();
    }
}
