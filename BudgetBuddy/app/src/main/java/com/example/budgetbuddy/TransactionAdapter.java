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


    private OnItemLongClickListener longClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(Transaction transaction);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public TransactionAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Transaction> DIFF_CALLBACK = new DiffUtil.ItemCallback<Transaction>() {
        @Override
        public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            boolean isSameDate = (oldItem.getDate() == null && newItem.getDate() == null) || (oldItem.getDate() != null && oldItem.getDate().equals(newItem.getDate()));
            boolean isSameAmount = oldItem.getAmount() == newItem.getAmount();
            boolean isSameAccount = (oldItem.getTransaction_account() == null && newItem.getTransaction_account() == null) || (oldItem.getTransaction_account() != null && oldItem.getTransaction_account().equals(newItem.getTransaction_account()));
            return isSameDate && isSameAmount && isSameAccount;
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
        holder.transactionAccount.setText(transaction.getTransaction_account());
        holder.transactionType.setText(transaction.getTransactionType());
        holder.transactionCategoryIcon.setImageResource(transaction.getCategoryIcon());
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView transactionDate;
        TextView transactionAmount;
        TextView transactionAccount;
        TextView transactionType;
        ImageView transactionCategoryIcon;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionDate = itemView.findViewById(R.id.transactionDate);
            transactionAmount = itemView.findViewById(R.id.tran_amount);
            transactionAccount = itemView.findViewById(R.id.accountLbl);
            transactionType = itemView.findViewById(R.id.category);
            transactionCategoryIcon = itemView.findViewById(R.id.transactionIcon);

            itemView.setOnLongClickListener(view -> {
                if (longClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(getItem(position));
                    }
                }
                return true;
            });
        }
    }
}