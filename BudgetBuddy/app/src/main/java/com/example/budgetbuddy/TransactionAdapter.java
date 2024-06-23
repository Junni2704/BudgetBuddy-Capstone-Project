package com.example.budgetbuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder> {

    protected TransactionAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Transaction> DIFF_CALLBACK = new DiffUtil.ItemCallback<Transaction>() {
        @Override
        public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            return oldItem.getDate().equals(newItem.getDate()) && oldItem.getAmount() == newItem.getAmount();
        }
    };

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = getItem(position);
        holder.transactionDate.setText(transaction.getDate());
        holder.transactionAmount.setText(String.format("$%.2f", transaction.getAmount()));
        holder.categoryName.setText(transaction.getCategoryName());
        holder.categoryIcon.setImageResource(transaction.getCategoryIcon());    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView transactionDate;
        TextView transactionAmount;
        TextView categoryName;
        ImageView categoryIcon;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionDate = itemView.findViewById(R.id.transactionDate);
            transactionAmount = itemView.findViewById(R.id.Date);
            categoryName = itemView.findViewById(R.id.category);
            categoryIcon = itemView.findViewById(R.id.transactionIcon);
        }
    }
}
