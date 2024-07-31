package com.vantage.myapplication.presentation.Invoice_activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vantage.myapplication.R;
import com.vantage.myapplication.database.table_entity.InvoiceCombined;
import com.vantage.myapplication.database.table_entity.InvoiceEntity;
import com.vantage.myapplication.database.table_entity.InvoiceItemEntity;
import com.vantage.myapplication.presentation.Invoice_activity.InvoiceActivityViewModel;
import com.vantage.myapplication.presentation.Invoice_activity.InvoiceTableAdapter;
import com.vantage.myapplication.presentation.warehouse_activity.WareHouseActivity;

import java.util.ArrayList;
import java.util.List;

public class InvoiceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    InvoiceTableAdapter adapter;
    InvoiceActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        viewModel = new ViewModelProvider(this).get(InvoiceActivityViewModel.class);

        try {
            viewModel.init(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        viewInit();
    }

    public void viewInit() {
        recyclerView = findViewById(R.id.recyclerView2);
        viewModel.getItems().observe(this, new Observer<List<InvoiceCombined>>() {
            @Override
            public void onChanged(List<InvoiceCombined> items) {
                adapter = new InvoiceTableAdapter(InvoiceActivity.this, items);
                recyclerView.setLayoutManager(new GridLayoutManager(InvoiceActivity.this, 1));
                recyclerView.setAdapter(adapter);
            }
        });

        Button buttonSubmit = findViewById(R.id.buttonSubmitAll);
        buttonSubmit.setOnClickListener(v -> submitAll());
    }

    private void submitAll() {
        List<InvoiceCombined> items = adapter.getData();
        InvoiceEntity invoice = new InvoiceEntity();
        List<InvoiceItemEntity> invoiceItems = new ArrayList<>();

        for (InvoiceCombined item : items) {
            InvoiceItemEntity invoiceItem = new InvoiceItemEntity();

            // Get quantity from EditText
            double qty = 0;
            try {
                qty = Double.parseDouble(((EditText) recyclerView.findViewHolderForAdapterPosition(items.indexOf(item)).itemView.findViewById(R.id.editInvoiceStock)).getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            invoiceItem.setQty(qty);
            invoiceItem.setPriceUID(item.getPriceUID());
            invoiceItem.setItemUID(item.getItemUID());

            invoiceItems.add(invoiceItem);
        }

        viewModel.saveInvoiceWithItems(this, invoice, invoiceItems);
        Intent intent = new Intent(InvoiceActivity.this, WareHouseActivity.class);
        startActivity(intent);
    }
}
