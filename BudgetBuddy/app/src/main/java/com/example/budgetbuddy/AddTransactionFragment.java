package com.example.budgetbuddy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.budgetbuddycopy.databinding.FragmentAddTransactionBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTransactionFragment extends BottomSheetDialogFragment {

    private FragmentAddTransactionBinding binding;
    private com.example.budgetbuddy.TransactionViewModel transactionViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transactionViewModel = new ViewModelProvider(requireActivity()).get(com.example.budgetbuddycopy.TransactionViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddTransactionBinding.inflate(inflater, container, false);

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

        binding.saveTransactionBtn.setOnClickListener(view -> {
            String date = binding.Date.getText().toString();
            String amountStr = binding.amount.getText().toString();
            double amount = Double.parseDouble(amountStr);
            com.example.budgetbuddycopy.Transaction transaction = new com.example.budgetbuddycopy.Transaction(date, amount);
            transactionViewModel.insert(transaction);
            dismiss();
        });

        return binding.getRoot();
    }
}
