package com.vantage.myapplication.presentation.vehicleLoading_activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vantage.myapplication.R;
import com.vantage.myapplication.database.table_entity.Combined;
import com.vantage.myapplication.presentation.vehicleLoading_activity.TableAdapter;
import com.vantage.myapplication.presentation.vehicleLoading_activity.VehicleLoadingActivityViewModel;
import com.vantage.myapplication.presentation.warehouse_activity.WareHouseActivity;

import org.json.JSONException;

import java.util.List;

public class VehicleLoadingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TableAdapter adapter;
    VehicleLoadingActivityViewModel viewModel;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_loading);
        viewModel = new ViewModelProvider(this).get(VehicleLoadingActivityViewModel.class);
        viewInit();
        try {
            viewModel.init(this);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        buttonSubmit.setOnClickListener(v -> {
            List<Combined> updatedItems = adapter.getItems();
            viewModel.saveWareHouse(this, updatedItems);
            Intent intent = new Intent(VehicleLoadingActivity.this, WareHouseActivity.class);
            startActivity(intent);
        });
    }

    public void viewInit() {
        recyclerView = findViewById(R.id.recyclerView);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        viewModel.getItems().observe(this, items -> {
            adapter = new TableAdapter(VehicleLoadingActivity.this, items);
            recyclerView.setLayoutManager(new GridLayoutManager(VehicleLoadingActivity.this, 1));
            recyclerView.setAdapter(adapter);
        });
    }
}
