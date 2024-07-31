package com.vantage.myapplication.presentation.Invoice_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vantage.myapplication.R;
import com.vantage.myapplication.database.table_entity.InvoiceCombined;

import java.util.List;

public class InvoiceTableAdapter extends RecyclerView.Adapter<InvoiceTableAdapter.InvoiceViewHolder> {

    private List<InvoiceCombined> data;
    private Context context;

    public InvoiceTableAdapter(Context context, List<InvoiceCombined> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoice_cell, parent, false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InvoiceViewHolder holder, int position) {
        InvoiceCombined item = data.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewRPL.setText(String.valueOf(item.getRPL()));
        holder.textViewStock.setText(String.valueOf(item.getStock()));
        holder.editQty.setText(""); // Clear the input field
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<InvoiceCombined> getData() {
        return data;
    }

    public static class InvoiceViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewRPL;
        TextView textViewStock;
        EditText editQty;

        public InvoiceViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textOne);
            textViewRPL = itemView.findViewById(R.id.textTwo);
            textViewStock = itemView.findViewById(R.id.textThree);
            editQty = itemView.findViewById(R.id.editInvoiceStock);
        }
    }
}
