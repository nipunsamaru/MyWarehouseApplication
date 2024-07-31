package com.vantage.myapplication.presentation.vehicleLoading_activity;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vantage.myapplication.database.data_service.ItemDS;
import com.vantage.myapplication.database.data_service.WarehouseDS;
import com.vantage.myapplication.database.table_entity.Combined;
import com.vantage.myapplication.database.table_entity.WareHouseEntity;

import org.json.JSONException;

import java.util.List;

public class VehicleLoadingActivityViewModel extends ViewModel {

    ItemDS itemDS;
    private MutableLiveData<List<Combined>> items = new MutableLiveData<>();

    public void init(Context context) throws JSONException {
        this.itemDS = new ItemDS(context);
        loadItems();
    }

    private void loadItems() throws JSONException {
        List<Combined> combined = itemDS.getCombinedData();
        items.setValue(combined);
    }

    public LiveData<List<Combined>> getItems() {
        return items;
    }

    public void saveWareHouse(Context context, List<Combined> items) {
        WarehouseDS warehouseDS = new WarehouseDS(context);

        for (Combined item : items) {
            if (item.getStock() != null) {
                WareHouseEntity wareHouseEntity = new WareHouseEntity();
                wareHouseEntity.setItemUID(item.getItemUID());
                wareHouseEntity.setPriceUID(item.getPriceUID());
                wareHouseEntity.setStock(item.getStock());

                warehouseDS.createOrUpdate(wareHouseEntity);
            } else {
                System.err.println("Stock value is null for item: " + item.getItemUID());
            }
        }
    }
}
