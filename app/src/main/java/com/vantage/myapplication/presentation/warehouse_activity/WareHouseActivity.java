package com.vantage.myapplication.presentation.warehouse_activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.vantage.myapplication.R;
import com.vantage.myapplication.presentation.vehicleLoading_activity.VehicleLoadingActivity;
import com.vantage.myapplication.presentation.Invoice_activity.InvoiceActivity;

public class WareHouseActivity extends AppCompatActivity {

    Button buttonLoading, buttonInvoice;
    WareHouseActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_house);

        viewModel = new ViewModelProvider(this).get(WareHouseActivityViewModel.class);
        viewInit();
        viewModel.saveItems(this);
        viewModel.savePrice(this);
        viewModel.saveDiscount(this);

    }

    public void viewInit() {
        buttonLoading = findViewById(R.id.buttonLoading);
        buttonInvoice = findViewById(R.id.buttonInvoice);
        buttonLoading.setOnClickListener(v -> onClickLoading());
        buttonInvoice.setOnClickListener(v-> onClickInvoice());

    }

    public void onClickLoading() {
        Intent intent = new Intent(WareHouseActivity.this, VehicleLoadingActivity.class);
        startActivity(intent);
    }

    public void onClickInvoice() {
        Intent intent = new Intent(WareHouseActivity.this, InvoiceActivity.class);
        startActivity(intent);
    }


}