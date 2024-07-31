package com.vantage.myapplication.presentation.vehicleLoading_activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vantage.myapplication.R;
import com.vantage.myapplication.database.table_entity.Combined;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {
    private List<Combined> data;
    private Context context;

    public TableAdapter(Context context, List<Combined> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cell, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TableViewHolder holder, int position) {
        Combined item = data.get(position);
        holder.textView1.setText(item.getName());
        holder.textView2.setText(String.valueOf(item.getRPL()));

        holder.editStock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    item.setStock(Double.parseDouble(s.toString()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    item.setStock(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Combined> getItems() {
        return data;
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        EditText editStock;

        TableViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.text1);
            textView2 = itemView.findViewById(R.id.text2);
            editStock = itemView.findViewById(R.id.editStock);
        }
    }
}
